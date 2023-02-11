//
// Created by Ashutosh Sharma on 05/02/23.
//
#include <jni.h>
#include <string>
#include <jni.h>
#include <jni.h>

extern "C" jstring
Java_com_ashu_savemytax_utils_Keys_webKey(JNIEnv *env, jobject thiz) {
    std::string web_client_key = "730378104833-et1fj6hg4kgjpqo7v9rcrqb7g0nvqlfa.apps.googleusercontent.com";
    return env->NewStringUTF(web_client_key.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_ashu_savemytax_utils_Keys_notificationChannel(JNIEnv *env, jobject thiz) {
    std::string notification_channel = "tax_saver_notification";
    return env->NewStringUTF(notification_channel.c_str());
}
extern "C"
JNIEXPORT jstring JNICALL
Java_com_ashu_savemytax_utils_Keys_notificationChannelDescription(JNIEnv *env, jobject thiz) {
    std::string notification_channel_description = "this is notification channel for octopus app";
    return env->NewStringUTF(notification_channel_description.c_str());
}