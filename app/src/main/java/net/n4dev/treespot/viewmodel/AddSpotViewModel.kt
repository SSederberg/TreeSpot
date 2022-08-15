package net.n4dev.treespot.viewmodel

import android.content.Context
import androidx.lifecycle.viewModelScope
import androidx.work.Data
import io.appwrite.services.Databases
import io.appwrite.services.Storage
import io.objectbox.Box
import kotlinx.coroutines.launch
import net.n4dev.treespot.core.AppwriteViewModel
import net.n4dev.treespot.core.api.ITreeSpot
import net.n4dev.treespot.core.api.ITreeSpotMedia
import net.n4dev.treespot.core.entity.TreeSpot
import net.n4dev.treespot.core.entity.TreeSpotMedia
import net.n4dev.treespot.db.constants.TreeSpotMediaConstants
import net.n4dev.treespot.db.constants.TreeSpotsConstants
import net.n4dev.treespot.worker.UploadTreeSpotWorker
import net.n4dev.treespot.worker.WorkerUtil

class AddSpotViewModel : AppwriteViewModel() {

    private lateinit var spotBox: Box<TreeSpot>
    private lateinit var mediaBox: Box<TreeSpotMedia>

    private lateinit var awDatabase: Databases
    private lateinit var awStorage: Storage

    override fun init(context: Context) {
        spotBox = super.getBox(TreeSpot::class.java)
        mediaBox = super.getBox(TreeSpotMedia::class.java)

        awDatabase = super.getAppWriteDatabase(context, TreeSpotsConstants.name)
        awStorage = Storage(super.getAppWriteClient(context))
    }

    fun addSpot(spot: ITreeSpot, media: List<ITreeSpotMedia>, context: Context) {
        //Local saving
        spotBox.put(spot as TreeSpot)

        media.forEach {
            mediaBox.put(it as TreeSpotMedia)
        }

        viewModelScope.launch {

            for (toUpload in media) {
                val mediaData = mapOf(
                    TreeSpotMediaConstants.SPOT_ID to toUpload.getSpotID(),
                    TreeSpotMediaConstants.MEDIA_TYPE to toUpload.getType(),
                    TreeSpotMediaConstants.USER_ID to toUpload.getUserID(),
                    TreeSpotMediaConstants.DEVICE_PATH to toUpload.getMediaPath(),
                    TreeSpotMediaConstants.FILENAME to toUpload.getMediaFileName(),
                    TreeSpotMediaConstants.MEDIA_ID to toUpload.getMediaID(),
                    TreeSpotMediaConstants.TAKEN_AT to toUpload.getMediaCreationDate()
                )

//                awDatabase.createDocument(
//                    TreeSpotMediaConstants.name,
//                    toUpload.getMediaID(),
//                    mediaData,
//                    listOf("role:member"),
//                    listOf("role:member")
//                )
//
//                val uri = Uri.parse(toUpload.getMediaPath())
//                val file = File(uri.path!!)
//
//                awStorage.createFile("ts_bucket_pictures",
//                    media.getMediaID(),
//                    file)
//            }
//
                val spotData = mapOf(
                    TreeSpotsConstants.SPOT_CREATION_DATE to spot.getCreationDate(),
                    TreeSpotsConstants.SPOT_DESCRIPTION to spot.getDescription(),
                    TreeSpotsConstants.SPOT_LAT_NORTH to spot.getLatNorth(),
                    TreeSpotsConstants.SPOT_LONG_WEST to spot.getLongWest(),
                    TreeSpotsConstants.SPOT_OWNER_ID to spot.getSpotOwnerID(),
                    TreeSpotsConstants.SPOT_UUID to spot.getSpotID(),
                    TreeSpotsConstants.SPOT_PRIVATE_DESCRIPTION to spot.getPrivateDescription()
                )

                val spotsData = Data.Builder()
                    .putAll(spotData)
                    .putAll(mediaData)
                    .build()

                val uploadWorker = WorkerUtil.generateOneTimeWorkRequest(
                    UploadTreeSpotWorker::class.java,
                    spotsData,
                    WorkerUtil.networkRequiredConstraints,
                    TreeSpotsConstants.WORKER_UPLOAD_SPOT
                )

                WorkerUtil.enqueueWork(context, uploadWorker)
            }
        }

        fun addSpot(spot: ITreeSpot, context: Context) {
            spotBox.put(spot as TreeSpot)
            viewModelScope.launch {
                val spotData = mapOf(
                    TreeSpotsConstants.SPOT_CREATION_DATE to spot.getCreationDate(),
                    TreeSpotsConstants.SPOT_DESCRIPTION to spot.getDescription(),
                    TreeSpotsConstants.SPOT_LAT_NORTH to spot.getLatNorth(),
                    TreeSpotsConstants.SPOT_LONG_WEST to spot.getLongWest(),
                    TreeSpotsConstants.SPOT_OWNER_ID to spot.getSpotOwnerID(),
                    TreeSpotsConstants.SPOT_UUID to spot.getSpotID(),
                    TreeSpotsConstants.SPOT_PRIVATE_DESCRIPTION to spot.getPrivateDescription()
                )

                val spotsData = Data.Builder()
                    .putAll(spotData)
                    .build()

                val uploadWorker = WorkerUtil.generateOneTimeWorkRequest(
                    UploadTreeSpotWorker::class.java,
                    spotsData,
                    WorkerUtil.networkRequiredConstraints,
                    TreeSpotsConstants.WORKER_UPLOAD_SPOT
                )

                WorkerUtil.enqueueWork(context, uploadWorker)
            }
        }
    }
}