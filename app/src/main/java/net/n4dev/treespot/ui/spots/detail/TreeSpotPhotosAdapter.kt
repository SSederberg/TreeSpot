package net.n4dev.treespot.ui.spots.detail

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.bumptech.glide.Glide
import io.objectbox.query.Query
import net.n4dev.treespot.BR
import net.n4dev.treespot.core.AbstractEntityAdapter
import net.n4dev.treespot.db.entity.TreeSpotMedia
import java.io.FileNotFoundException
import java.io.InputStream

class TreeSpotPhotosAdapter(viewHolder: TreeSpotPhotosViewHolder, query : Query<TreeSpotMedia>
) : AbstractEntityAdapter<TreeSpotMedia, TreeSpotPhotosViewHolder>(viewHolder, query, BR.media, false, TreeSpotMedia::class.java){


    override fun onItemSelected(
        holder: TreeSpotPhotosViewHolder?,
        entity: TreeSpotMedia?,
        context: Context?,
        position: Int
    ) {

    }

    override fun onBindItem(
        holder: TreeSpotPhotosViewHolder,
        entity: TreeSpotMedia,
        position: Int
    ) {
      val uri = Uri.parse(entity.getMediaPath())

        Glide.with(holder.itemView.context)
            .asBitmap()
            .load(uri)
            .into(holder.xmlBinding.spotMediaImage)

    }

    override fun onNoItemsAvailable(holder: TreeSpotPhotosViewHolder?) {

    }

    private fun decodeUriToBitmap(sendUri: Uri, contentResolver : ContentResolver): Bitmap? {
        var getBitmap: Bitmap? = null
        try {
            val image_stream: InputStream
            try {
                image_stream = contentResolver.openInputStream(sendUri)!!
                getBitmap = BitmapFactory.decodeStream(image_stream)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return getBitmap
    }
}