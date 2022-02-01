package net.n4dev.treespot

import android.app.Application
import com.parse.Parse

class TreeSpotApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Parse.initialize(parseDevConfiguration)
    }

    private val parseDevConfiguration = Parse.Configuration.Builder(this)
        .applicationId("treespot-dev")
        .server("http://192.168.1.245:1337/parse/")
        .build()
}