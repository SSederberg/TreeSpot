package net.n4dev.treespot.firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.orhanobut.logger.Logger

class TreespotFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "TreespotFirebaseMessagingService"

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
        Logger.i("New Token For Firebase was given!")
    }

    override fun onDeletedMessages() {
        super.onDeletedMessages()
        Logger.i("OnDeletedMessages was called!")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "From: ${remoteMessage.from}")
        if (remoteMessage.data.isNotEmpty()) {
//            listener.onNotificationWithData(remoteMessage)
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }


    }
}