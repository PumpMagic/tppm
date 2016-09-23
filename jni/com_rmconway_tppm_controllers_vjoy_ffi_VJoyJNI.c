#include <jni.h>
#include <stdio.h>
#include "vjoyinterface.h"
#include "com_rmconway_tppm_controllers_vjoy_ffi_VJoyJNI.h"

/*
 * Class:     com_rmconway_tppm_controllers_vjoy_ffi_VJoyJNI
 * Method:    getVJoyVersion
 * Signature: ()S
 */
JNIEXPORT jshort JNICALL Java_com_rmconway_tppm_controllers_vjoy_ffi_VJoyJNI_getVJoyVersion (JNIEnv *env, jobject obj)
{
    return GetvJoyVersion();
}

/*
 * Class:     com_rmconway_tppm_controllers_vjoy_ffi_VJoyJNI
 * Method:    isVJoyEnabled
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_rmconway_tppm_controllers_vjoy_ffi_VJoyJNI_isVJoyEnabled (JNIEnv *env, jobject obj)
{
    return vJoyEnabled();
}

/*
 * Class:     com_rmconway_tppm_controllers_vjoy_ffi_VJoyJNI
 * Method:    getNumButtons
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_com_rmconway_tppm_controllers_vjoy_ffi_VJoyJNI_getNumButtons (JNIEnv *env, jobject obj, jint vjoyDeviceID)
{
    return GetVJDButtonNumber(vjoyDeviceID);
}

/*
 * Class:     com_rmconway_tppm_controllers_vjoy_ffi_VJoyJNI
 * Method:    doesDeviceExist
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_rmconway_tppm_controllers_vjoy_ffi_VJoyJNI_doesDeviceExist (JNIEnv *env, jobject obj, jint vjoyDeviceID)
{
    return isVJDExists(vjoyDeviceID);
}

/*
 * Class:     com_rmconway_tppm_controllers_vjoy_ffi_VJoyJNI
 * Method:    claimDevice
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_rmconway_tppm_controllers_vjoy_ffi_VJoyJNI_claimDevice (JNIEnv *env, jobject obj, jint vjoyDeviceID)
{
    return AcquireVJD(vjoyDeviceID);
}

/*
 * Class:     com_rmconway_tppm_controllers_vjoy_ffi_VJoyJNI
 * Method:    releaseDevice
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_rmconway_tppm_controllers_vjoy_ffi_VJoyJNI_releaseDevice (JNIEnv *env, jobject obj, jint vjoyDeviceID)
{
    return RelinquishVJD(vjoyDeviceID);
}

/*
 * Class:     com_rmconway_tppm_controllers_vjoy_ffi_VJoyJNI
 * Method:    resetDevice
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_com_rmconway_tppm_controllers_vjoy_ffi_VJoyJNI_resetDevice (JNIEnv *env, jobject obj, jint vjoyDeviceID)
{
    return ResetVJD(vjoyDeviceID);
}

/*
 * Class:     com_rmconway_tppm_controllers_vjoy_ffi_VJoyJNI
 * Method:    setButton
 * Signature: (IBZ)Z
 */
JNIEXPORT jboolean JNICALL Java_com_rmconway_tppm_controllers_vjoy_ffi_VJoyJNI_setButton (JNIEnv *env, jobject obj, jint deviceID, jbyte buttonID, jboolean value)
{
    return SetBtn(value, deviceID, buttonID);
}
