package net.n4dev.treespot.viewmodel

import android.content.Context
import io.appwrite.services.Database
import io.appwrite.services.Storage
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.AbstractViewModel
import net.n4dev.treespot.core.api.ITreeSpot

class AddSpotViewModel : AbstractViewModel() {

    private lateinit var awDatabase: io.appwrite.services.Database
    private lateinit var awStorage: Storage
    private val treespotImagesBucketID = "ts_spot_images"

    override fun init(context: Context) {
        val client = TreeSpotApplication.getClient(context)
        awDatabase = Database(client)
        awStorage = Storage(client)
    }

    fun addSpot(spot : ITreeSpot) {

    }
}