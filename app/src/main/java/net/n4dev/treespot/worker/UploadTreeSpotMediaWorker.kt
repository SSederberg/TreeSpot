package net.n4dev.treespot.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class UploadTreeSpotMediaWorker(val context: Context, workerParams: WorkerParameters) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        TODO("Not yet implemented")
    }
}