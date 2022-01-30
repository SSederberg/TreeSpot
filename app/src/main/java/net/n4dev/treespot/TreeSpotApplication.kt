package net.n4dev.treespot

import android.app.Application
import android.content.Context
import io.appwrite.Client

class TreeSpotApplication: Application() {

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