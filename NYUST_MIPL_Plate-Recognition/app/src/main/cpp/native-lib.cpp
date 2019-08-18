#include <jni.h>
#include <string>
#include <android/log.h>

extern "C" {


jstring
Java_com_mipl_lungyu_licenseplaterecognition_jni_LPDModule_stringFromJNI(
        JNIEnv *env,
        jobject obj, jstring cascade) {
    std::string hello = "Hello JNI from C++";
    return env->NewStringUTF(hello.c_str());
}


jstring
Java_com_mipl_lungyu_licenseplaterecognition_jni_LPDModule_LPD(
        JNIEnv *env,
        jobject obj, jstring cascade, jintArray photoData, jint w, jint h, jint t) {
    //adaboost init
    const char *str_cascade;
    str_cascade = env->GetStringUTFChars(cascade, false);
    jintArray faceArray1, faceArray2;
//    CvHaarClassifierCascade * cv_cascade = (CvHaarClassifierCascade *) cvLoad(str_cascade);


    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


}