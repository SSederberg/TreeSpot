package net.n4dev.treespot.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.orhanobut.logger.Logger
import io.appwrite.services.Database
import kotlinx.coroutines.coroutineScope
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.db.constants.TreeSpotsConstants

class UploadTreeSpotWorker(val context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    val client = TreeSpotApplication.getClient(context)
    private var awDatabase: Database = Database(client)

    override suspend fun doWork(): Result = coroutineScope {
       try {
           awDatabase.createDocument(
               TreeSpotsConstants.name,
               "unique()",
               inputData.keyValueMap,
               listOf("role:member"),
               listOf("role:member")
           )

       }catch (e : Exception) {
           Logger.e(e, "")
           Result.failure()
       }

        Result.success()
    }
}