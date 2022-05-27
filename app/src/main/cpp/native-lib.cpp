#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_druide_flexwithmovies_utils_Keys_apiKey(JNIEnv *env, jobject object) {
    // you need to set your api key here
    std::string api_key = "your_api_key";
    return env->NewStringUTF(api_key.c_str());
}