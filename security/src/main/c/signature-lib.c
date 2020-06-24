

#include <jni.h>

JNIEXPORT jbyteArray JNICALL
Java_co_yap_security_SecurityHelper_bytesFromJNI(JNIEnv *env, jobject ctx, jobject obj) {
    jclass cls = (*env)->GetObjectClass(env, obj);
    jmethodID mid = (*env)->GetMethodID(env, cls, "getPackageManager",
                                        "()Landroid/content/pm/PackageManager;");
    jobject packageManager = (*env)->CallObjectMethod(env, obj, mid);

// this.getPackageName()
    mid = (*env)->GetMethodID(env, cls, "getPackageName", "()Ljava/lang/String;");//
    jstring packageName = (jstring) (*env)->CallObjectMethod(env, obj, mid);

// packageManager->getPackageInfo(packageName, GET_SIGNATURES);
    cls = (*env)->GetObjectClass(env, packageManager);
    mid = (*env)->GetMethodID(env, cls, "getPackageInfo",
                              "(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;");
    jint flags = 0x00000040;
    jobject packageInfo = (*env)->CallObjectMethod(env, packageManager, mid, packageName, flags);

// packageInfo->signatures
    cls = (*env)->GetObjectClass(env, packageInfo);
    jfieldID fid = (*env)->GetFieldID(env, cls, "signatures", "[Landroid/content/pm/Signature;");
    jobject signatures = (*env)->GetObjectField(env, packageInfo, fid);

// signatures[0]
    jobject signature = (*env)->GetObjectArrayElement(env, signatures, 0);

// signature->toByteArray()
    cls = (*env)->GetObjectClass(env, signature);
    mid = (*env)->GetMethodID(env, cls, "toByteArray", "()[B");
    jbyteArray appSig = (*env)->CallObjectMethod(env, signature, mid);
    return appSig;
}

