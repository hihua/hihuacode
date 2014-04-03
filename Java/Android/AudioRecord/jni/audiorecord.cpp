#include <jni.h>
#include <time.h>
#include <math.h>
#include <limits.h>
#include <stdio.h>
#include <stdlib.h>
#include <inttypes.h>
#include <unistd.h>
#include <assert.h>
#include <android/log.h>

#include <speex/speex.h>

#define LOG_TAG		"AudioRecord"

#define LOGI(...)	__android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)
#define LOGD(...)	__android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGE(...)	__android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)


#define	CLASSACTIVITYMAIN		com_android_media_ActivityMain
#define	CLASSSPEEX				com_android_media_speex_Speex

#define FUNCNAME(CLASS, FUNC) 		Java_##CLASS##_##FUNC
#define FUNCS(CLASS, FUNC) 			FUNCNAME(CLASS, FUNC)
#define FUNCACTIVITYMAIN(FUNC) 		FUNCS(CLASSACTIVITYMAIN, FUNC)
#define FUNCSPEEX(FUNC) 			FUNCS(CLASSSPEEX, FUNC)


#ifdef __cplusplus
extern "C" {
#endif
static SpeexBits ebits, dbits;
static int dec_frame_size = 0;
static int enc_frame_size = 0;
static void* enc_state = NULL;
static void* dec_state = NULL;

jmethodID findMethodId(JNIEnv* env, jobject obj, const char* method, const char* param) {
	jclass clazz = env->GetObjectClass(obj);
	return env->GetMethodID(clazz, method, param);
}

JNIEXPORT void JNICALL FUNCSPEEX(initEncode)(JNIEnv* env, jobject obj, jint compression) {
	speex_bits_init(&ebits);
	enc_state = speex_encoder_init(speex_lib_get_mode(SPEEX_MODEID_NB));
	speex_encoder_ctl(enc_state, SPEEX_SET_QUALITY, &compression);
	speex_encoder_ctl(enc_state, SPEEX_GET_FRAME_SIZE, &enc_frame_size);
	int tmp = 1;
	speex_encoder_ctl(enc_state, SPEEX_SET_COMPLEXITY, &tmp);
	tmp = 0;
	speex_encoder_ctl(enc_state, SPEEX_SET_VBR, &tmp);
}

JNIEXPORT void JNICALL FUNCSPEEX(initDecode)(JNIEnv* env, jobject obj) {
	speex_bits_init(&dbits);
	dec_state = speex_decoder_init(speex_lib_get_mode(SPEEX_MODEID_NB));
	speex_decoder_ctl(dec_state, SPEEX_GET_FRAME_SIZE, &dec_frame_size);
	int tmp = 1;
	speex_decoder_ctl(dec_state, SPEEX_SET_ENH, &tmp);
}

JNIEXPORT int JNICALL FUNCSPEEX(encodeSpeex)(JNIEnv* env, jobject obj, jbyteArray in) {
	speex_bits_reset(&ebits);
	jbyte* ptr = env->GetByteArrayElements(in, 0);
	int ret = speex_encode_int(enc_state, (spx_int16_t*)ptr, &ebits);
	env->ReleaseByteArrayElements(in, ptr, 0);
	return ret;
}

JNIEXPORT int JNICALL FUNCSPEEX(encodeSpeexs)(JNIEnv* env, jobject obj, jbyteArray out, jint out_offset, jint out_size) {
	jbyte* ptr = env->GetByteArrayElements(out, 0);
	int size = speex_bits_write_whole_bytes(&ebits, (char*)(ptr + out_offset), out_size);
	env->ReleaseByteArrayElements(out, ptr, 0);
	return size;
}

JNIEXPORT void JNICALL FUNCSPEEX(decodeSpeex)(JNIEnv* env, jobject obj, jbyteArray in, jint in_offset, jint in_size) {
	speex_bits_reset(&dbits);
	jbyte* ptr = env->GetByteArrayElements(in, 0);
	speex_bits_read_from(&dbits, (char*)(ptr + in_offset), in_size);
	env->ReleaseByteArrayElements(in, ptr, 0);
}

JNIEXPORT int JNICALL FUNCSPEEX(decodeSpeexs)(JNIEnv* env, jobject obj, jbyteArray out, jint out_offset) {
	jbyte* ptr = env->GetByteArrayElements(out, 0);
	int ret = speex_decode_int(dec_state, &dbits, (spx_int16_t*)(ptr + out_offset));
	env->ReleaseByteArrayElements(out, ptr, 0);
	return ret;
}

JNIEXPORT int JNICALL FUNCSPEEX(decodeRemaining)(JNIEnv* env, jobject obj) {
	return speex_bits_remaining(&dbits);
}

JNIEXPORT int JNICALL FUNCSPEEX(getEncodeFrameSize)(JNIEnv* env, jobject obj) {
	return enc_frame_size;
}

JNIEXPORT int JNICALL FUNCSPEEX(getDecodeFrameSize)(JNIEnv* env, jobject obj) {
	return dec_frame_size;
}

JNIEXPORT void JNICALL FUNCSPEEX(releaseEncode)(JNIEnv* env, jobject obj) {
	speex_bits_destroy(&ebits);

	if (enc_state != NULL) {
		speex_encoder_destroy(enc_state);
		enc_state = NULL;
	}
}

JNIEXPORT void JNICALL FUNCSPEEX(releaseDecode)(JNIEnv* env, jobject obj) {
	speex_bits_destroy(&dbits);

	if (dec_state != NULL) {
		speex_decoder_destroy(dec_state);
		dec_state = NULL;
	}
}
#ifdef __cplusplus
}
#endif
