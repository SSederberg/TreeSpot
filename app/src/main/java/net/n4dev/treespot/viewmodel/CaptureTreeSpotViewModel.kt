package net.n4dev.treespot.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import io.appwrite.services.Databases
import io.appwrite.services.Storage
import kotlinx.coroutines.launch
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.AppwriteViewModel
import java.io.File

class CaptureTreeSpotViewModel : AppwriteViewModel() {

    private lateinit var awDatabase: Databases
    private lateinit var awStorage : Storage

    override fun init(context: Context) {
        val client = TreeSpotApplication.getClient(context)

        awDatabase = super.getAppWriteDatabase(context, "NULL")
        awStorage = Storage(client)
    }

    fun save(file : File) {
        viewModelScope.launch {

        }
    }
}