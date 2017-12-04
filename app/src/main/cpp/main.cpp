#include <iostream>
#include <jni.h>
#include <string>
#include <map>
#include "PropertiesParser.h"

using namespace std;
using namespace cppproperties;

extern "C"
JNIEXPORT jstring

JNICALL
Java_io_shtanko_picasagallery_Config_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {

    string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT void JNICALL
Java_io_shtanko_picasagallery_Config_initProperties(JNIEnv *env, jobject instance, jstring path_) {
    const char *path = env->GetStringUTFChars(path_, 0);
    //Properties props = PropertiesParser::Read(path);

    // TODO

    env->ReleaseStringUTFChars(path_, path);
}