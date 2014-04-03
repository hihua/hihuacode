LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE := speex-build
LOCAL_SRC_FILES := lib/speex/libspeex.so
LOCAL_EXPORT_C_INCLUDES := include/speex
LOCAL_EXPORT_LDLIBS := lib/speex/libspeex.so
LOCAL_PRELINK_MODULE := true
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_ALLOW_UNDEFINED_SYMBOLS=false
LOCAL_MODULE    		:= audiorecord
LOCAL_LDLIBS 			:= -llog -lz -lm $(LOCAL_PATH)/lib/speex/libspeex.so
LOCAL_SRC_FILES 		:= audiorecord.cpp
LOCAL_C_INCLUDES 		:= $(LOCAL_PATH)/include/speex
include $(BUILD_SHARED_LIBRARY)
