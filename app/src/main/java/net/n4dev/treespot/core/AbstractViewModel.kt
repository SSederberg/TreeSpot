package net.n4dev.treespot.core

import android.content.Context
import androidx.lifecycle.ViewModel
import io.appwrite.Client
import io.appwrite.services.Avatars
import io.appwrite.services.Database
 import io.objectbox.Box
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.api.IEntity
import net.n4dev.treespot.core.api.IViewModel
import net.n4dev.treespot.db.TreeSpotObjectBox

abstract class AbstractViewModel : ViewModel(), IViewModel {

     fun getAppWriteClient(context: Context) : Client {
        return TreeSpotApplication.getClient(context)
    }

    fun getAppWriteDatabase(context: Context) : Database {
        val client = getAppWriteClient(context)
        return Database(client)
    }

    fun getAppWriteAvatars(context: Context) : Avatars {
        val client = TreeSpotApplication.getClient(context)
        return Avatars(client)
    }

    fun <T : IEntity> getBox(klass : Class<T>): Box<T> {
        return TreeSpotObjectBox.getBoxStore().boxFor(klass)
    }

    fun getMemberRole() : List<String> {
        return listOf("role:member")
    }
}