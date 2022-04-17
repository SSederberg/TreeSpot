package net.n4dev.treespot.ui.main.fragments.spots

import android.content.Context
import android.os.Bundle
import com.bumptech.glide.Glide
import net.n4dev.treespot.BR
import net.n4dev.treespot.core.AbstractEntityAdapter
import net.n4dev.treespot.core.AbstractQuery
import net.n4dev.treespot.core.entity.TreeSpot
import net.n4dev.treespot.core.entity.TreeSpotMedia
import net.n4dev.treespot.db.TreeSpotObjectBox
import net.n4dev.treespot.db.query.GetLocationMediaQuery
import net.n4dev.treespot.ui.spots.detail.TreeSpotDetailActivity
import net.n4dev.treespot.util.ActivityUtil

class MySpotsAdapter(holder: MySpotViewHolder, query : AbstractQuery<TreeSpot>, val requestedID : String)
    : AbstractEntityAdapter<TreeSpot, MySpotViewHolder>(holder, query, BR.myTreeSpot, false, TreeSpot::class.java) {


    override fun onItemSelected(
        holder: MySpotViewHolder,
        entity: TreeSpot,
        context: Context,
        position: Int
    ) {

        val bundle = Bundle()
        bundle.putString(TreeSpotDetailActivity.ARG_LOCATION_ID, entity.getSpotID())
        bundle.putString(TreeSpotDetailActivity.ARG_USER_TYPE, TreeSpotDetailActivity.ARG_USER)
        bundle.putString(TreeSpotDetailActivity.ARG_REQUESTED_BY_ID, requestedID)

        ActivityUtil.startActivity(bundle, TreeSpotDetailActivity::class.java, holder.itemView.context, false)
    }

    override fun onBindItem(holder: MySpotViewHolder, entity: TreeSpot, position: Int) {
        holder.xmlBinding.aiTreespotGmap.setOnClickListener {
            ActivityUtil.forwardToGMaps(entity, holder.itemView.context)
        }

        val mediaQuery = GetLocationMediaQuery(entity.getSpotID())

        val mediaBox = TreeSpotObjectBox.getBox(TreeSpotMedia::class.java)

        val queryResult = mediaBox.query(mediaQuery.buildQuery()).build().find()

        if(queryResult.size > 0) {
            val media = queryResult[0]

            Glide.with(holder.itemView.context)
                .asBitmap()
                .load(media.getMediaPath())
                .into(holder.xmlBinding.aiTreespotPreview)

        }
    }

    override fun onNoItemsAvailable(holder: MySpotViewHolder?) {

    }

}
