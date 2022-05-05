package net.n4dev.treespot.worker

import android.content.Context
import android.net.Uri
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.orhanobut.logger.Logger
import io.appwrite.services.Database
import io.appwrite.services.Storage
import kotlinx.coroutines.coroutineScope
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.db.constants.TreeSpotMediaConstants
import net.n4dev.treespot.db.constants.TreeSpotsConstants
import java.io.File

class UploadTreeSpotWorker(val context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    val client = TreeSpotApplication.getClient(context)
    private var awDatabase: Database = Database(client)
    private var awStorage : Storage = Storage(client)

    override suspend fun doWork(): Result = coroutineScope {

        val spotData = mapOf()
        val mediaData = mapOf()

       try {
           awDatabase.createDocument(
               TreeSpotsConstants.name,
               "unique()",
               spotData,
               listOf("role:member"),
               listOf("role:member")
           )

           awDatabase.createDocument(TreeSpotMediaConstants.name,
           "unique()",
           mediaData,
               listOf("role:member"),
               listOf("role:member")
           )
           val uri = inputData.keyValueMap[TreeSpotMediaConstants.DEVICE_PATH] as String

           val file = File(uri)
           awStorage.createFile("", "unique()", file)
       }catch (e : Exception) {
           Logger.e(e, "")
           Result.failure()
       }

        Result.success()
    }
}