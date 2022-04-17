package net.n4dev.treespot.util

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.IntRange


class DeviceConnectionHelper {

    companion object {

        @SuppressLint("MissingPermission")
        @IntRange(from = 0, to = 3)
        fun getConnectionType(context: Context): Int {
            var result = 0 // Returns connection type. 0: none; 1: mobile data; 2: wifi
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    result = 2
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    result = 1
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                    result = 3
                }
            }
            return result
        }

        fun isConnected(context: Context) : Boolean {
            return getConnectionType(context) > 0
        }
    }

}