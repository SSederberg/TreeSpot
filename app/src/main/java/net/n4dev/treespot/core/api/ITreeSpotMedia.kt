package net.n4dev.treespot.core.api

import android.content.Context
import android.graphics.Bitmap

interface ITreeSpotMedia : IEntity {

    fun isVideo() : Boolean
    fun isPicture() : Boolean

    fun isUploaded() : Boolean

    /**
     * The Spot UUID for this location media.
     */
    fun getSpotID() : String

    /**
     * The user ID of the user who has captured it.
     */
    fun getUserID() : String

    /**
     * A URI path represented as a String
     */
    fun getMediaPath() : String

    /**
     * The filename itself for this piece of media.
     */
    fun getMediaFileName() : String

    /**
     * The Unix timestamp of when this piece of media was created.
     */
    fun getMediaCreationDate() : Long

    /**
     * The UUID for this piece of media.
     */
    fun getMediaID() : String

    fun getImageAsBitMap(context: Context) : Bitmap
}