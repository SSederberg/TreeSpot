package net.n4dev.treespot.util

import android.util.Log
import java.lang.Exception

class LogUtil {

    companion object {
        private val TAG = "TreeSpot"

        fun i(log: String) {
            Log.i(TAG, log)
        }

        fun e(log : String) {
            Log.e(TAG, log)
        }

        fun e(exception: Exception) {
            exception.printStackTrace()
            Log.e(TAG, "A error has occurred in TreeSpot!", exception)
        }
    }
}