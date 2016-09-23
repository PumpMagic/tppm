package com.rmconway.tppm.controllers.twitchplays;

import com.rmconway.tppm.controllers.inputs.HasInputs;
import com.rmconway.tppm.controllers.inputs.Input;

import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TwitchPlaysParser provides utility methods for parsing text messages as controller commands.
 */
public class TwitchPlaysParser {
    private static String numberGroupName = "number";
    private static String unitGroupName = "unit";
    private static Pattern durationPattern = Pattern.compile("(?<"+numberGroupName+">-?\\d+)(?<"+unitGroupName+">ms|s|f)");

    private static final double BUTTON_PRESS_VALUE = 1.0;

    private HasInputs controller;
    private InputProfile inputProfile;


    public TwitchPlaysParser(HasInputs controller, InputProfile inputProfile) {
        this.controller = controller;
        this.inputProfile = inputProfile;
    }

    private static String[] tokenize(String text) {
        return text.split(" ");
    }

    private Optional<Long> parseAsDuration(String token) {
        Matcher durationMatcher = durationPattern.matcher(token);
        if (durationMatcher.matches()) {
            // Token fits the duration pattern, but we don't know if it's valid yet
            try {
                String numberString = durationMatcher.group(numberGroupName);
                String unit = durationMatcher.group(unitGroupName);

                int number = Integer.parseInt(numberString);
                long duration;
                switch(unit) {
                    case "ms":
                        duration = TimeUnit.MILLISECONDS.toNanos(number);
                        break;
                    case "s":
                        duration = TimeUnit.SECONDS.toNanos(number);
                        break;
                    case "f":
                        duration = number*inputProfile.nanosecondsPerFrame();
                        break;
                    default:
                        // Should never happen unless our duration pattern is not as we expect
                        return Optional.empty();
                }

                return Optional.of(duration);
            } catch (NumberFormatException e) {
                // The number portion of the delay is invalid (probably too large)
            }
            catch (IllegalStateException|IllegalArgumentException e) {
                // Should only happen if our group names are inconsistent somehow, or we didn't call matches()
            }
        }

        return Optional.empty();
    }

    private Optional<Long> parseAsDelay(String token) {
        // A delay token is just a parentheses-enclosed duration
        if (token.startsWith("(") && token.endsWith(")")) {
            String insideParentheses = token.substring(1, token.length()-1);

            return parseAsDuration(insideParentheses);
        }

        return Optional.empty();
    }

    private Optional<Input> parseAsInput(String token) {
        return controller.getInputByName(token);
    }

    public Optional<PriorityQueue<TimedInputCommand>> parse(String text) {
        String[] tokens = tokenize(text);

        int tokenBeingProcessed = 0;
        long nextOffset = 0;
        PriorityQueue<TimedInputCommand> commands = new PriorityQueue<>(TimedInputCommand.comparator);

        // Iterate over starter tokens
        // Valid "starter" tokens are input identifiers (button names) and delays (durations enclosed in parentheses)
        // Inputs can have a "modifier" duration token
        boolean lastTokenWasDelay = true;
        TimedInputCommand lastCommand = null;

        while (tokenBeingProcessed < tokens.length) {
            String token = tokens[tokenBeingProcessed];

            // Try parsing the token as a delay
            Optional<Long> asDelay = parseAsDelay(token);
            if (asDelay.isPresent()) {
                nextOffset += asDelay.get();
                tokenBeingProcessed += 1;
                lastTokenWasDelay = true;
                continue;
            }

            // Try parsing the token as an input identifier
            Optional<Input> asInput = parseAsInput(token);
            if (asInput.isPresent()) {
                // Got an input token! Start making the input command
                Input input = asInput.get();

                // If no delay was specified for after the last input, infer it using the input profile
                if (!lastTokenWasDelay) {
                    long recommendedDelay = inputProfile.suggestedDelay(lastCommand.target, input);
                    if (recommendedDelay < 0) {
                        if (lastCommand.duration > Math.abs(recommendedDelay)) {
                            nextOffset += recommendedDelay;
                        } else {
                            // Our next command would go before the last one... probably not intended by user
                            nextOffset += lastCommand.duration;
                        }
                    } else {
                        nextOffset += recommendedDelay;
                    }
                }

                TimedInputCommand command = new TimedInputCommand(input,
                        BUTTON_PRESS_VALUE,
                        nextOffset,
                        inputProfile.defaultInputDurations.get(input),
                        true);

                // Act on a subsequent duration token, if present, by specifying a manual duration
                int tokensProcessed = 1;
                if (tokenBeingProcessed < tokens.length - 1) {
                    Optional<Long> asDuration = parseAsDuration(tokens[tokenBeingProcessed+1]);
                    if (asDuration.isPresent()) {
                        command.duration = asDuration.get();
                        tokensProcessed += 1;
                    }
                }

                commands.add(command);

                tokenBeingProcessed += tokensProcessed;
                lastTokenWasDelay = false;
                lastCommand = command;
                nextOffset += command.duration;
                continue;
            }

            return Optional.empty();
        }

        if (!commands.isEmpty()) {
            return Optional.of(commands);
        }

        return Optional.empty();
    }
}
