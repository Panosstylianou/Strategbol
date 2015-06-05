LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := andengine_shared
LOCAL_SRC_FILES := \
	/Users/panosstylianou/AndroidStudioProjects/Strategbol/andEngine/src/main/jni/Android.mk \
	/Users/panosstylianou/AndroidStudioProjects/Strategbol/andEngine/src/main/jni/Application.mk \
	/Users/panosstylianou/AndroidStudioProjects/Strategbol/andEngine/src/main/jni/build.sh \
	/Users/panosstylianou/AndroidStudioProjects/Strategbol/andEngine/src/main/jni/src/BufferUtils.cpp \
	/Users/panosstylianou/AndroidStudioProjects/Strategbol/andEngine/src/main/jni/src/GLES20Fix.c \

LOCAL_C_INCLUDES += /Users/panosstylianou/AndroidStudioProjects/Strategbol/andEngine/src/main/jni
LOCAL_C_INCLUDES += /Users/panosstylianou/AndroidStudioProjects/Strategbol/andEngine/src/debug/jni

include $(BUILD_SHARED_LIBRARY)
