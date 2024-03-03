package com.isd.isd

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson

class MyFirebaseService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val title : String? = message.notification?.title
        val content : String? = message.notification?.body
        val data : String? = Gson().toJson(message.data)


        if (content != null) {
            if (title != null) {
                Utils.showNotification(this, title,content)
            }
        }
    }


}