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
            val url = BuildConfig.APPWRITE_URL
            val projectID = BuildConfig.PROJECT_ID
            return Client(context)
                .setEndpoint(url)
                .setProject(projectID)
                .setSelfSigned(true);
        }

        fun getClient(context: Context, secretJWT : String) : Client {
            val url = BuildConfig.APPWRITE_URL
            val projectID = BuildConfig.PROJECT_ID
            return Client(context)
                .setEndpoint(url)
                .setProject(projectID)
                .setJWT(secretJWT)
                .setSelfSigned(true);
        }
    }
}