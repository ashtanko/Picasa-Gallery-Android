#include <iostream>
#include <jni.h>
#include <string>
#include <map>

using namespace std;

extern "C"
JNIEXPORT jstring

JNICALL
Java_io_shtanko_picasagallery_Config_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {

    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

