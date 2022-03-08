package net.n4dev.treespot.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import io.appwrite.services.Database
import io.appwrite.services.Storage
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.api.ITreeSpot
import net.n4dev.treespot.core.api.IViewModel
import net.n4dev.treespot.db.TreeSpotDatabases

class AddSpotViewModel : ViewModel(), IViewModel {

    private lateinit var awDatabase: io.appwrite.services.Database
    private lateinit var awStorage: Storage
    private lateinit var couchbase : TreeSpotDatabases
    private val treespotImagesBucketID = "ts_spot_images"

    override fun init(context: Context) {
        val client = TreeSpotApplication.getClient(context)
        awDatabase = Database(client)
        awStorage = Storage(client)

        couchbase = TreeSpotDatabases()
        couchbase.init(context)
    }

    fun addSpot(spot : ITreeSpot) {

    }
}