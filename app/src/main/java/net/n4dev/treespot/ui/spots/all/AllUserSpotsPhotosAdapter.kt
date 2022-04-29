package net.n4dev.treespot.ui.spots.all

import android.content.Context
import com.bumptech.glide.Glide
import net.n4dev.treespot.BR
import net.n4dev.treespot.core.AbstractEntityAdapter
import net.n4dev.treespot.core.AbstractQuery
import net.n4dev.treespot.core.entity.TreeSpotMedia

class AllUserSpotsPhotosAdapter(viewHolder: AllUserSpotsPhotosViewHolder, query : AbstractQuery<TreeSpotMedia>)
    : AbstractEntityAdapter<TreeSpotMedia, AllUserSpotsPhotosViewHolder>(viewHolder, query, BR.media, false, TreeSpotMedia::class.java) {


    override fun onItemSelected(
        holder: AllUserSpotsPhotosViewHolder,
        entity: TreeSpotMedia,
        context: Context,
        position: Int
    ) {

    }

    override fun onBindItem(
        holder: AllUserSpotsPhotosViewHolder,
        entity: TreeSpotMedia,
        position: Int
    ) {

        Glide.with(holder.itemView.context)
            .asBitmap()
            .load(entity.getImageAsBitMap(holder.itemView.context))
            .into(holder.xmlBinding.spotMediaImage)
    }

    override fun onNoItemsAvailable(holder: AllUserSpotsPhotosViewHolder) {
    }


}