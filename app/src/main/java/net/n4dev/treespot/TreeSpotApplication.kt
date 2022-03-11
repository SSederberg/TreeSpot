package net.n4dev.treespot

import android.app.Application
import android.content.Context
import androidx.annotation.NonNull
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import io.appwrite.Client
import net.n4dev.treespot.db.TreeSpotObjectBox


class TreeSpotApplication : Application(), CameraXConfig.Provider {

    @NonNull
    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }

    override fun onCreate() {
        super.onCreate()
        TreeSpotObjectBox.init(this)
    }

    companion object {
        fun getClient(context: Context): Client {
            return Client(context)
                .setEndpoint("http://192.168.1.245/v1")
                .setProject("treespot-dev")
                .setSelfSigned(true);
        }

        fun getClient(context: Context, secretJWT : String) : Client {
            return Client(context)
                .setEndpoint("http://192.168.1.245/v1")
                .setProject("treespot-dev")
                .setJWT(secretJWT)
                .setSelfSigned(true);
        }
    }
}