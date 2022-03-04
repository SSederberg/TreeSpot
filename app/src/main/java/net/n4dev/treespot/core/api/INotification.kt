package net.n4dev.treespot.core.api

import android.app.Notification
import android.content.Context

interface INotification {

    fun prepareNotification(context: Context) : INotification

    fun getNotification() : Notification

    fun buildAndDisplay(context: Context)

}