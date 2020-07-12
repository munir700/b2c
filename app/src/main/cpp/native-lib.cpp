//
// Created by Faheem Riaz on 15/05/2020.
//
#include <jni.h>
#include <string>
#include "base64.h"
#include <chrono>

extern "C" JNIEXPORT jobject JNICALL
Java_co_yap_app_AAPApplication_signatureKeysFromJNI(JNIEnv *env, jobject /*this*/,
                                                    jstring javaString, jstring flavour,
                                                    jstring buildVariant,jstring versionName,jstring versionCode) {
    const char *nativeString = env->GetStringUTFChars(javaString, 0);
    jclass appSignature = env->FindClass(nativeString);
    jmethodID constructor = env->GetMethodID(appSignature, "<init>",
                                             "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");

    std::string sha1Encoded = "QkU6NjE6NDE6NUM6MDQ6MzQ6REU6RDg6NDU6NkU6QzA6MDk6RTA6OTU6REY6RTQ6NDk6ODA6QTc6RjY=";
    std::string md5Encoded = "OEU6OUM6Qjk6QkM6MEM6RUE6QkY6NTY6ODE6MDQ6Q0Q6MTY6MTU6Qjk6RDA6NjI=";
    std::string sha256Encoded = "QTU6QjQ6MkY6MUQ6NEE6RTU6Q0E6Q0I6OEQ6ODM6NUI6Q0U6MUQ6ODU6N0E6NzY6NDE6RkU6NUM6QjI6NTk6RkI6M0Q6MDQ6NTM6MTE6Qzg6QkI6ODA6RjE6NjY6RkQ=";
    //un-comment when we needed to encode a string into base64
    //std::string encoded = base64_encode(reinterpret_cast<const unsigned char *>(sha256.c_str()),
    //sha256.length());

    std::string sha1Decoded = base64_decode((sha1Encoded.c_str()));
    std::string md5Decoded = base64_decode((md5Encoded.c_str()));
    std::string sha256Decoded = base64_decode((sha256Encoded.c_str()));

    std::string api_endpoint;
    std::string leanPlumSecretKey;
    std::string leanPlumKey;
    std::string adjustAppToken = "am0wjeshw5xc";

#ifdef LIVE
    api_endpoint = "https://yap.com/";
#endif
#ifdef STG
    api_endpoint = "https://stg.yap.co/";
#endif
#ifdef QA
    api_endpoint = "https://qa.yap.co/";
#endif
#ifdef DEV
    api_endpoint = https://dev-b.yap.com/;
#endif

    const char *productFlavour = env->GetStringUTFChars(flavour, 0);
    const char *buildType = env->GetStringUTFChars(buildVariant, 0);
    const char *vName = env->GetStringUTFChars(versionName, 0);
    const char *vCode = env->GetStringUTFChars(versionCode, 0);
    if (strcmp(productFlavour, "live") == 0 && strcmp(buildType, "release") == 0) {
        leanPlumSecretKey = "app_DtOp3ipxDUi9AM7Bg3jv351hZ4DVrLgC9JZX4L46lIc";
        leanPlumKey = "prod_MfjUF6Sh3GuNE2RtQMkXZTeCUSTS3K0v2CLeGCp0gzk";

    } else if (strcmp(productFlavour, "live") == 0 && strcmp(buildType, "debug") == 0) {
        leanPlumSecretKey = "app_DtOp3ipxDUi9AM7Bg3jv351hZ4DVrLgC9JZX4L46lIc";
        leanPlumKey = "dev_RAFVBmDKypdOr3kbd326JUoqGLr8iSvt2Lei4BK48qk";

    } else if (strcmp(productFlavour, "stg") == 0 && strcmp(buildType, "release") == 0) {
        leanPlumSecretKey = "app_OjUbwCEcWfawOQzYABPyg5R7y9sFLgFm9C1JdgIa3Qk";
        leanPlumKey = "prod_KX4ktWrg5iHyP12VbRZ92U0SOVXyYrcWk5B68TfBAW0";

    } else if (strcmp(productFlavour, "stg") == 0 && strcmp(buildType, "debug") == 0) {
        leanPlumSecretKey = "app_OjUbwCEcWfawOQzYABPyg5R7y9sFLgFm9C1JdgIa3Qk";
        leanPlumKey = "dev_2ssrA8Mh1BazUIZHqIQabRP0a76cQwZ1MYfHsJpODMQ";

    } else if (strcmp(productFlavour, "qa") == 0 && strcmp(buildType, "release") == 0) {
        leanPlumSecretKey = "app_OjUbwCEcWfawOQzYABPyg5R7y9sFLgFm9C1JdgIa3Qk";
        leanPlumKey = "prod_KX4ktWrg5iHyP12VbRZ92U0SOVXyYrcWk5B68TfBAW0";
    } else if (strcmp(productFlavour, "qa") == 0 && strcmp(buildType, "debug") == 0) {

        leanPlumSecretKey = "app_OjUbwCEcWfawOQzYABPyg5R7y9sFLgFm9C1JdgIa3Qk";
        leanPlumKey = "dev_2ssrA8Mh1BazUIZHqIQabRP0a76cQwZ1MYfHsJpODMQ";
    } else if (strcmp(productFlavour, "dev") == 0 && strcmp(buildType, "release") == 0) {

        leanPlumSecretKey = "app_OjUbwCEcWfawOQzYABPyg5R7y9sFLgFm9C1JdgIa3Qk";
        leanPlumKey = "prod_KX4ktWrg5iHyP12VbRZ92U0SOVXyYrcWk5B68TfBAW0";
    } else if (strcmp(productFlavour, "dev") == 0 && strcmp(buildType, "debug") == 0) {

        leanPlumSecretKey = "app_OjUbwCEcWfawOQzYABPyg5R7y9sFLgFm9C1JdgIa3Qk";
        leanPlumKey = "dev_2ssrA8Mh1BazUIZHqIQabRP0a76cQwZ1MYfHsJpODMQ";
    } else {

    }

    jobject jObj = env->NewObject(appSignature, constructor, env->NewStringUTF(md5Decoded.c_str()),
                                  env->NewStringUTF(sha1Decoded.c_str()),
                                  env->NewStringUTF(sha256Decoded.c_str()),
                                  env->NewStringUTF(leanPlumSecretKey.c_str()),
                                  env->NewStringUTF(leanPlumKey.c_str()),
                                  env->NewStringUTF(adjustAppToken.c_str()),
                                  env->NewStringUTF(api_endpoint.c_str()),
                                  env->NewStringUTF(buildType),
                                  env->NewStringUTF(productFlavour),
                                  env->NewStringUTF(vName),
                                  env->NewStringUTF(vCode));
    return jObj;
}
