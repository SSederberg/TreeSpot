package net.n4dev.treespot.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class TreespotFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "TreespotFirebaseMessagingService"

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "From: ${remoteMessage.from}")
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }


    }

    companion object {
        fun init() {

        }
    }
}