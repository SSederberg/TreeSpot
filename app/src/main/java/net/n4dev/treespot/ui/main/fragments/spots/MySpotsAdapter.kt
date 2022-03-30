package net.n4dev.treespot.ui.main.fragments.spots

import android.content.Context
import android.net.Uri
import android.os.Bundle
import io.objectbox.query.Query
import net.n4dev.treespot.BR
import net.n4dev.treespot.core.AbstractEntityAdapter
import net.n4dev.treespot.db.entity.TreeSpot
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

        ActivityUtil.startActivity(bundle, TreeSpotDetailActivity::class.java, holder.itemView.context)
    }

    override fun onBindItem(holder: MySpotViewHolder, entity: TreeSpot, position: Int) {
        holder.xmlBinding.aiTreespotGmap.setOnClickListener {
            forwardToGMaps(entity, holder.itemView.context)
        }
    }

    override fun onNoItemsAvailable(holder: MySpotViewHolder?) {

    }

    private fun forwardToGMaps(entity: TreeSpot, context: Context) {
        var formatted = ""
        var uri: Uri? = null
        formatted = entity.getLatNorth() + "," + entity.getLongWest() + "(" + entity.getDescription() + ")"
        uri = Uri.parse("geo:0,0?q=$formatted")
        ActivityUtil.goToGoogleMaps(context, uri)
    }


}
