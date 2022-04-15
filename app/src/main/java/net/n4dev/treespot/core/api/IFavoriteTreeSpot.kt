package net.n4dev.treespot.core.api

interface IFavoriteTreeSpot : ITreeSpot {


    fun getFavoriteUserID() : String
    fun setFavoriteUserID(userID: String)

    fun getFavoriteDate() : Long
    fun setFavoriteDate(dateLong : Long)

}