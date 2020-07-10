//
// Created by Faheem Riaz on 15/05/2020.
//
#include <jni.h>
#include <string>
#include "base64.h"
#include <chrono>

extern "C" JNIEXPORT jobject JNICALL
Java_co_yap_app_main_MainActivity_buildConfigKeysFromJNI(JNIEnv *env, jobject /*this*/,
                                                         jstring javaString, jstring flavour,
                                                         jstring buildVariant) {
    std::string leanPlumApiKey = "app_DtOp3ipxDUi9AM7Bg3jv351hZ4DVrLgC9JZX4L46lIc";
    std::string adjustAppToken = "am0wjeshw5xc";
    std::string googleMapsAPIKey = "AIzaSyD14efXe-xXjpqUPX85ZhiKaKFHZyANSrE";


    const char *productFlavour = env->GetStringUTFChars(flavour, nullptr);
    const char *buildType = env->GetStringUTFChars(buildVariant, nullptr);
    if (strcmp(productFlavour, "live") == 0 && strcmp(buildType, "release") == 0) {
        leanPlumApiKey = "prod_MfjUF6Sh3GuNE2RtQMkXZTeCUSTS3K0v2CLeGCp0gzk";
        adjustAppToken = "am0wjeshw5xc";
        googleMapsAPIKey = "AIzaSyD14efXe-xXjpqUPX85ZhiKaKFHZyANSrE"
    } else if (strcmp(productFlavour, "live") == 0 && strcmp(, "debug") == 0) {
        leanPlumApiKey = "app_DtOp3ipxDUi9AM7Bg3jv351hZ4DVrLgC9JZX4L46lIc";
        adjustAppToken = "am0wjeshw5xc";
        googleMapsAPIKey = "AIzaSyD14efXe-xXjpqUPX85ZhiKaKFHZyANSrE"
    } else if (strcmp(productFlavour, "stg") == 0) {
    } else if (strcmp(productFlavour, "qa") == 0) {
    } else if (strcmp(productFlavour, "dev") == 0) {
    }

    const char *nativeString = env->GetStringUTFChars(javaString, nullptr);
    jclass buildConfigManager = env->FindClass(nativeString);
    jmethodID constructor = env->GetMethodID(buildConfigManager, "<init>",
                                             "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");


    //un-comment when we needed to encode a string into base64
//    std::string encoded = base64_encode(reinterpret_cast<const unsigned char *>(sha256.c_str()),
//                                        sha256.length());

//    std::string sha1Decoded = base64_decode((sha1Encoded.c_str()));
//    std::string md5Decoded = base64_decode((md5Encoded.c_str()));
//    std::string sha256Decoded = base64_decode((sha256Encoded.c_str()));

    jobject jObj = env->NewObject(buildConfigManager, constructor,
                                  env->NewStringUTF(leanPlumApiKey.c_str()),
                                  env->NewStringUTF(adjustAppToken.c_str()),
                                  env->NewStringUTF(googleMapsAPIKey.c_str()));
    return jObj;
}

