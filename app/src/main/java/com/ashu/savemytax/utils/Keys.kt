package com.ashu.savemytax.utils

object Keys {

    init {
        System.loadLibrary("native-lib")
    }

    external fun webKey(): String

    external fun notificationChannel(): String

    external fun notificationChannelDescription(): String
}