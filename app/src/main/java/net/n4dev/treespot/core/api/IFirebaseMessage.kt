package net.n4dev.treespot.core.api

import com.google.firebase.messaging.RemoteMessage

interface IFirebaseMessage {

    fun onNotificationWithData(remoteMessage: RemoteMessage)
}