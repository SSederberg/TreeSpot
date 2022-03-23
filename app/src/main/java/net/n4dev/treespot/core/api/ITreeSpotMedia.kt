package net.n4dev.treespot.core.api

interface ITreeSpotMedia : IEntity {

    fun isVideo() : Boolean
    fun isPicture() : Boolean

    fun isUploaded() : Boolean

    fun getSpotID() : String

    fun getTakenByUserID() : String

    fun getMediaPath() : String
}