package net.n4dev.treespot.ui

import android.Manifest
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.RemoteMessage
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import io.objectbox.Box
import net.n4dev.treespot.core.api.IEntity
import net.n4dev.treespot.core.api.IFirebaseMessage
import net.n4dev.treespot.db.TreeSpotObjectBox


abstract class TreeSpotActivity : AppCompatActivity(), IFirebaseMessage {

    abstract fun buildFromBundle(bundle: Bundle)

    val developmentFormatStrategy = PrettyFormatStrategy.newBuilder()
        .showThreadInfo(true)
        .tag("TreeSpot")
        .methodCount(5)
        .build()

    fun <T : IEntity> getBox(klass : Class<T>): Box<T> {
        return TreeSpotObjectBox.getBoxStore().boxFor(klass)
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