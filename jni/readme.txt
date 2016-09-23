1. cd src/main/java
2. javac com/rmconway/tppm/controllers/vjoy/ffi/VJoyJNI.java
3. javah com.rmconway.tppm.controllers.vjoy.ffi.VJoyJNI
4. mv com_rmconway_tppm_controllers_vjoy_ffi_VJoyJNI.h ../../../jni
5. cd ../../../jni
6. Write implementation JNI C implementation