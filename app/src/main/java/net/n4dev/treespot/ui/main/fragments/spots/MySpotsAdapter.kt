package net.n4dev.treespot.ui.main.fragments.spots

import android.content.Context
import android.os.Bundle
import com.bumptech.glide.Glide
import io.objectbox.query.Query
import net.n4dev.treespot.BR
import net.n4dev.treespot.core.AbstractEntityAdapter
import net.n4dev.treespot.db.entity.TreeSpot
import net.n4dev.treespot.db.query.GetLocationMediaQuery
import net.n4dev.treespot.ui.spots.detail.TreeSpotDetailActivity
import net.n4dev.treespot.util.ActivityUtil

class MySpotsAdapter(holder: MySpotViewHolder, query : Query<TreeSpot>)
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

        ActivityUtil.startActivity(bundle, TreeSpotDetailActivity::class.java, holder.itemView.context)
    }

    override fun onBindItem(holder: MySpotViewHolder, entity: TreeSpot, position: Int) {
        holder.xmlBinding.aiTreespotGmap.setOnClickListener {
            ActivityUtil.forwardToGMaps(entity, holder.itemView.context)
        }

        val mediaQuery = GetLocationMediaQuery.get(entity.getSpotID()).find()

        if(mediaQuery.size > 0) {
            val media = mediaQuery[0]
//            val preview = media.getImageAsBitMap(holder.itemView.context)

            Glide.with(holder.itemView.context)
                .asBitmap()
                .load(media.getMediaPath())
                .into(holder.xmlBinding.aiTreespotPreview)

        }
    }

    override fun onNoItemsAvailable(holder: MySpotViewHolder?) {

    }

}
