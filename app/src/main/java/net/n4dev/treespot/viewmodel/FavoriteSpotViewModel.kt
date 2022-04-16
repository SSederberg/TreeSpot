package net.n4dev.treespot.viewmodel

import android.content.Context
import androidx.work.Data
import io.objectbox.Box
import net.n4dev.treespot.core.AbstractViewModel
import net.n4dev.treespot.core.entity.FavoriteSpot
import net.n4dev.treespot.core.entity.TreeSpot
import net.n4dev.treespot.db.TreeSpotObjectBox
import net.n4dev.treespot.db.constants.TreeSpotFavoriteConstants
import net.n4dev.treespot.db.query.GetSingleFavoriteSpotQuery
import net.n4dev.treespot.worker.UploadFavoriteTreeSpotWorker
import net.n4dev.treespot.worker.WorkerUtil

class FavoriteSpotViewModel : AbstractViewModel() {

    private lateinit var favoriteBox : Box<FavoriteSpot>

    override fun init(context: Context) {
        favoriteBox = TreeSpotObjectBox.getBox(FavoriteSpot::class.java)

    }

    fun addFavoriteSpot(spot : TreeSpot, requestedUserID : String, context: Context) {

        val currentTime =  System.currentTimeMillis()
        val favoriteSpot = FavoriteSpot(
            spot.getSpotID(),
            spot.getSpotOwnerID(),
            requestedUserID,
           currentTime
        )

        favoriteBox.put(favoriteSpot)

        val workData = Data.Builder()
            .putString(TreeSpotFavoriteConstants.SPOT_UUID, spot.getSpotID())
            .putString(TreeSpotFavoriteConstants.SPOT_OWNER_ID, spot.getSpotOwnerID())
            .putString(TreeSpotFavoriteConstants.SPOT_FAV_USER_ID, requestedUserID)
            .putLong(TreeSpotFavoriteConstants.SPOT_FAV_DATE, currentTime)
            .build()

        val favoriteSpotWorkRequest = WorkerUtil.generateOneTimeWorkRequest(
            UploadFavoriteTreeSpotWorker::class.java,
            workData,
            WorkerUtil.networkRequiredConstraints,
            UploadFavoriteTreeSpotWorker.TAG)

        WorkerUtil.enqueueWork(context, favoriteSpotWorkRequest)
    }

    fun removeFavoriteSpot(spot: TreeSpot, requestedUserID: String, context: Context) {
        val singleSpotQuery = GetSingleFavoriteSpotQuery.get(spot.getSpotID())
        val singleSpot = singleSpotQuery.findFirst()

        favoriteBox.remove(singleSpot!!.localID)
    }
}