package net.n4dev.treespot.viewmodel

import android.content.Context
 import androidx.lifecycle.viewModelScope
import io.appwrite.services.Database
import io.appwrite.services.Storage
import kotlinx.coroutines.launch
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.AbstractViewModel
import java.io.File

class CaptureTreeSpotViewModel : AbstractViewModel() {

    private lateinit var awDatabase: Database
    private lateinit var awStorage : Storage

    override fun init(context: Context) {
        val client = TreeSpotApplication.getClient(context)

        awDatabase = super.getAppWriteDatabase(context)
        awStorage = Storage(client)
    }

    fun save(file : File) {
        viewModelScope.launch {

        }
    }
}