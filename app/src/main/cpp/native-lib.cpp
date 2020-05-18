//
// Created by Faheem Riaz on 15/05/2020.
//
#include <jni.h>
#include <string>
#include "base64.h"
#include <chrono>

extern "C" JNIEXPORT jobject JNICALL
Java_co_yap_app_main_MainActivity_signatureKeysFromJNI(JNIEnv *env, jobject /*this*/) {
    std::string hello = "Faheem Riaz";
    std::string encoded = base64_encode(reinterpret_cast<const unsigned char *>(hello.c_str()),
                                        hello.length());

    return env->NewStringUTF(encoded.c_str());
}

