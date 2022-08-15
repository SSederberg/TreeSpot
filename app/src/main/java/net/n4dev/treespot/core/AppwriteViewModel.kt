package net.n4dev.treespot.core

import android.content.Context
import io.appwrite.Client
import io.appwrite.services.Avatars
import io.appwrite.services.Databases
import io.objectbox.Box
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.api.IEntity
import net.n4dev.treespot.core.api.IViewModel
import net.n4dev.treespot.db.TreeSpotObjectBox

/**
 * View model object used to make App Write API calls, due to how the
 * SDK is designed, it requires a suspend function to work, so we use
 * viewmodel scopes to use them.
 */
abstract class AppwriteViewModel : AbstractViewModel(), IViewModel {

    fun getAppWriteClient(context: Context) : Client {
        return TreeSpotApplication.getClient(context)
    }

    fun getAppWriteDatabase(context: Context, databaseId : String) : Databases {
        val client = getAppWriteClient(context)
        return Databases(client, databaseId)
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

    override fun init(context: Context) {

    }
}