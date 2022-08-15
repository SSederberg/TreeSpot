package net.n4dev.treespot.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.orhanobut.logger.Logger
import io.appwrite.services.Databases
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.db.constants.TreeSpotFavoriteConstants

class UploadFavoriteTreeSpotWorker(val context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {

    companion object {
        const val TAG = "TS_UPLOAD_FAV_SPOT_WORKER"
    }

    val client = TreeSpotApplication.getClient(context)
    private val awDatabase: Databases = Databases(client, TreeSpotFavoriteConstants.name)

    override suspend fun doWork(): Result {
        val ownerID = inputData.getString(TreeSpotFavoriteConstants.SPOT_OWNER_ID)
        val spotID = inputData.getString(TreeSpotFavoriteConstants.SPOT_UUID)
        val favUserID = inputData.getString(TreeSpotFavoriteConstants.SPOT_FAV_USER_ID)
        val favDate = inputData.getLong(TreeSpotFavoriteConstants.SPOT_FAV_DATE, System.currentTimeMillis())

        val inputData = mapOf(
            TreeSpotFavoriteConstants.SPOT_UUID to spotID,
            TreeSpotFavoriteConstants.SPOT_OWNER_ID to ownerID,
            TreeSpotFavoriteConstants.SPOT_FAV_USER_ID to favUserID,
            TreeSpotFavoriteConstants.SPOT_FAV_DATE to favDate
        )

        try {
            awDatabase.createDocument(
                TreeSpotFavoriteConstants.name,
                "unique()",
                inputData,
                WorkerUtil.getMemberRole(),
                WorkerUtil.getMemberRole())

            return Result.success()
        } catch (ex : Exception) {
            Logger.e(ex, "")
            return Result.failure()
        }
    }
}