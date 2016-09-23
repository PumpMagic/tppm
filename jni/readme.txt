0. Install mingw-w64
0. Put the absolute path of src/main/resources in your Windows PATH, LOL
1. cd src/main/java
2. javac com/rmconway/tppm/controllers/vjoy/ffi/VJoyJNI.java
3. javah com.rmconway.tppm.controllers.vjoy.ffi.VJoyJNI
4. mv com_rmconway_tppm_controllers_vjoy_ffi_VJoyJNI.h ../../../jni
5. cd ../../../jni
6. Write implementation JNI C implementation
7. gcc -Wl,--add-stdcall-alias -I"$JAVA_HOME\include" -I"$JAVA_HOME\include\win32" -shared -o vjoyjni.dll com_rmconway_tppm_controllers_vjoy_ffi_VJoyJNI.c -L"..\src\main\resources" -lvJoyInterface
8. cp vjoyjni.dll ../src/main/resources