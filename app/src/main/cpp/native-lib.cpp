//
// Created by Faheem Riaz on 15/05/2020.
//
#include <jni.h>
#include <string>
#include "base64.h"
#include <chrono>

extern "C" JNIEXPORT jobject JNICALL
Java_co_yap_app_main_MainActivity_signatureKeysFromJNI(JNIEnv *env, jobject /*this*/,
                                                       jstring javaString) {
    const char *nativeString = env->GetStringUTFChars(javaString, 0);
    jclass appSignature = env->FindClass(nativeString);
    jmethodID constructor = env->GetMethodID(appSignature, "<init>",
                                             "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");

    std::string sha1Encoded = "QkU6NjE6NDE6NUM6MDQ6MzQ6REU6RDg6NDU6NkU6QzA6MDk6RTA6OTU6REY6RTQ6NDk6ODA6QTc6RjY=";
    std::string md5Encoded = "OEU6OUM6Qjk6QkM6MEM6RUE6QkY6NTY6ODE6MDQ6Q0Q6MTY6MTU6Qjk6RDA6NjI=";
    std::string sha256Encoded = "QTU6QjQ6MkY6MUQ6NEE6RTU6Q0E6Q0I6OEQ6ODM6NUI6Q0U6MUQ6ODU6N0E6NzY6NDE6RkU6NUM6QjI6NTk6RkI6M0Q6MDQ6NTM6MTE6Qzg6QkI6ODA6RjE6NjY6RkQ=";
    //un-comment when we needed to encode a string into base64
//    std::string encoded = base64_encode(reinterpret_cast<const unsigned char *>(sha256.c_str()),
//                                        sha256.length());

    std::string sha1Decoded = base64_decode((sha1Encoded.c_str()));
    std::string md5Decoded = base64_decode((md5Encoded.c_str()));
    std::string sha256Decoded = base64_decode((sha256Encoded.c_str()));

    jobject jObj = env->NewObject(appSignature, constructor, env->NewStringUTF(md5Decoded.c_str()),
                                  env->NewStringUTF(sha1Decoded.c_str()),
                                  env->NewStringUTF(sha256Decoded.c_str()));
    return jObj;
}