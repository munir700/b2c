//
// Created by Faheem Riaz on 15/05/2020.
//
#include <jni.h>
#include <string>
#include "base64.h"
#include <chrono>

extern "C" JNIEXPORT jobject JNICALL
Java_co_yap_app_AAPApplication_buildConfigKeysFromJNI(JNIEnv *env, jobject /*this*/,
                                                      jstring javaString, jstring flavour,
                                                      jstring buildVariant) {

    const char *nativeString = env->GetStringUTFChars(javaString, 0);
    jclass buildConfigManager = env->FindClass(nativeString);
    jmethodID constructor = env->GetMethodID(buildConfigManager, "<init>",
                                             "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");

    std::string api_endpoint;
    std::string leanPlumSecretKey;
    std::string leanPlumKey;
    std::string adjustAppToken = "am0wjeshw5xc";

#ifdef LIVE
    api_endpoint = "https://stg.yap.co/";
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

    const char *productFlavour = env->GetStringUTFChars(flavour, nullptr);
    const char *buildType = env->GetStringUTFChars(buildVariant, nullptr);
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

    jobject jObj = env->NewObject(buildConfigManager, constructor,
                                  env->NewStringUTF(leanPlumSecretKey.c_str()),
                                  env->NewStringUTF(leanPlumKey.c_str()),
                                  env->NewStringUTF(adjustAppToken.c_str()),
                                  env->NewStringUTF(api_endpoint.c_str()),
                                  env->NewStringUTF(buildType),
                                  env->NewStringUTF(productFlavour),
                                  env->NewStringUTF(""),
                                  env->NewStringUTF(""));
    return jObj;
}
