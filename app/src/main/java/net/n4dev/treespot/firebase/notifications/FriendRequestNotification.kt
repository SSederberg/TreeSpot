package net.n4dev.treespot.firebase.notifications

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import net.n4dev.treespot.R
import net.n4dev.treespot.core.api.INotification

class FriendRequestNotification : INotification {
    private lateinit var builder : NotificationCompat.Builder
    private val notificationID = 101

    companion object {
        const val not_id = "TS_FriendRequest"
    }

    override fun prepareNotification(context: Context) : FriendRequestNotification {
        builder = NotificationCompat.Builder(context, "ts_not_id")
            .setSmallIcon(R.drawable.ic_friends)
            .setContentTitle("New Friend Request!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        return this
    }

    override fun getNotification(): Notification {
       return builder.build()
    }

    override fun buildAndDisplay(context: Context) {
        val managerCompat = NotificationManagerCompat.from(context)

        managerCompat.notify(notificationID, getNotification())
    }


}