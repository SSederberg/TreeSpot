package net.n4dev.treespot.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.viewModelScope
import io.appwrite.services.Database
import io.appwrite.services.Storage
import io.objectbox.Box
import kotlinx.coroutines.launch
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.core.AbstractViewModel
import net.n4dev.treespot.core.api.ITreeSpot
import net.n4dev.treespot.db.constants.TreeSpotMediaConstants
import net.n4dev.treespot.db.constants.TreeSpotsConstants
import net.n4dev.treespot.db.entity.TreeSpot
import net.n4dev.treespot.db.entity.TreeSpotMedia
import java.io.File

class AddSpotViewModel : AbstractViewModel() {

    private lateinit var awDatabase: io.appwrite.services.Database
    private lateinit var awStorage: Storage
    private lateinit var awFile : io.appwrite.models.File
    private lateinit var spotBox : Box<TreeSpot>
    private lateinit var mediaBox : Box<TreeSpotMedia>

    override fun init(context: Context) {
        val client = TreeSpotApplication.getClient(context)
        awDatabase = Database(client)
        awStorage = Storage(client)

        spotBox = super.getBox(TreeSpot::class.java)
        mediaBox = super.getBox(TreeSpotMedia::class.java)
    }

    fun addSpot(spot : ITreeSpot, media : List<TreeSpotMedia>) {
        //Local saving
        spotBox.put(spot as TreeSpot)
        mediaBox.put(media)

        viewModelScope.launch {

            for(toUpload in media) {
                val mediaData = mapOf(
                    TreeSpotMediaConstants.SPOT_ID to toUpload.getSpotID(),
                    TreeSpotMediaConstants.MEDIA_TYPE to toUpload.getType(),
                    TreeSpotMediaConstants.USER_ID to toUpload.getUserID(),
                    TreeSpotMediaConstants.DEVICE_PATH to toUpload.getMediaPath(),
                    TreeSpotMediaConstants.FILENAME to toUpload.getMediaFileName(),
                    TreeSpotMediaConstants.MEDIA_ID to toUpload.getMediaID(),
                    TreeSpotMediaConstants.TAKEN_AT to toUpload.getMediaCreationDate()
                )

                awDatabase.createDocument(
                    TreeSpotMediaConstants.name,
                    "unique()",
                    mediaData,
                    listOf("role:member"),
                    listOf("role:member")
                )

                val uri = Uri.parse(toUpload.getMediaPath())
                val file = File(uri.path!!)

                awStorage.createFile(toUpload.getMediaID(),
                file,
                getMemberRole(),
                getMemberRole())
            }

            val spotData = mapOf(
                TreeSpotsConstants.SPOT_CREATION_DATE to spot.getCreationDate(),
                TreeSpotsConstants.SPOT_DESCRIPTION to spot.getDescription(),
                TreeSpotsConstants.SPOT_LAT_NORTH to spot.getLatNorth(),
                TreeSpotsConstants.SPOT_LONG_WEST to spot.getLongWest(),
                TreeSpotsConstants.SPOT_OWNER_ID to spot.getSpotOwnerID(),
                TreeSpotsConstants.SPOT_UUID to spot.getSpotID(),
                TreeSpotsConstants.SPOT_PRIVATE_DESCRIPTION to spot.getPrivateDescription()
            )

            awDatabase.createDocument(
                TreeSpotsConstants.name,
                "unique()",
                spotData,
                listOf("role:member"),
                listOf("role:member")
            )

        }
    }
}