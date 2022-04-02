package net.n4dev.treespot.core.api

import android.content.Context
import android.graphics.Bitmap

interface ITreeSpotMedia : IEntity {

    fun isVideo() : Boolean
    fun isPicture() : Boolean

    fun isUploaded() : Boolean

    fun getSpotID() : String

    fun getUserID() : String

    fun getMediaPath() : String

    fun getMediaFileName() : String

    fun getMediaCreationDate() : Long

    fun getMediaID() : String

    fun getImageAsBitMap(context: Context) : Bitmap
}