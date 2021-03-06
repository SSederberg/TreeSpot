package net.n4dev.treespot.ui.friends.detail

import android.content.Context
import android.os.Bundle
import net.n4dev.treespot.BR
import net.n4dev.treespot.core.AbstractEntityAdapter
import net.n4dev.treespot.core.AbstractQuery
import net.n4dev.treespot.core.entity.TreeSpot
import net.n4dev.treespot.ui.spots.detail.TreeSpotDetailActivity
import net.n4dev.treespot.util.ActivityUtil


class FriendDetailSpotsAdapter(holder: FriendDetailSpotsViewHolder, query : AbstractQuery<TreeSpot>, val requestedID : String)
    : AbstractEntityAdapter<TreeSpot, FriendDetailSpotsViewHolder>(holder, query, BR.myTreeSpot, false, TreeSpot::class.java) {


    override fun onItemSelected(
        holder: FriendDetailSpotsViewHolder,
        entity: TreeSpot,
        context: Context,
        position: Int
    ) {
        val bundle = Bundle()
        bundle.putString(TreeSpotDetailActivity.ARG_LOCATION_ID, entity.getSpotID())
        bundle.putString(TreeSpotDetailActivity.ARG_USER_TYPE, TreeSpotDetailActivity.ARG_FRIEND)
        bundle.putString(TreeSpotDetailActivity.ARG_REQUESTED_BY_ID, requestedID)

        ActivityUtil.startActivity(bundle, TreeSpotDetailActivity::class.java, holder.itemView.context, false)
    }

    override fun onBindItem(
        holder: FriendDetailSpotsViewHolder,
        entity: TreeSpot,
        position: Int
    ) {
        holder.xmlBinding.aiTreespotGmap.setOnClickListener {
            ActivityUtil.forwardToGMaps(entity, holder.itemView.context)
        }
    }

    override fun onNoItemsAvailable(holder: FriendDetailSpotsViewHolder) {

    }
}