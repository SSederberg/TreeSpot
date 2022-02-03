package net.n4dev.treespot

import android.app.Application
import androidx.annotation.NonNull
import androidx.camera.camera2.Camera2Config
import androidx.camera.core.CameraXConfig
import com.parse.Parse


class TreeSpotApplication : Application(), CameraXConfig.Provider {

    override fun onCreate() {
        super.onCreate()
        Parse.initialize(parseDevConfiguration)
    }

    private val parseDevConfiguration = Parse.Configuration.Builder(this)
        .applicationId("treespot-dev")
        .server("http://192.168.1.245:1337/parse/")
        .build()

    @NonNull
    override fun getCameraXConfig(): CameraXConfig {
        return Camera2Config.defaultConfig()
    }
}