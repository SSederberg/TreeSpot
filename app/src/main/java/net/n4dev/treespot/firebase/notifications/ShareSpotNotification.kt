package net.n4dev.treespot.firebase.notifications

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import net.n4dev.treespot.R
import net.n4dev.treespot.core.api.INotification

class ShareSpotNotification(private val username : String) : INotification {
    private lateinit var builder : NotificationCompat.Builder
    private val notificationID = 102

    override fun prepareNotification(context: Context): ShareSpotNotification {
        builder = NotificationCompat.Builder(context, "ts_not_id")
            .setContentTitle("$username has shared a spot with you!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.ic_location)
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