package com.abdullahtutun.fcm.model

data class PushNotification(
    var data: NotificationData,
    var to: String
)
