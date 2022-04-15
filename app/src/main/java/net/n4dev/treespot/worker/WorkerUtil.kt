package net.n4dev.treespot.worker

import android.content.Context
import androidx.annotation.Nullable
import androidx.work.*
import java.util.concurrent.TimeUnit

class WorkerUtil {

    companion object {

        val networkRequiredConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val uploadVideoConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val UNIQUE_TAG = "TREESPOT_WORKER"

        fun generateOneTimeWorkRequest(klass: Class<out CoroutineWorker>, @Nullable inputData: Data?, constraints: Constraints ,uniqueTag : String) : OneTimeWorkRequest {
            if(inputData == null) {
                return OneTimeWorkRequest.Builder(klass)
                    .setConstraints(constraints)
                    .setInitialDelay(1, TimeUnit.SECONDS)
                    .addTag(UNIQUE_TAG)
                    .addTag(uniqueTag)
                    .build()
            } else {
                return OneTimeWorkRequest.Builder(klass)
                    .setConstraints(constraints)
                    .setInitialDelay(1, TimeUnit.SECONDS)
                    .addTag(UNIQUE_TAG)
                    .addTag(uniqueTag)
                    .setInputData(inputData)
                    .build()
            }
        }

        fun enqueueWork(context: Context, workRequest: OneTimeWorkRequest) {
            WorkManager.getInstance(context).enqueue(workRequest)
        }

        fun beginChainedWork(context: Context, workRequest: OneTimeWorkRequest) : WorkContinuation  {
           return WorkManager.getInstance(context).beginWith(workRequest)
        }

        fun getMemberRole() : List<String> {
            return listOf("role:member")
        }
    }
}