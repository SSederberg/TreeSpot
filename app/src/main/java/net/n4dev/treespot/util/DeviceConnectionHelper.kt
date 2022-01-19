package net.n4dev.treespot.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

class DeviceConnectionHelper {

    companion object {
        fun isConnected(context: Context) : Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
            return isConnected
        }

        fun isConnectionMetered(context: Context) : Boolean {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val isMetered = cm.isActiveNetworkMetered()
            return isMetered
        }
    }

}