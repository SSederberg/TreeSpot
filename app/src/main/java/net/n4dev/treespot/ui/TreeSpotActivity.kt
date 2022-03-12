package net.n4dev.treespot.ui

import android.Manifest
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.RemoteMessage
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import net.n4dev.treespot.core.api.IFirebaseMessage
import net.n4dev.treespot.db.entity.User


open class TreeSpotActivity : AppCompatActivity(), IFirebaseMessage {


    val developmentFormatStrategy = PrettyFormatStrategy.newBuilder()
        .showThreadInfo(true)
        .tag("TreeSpot")
        .methodCount(5)
        .build()

    fun loadUser(string : String) : ArrayList<User> {
        val returnedList: ArrayList<User> = ArrayList()


        val loadThread = Thread {

        }

        loadThread.start()
        loadThread.join()

        return returnedList
    }

    override fun onNotificationWithData(remoteMessage: RemoteMessage) {
        Logger.d("Invoked in TreeSpotActivity")
    }

    fun getSharedPreferences() : SharedPreferences {
        return getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
    }

    companion object {
        val TREESPOT_PERMISSIONS = arrayOf(Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE)

        val PREFS_NAME = "TreeSpotPrefsFile"
        val PREF_VERSION_CODE_KEY = "version_code"
        val DOESNT_EXIST = -1
        val PREF_ACTIVE_USERNAME_ID = "active_username"
        val PREF_ACTIVE_SESSION_ID = "active_session"
        val PREF_ACTIVE_JWT = "active_jwt"
    }
}