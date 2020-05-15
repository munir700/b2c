//
// Created by Faheem Riaz on 15/05/2020.
//
#include <jni.h>
#include <string>

extern "C" JNIEXPORT jobject JNICALL
Java_co_yap_activities_MainActivity_SignatureKeysFromJNI(JNIEnv *env, jobject /*this*/) {
    std::string hello = "hema";
    return env->NewStringUTF(hello.c_str());
}

