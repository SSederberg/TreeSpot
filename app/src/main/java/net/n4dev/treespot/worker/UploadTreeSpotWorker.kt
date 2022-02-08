package net.n4dev.treespot.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class UploadTreeSpotWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {


    override fun doWork(): Result {
        TODO("Not yet implemented")
    }
}