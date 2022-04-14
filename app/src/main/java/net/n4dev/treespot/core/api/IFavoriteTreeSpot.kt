package net.n4dev.treespot.core.api

interface IFavoriteTreeSpot : IEntity {

    /**
     * The UUID of the spot
     */
    fun getSpotID() :  String
    fun setSpotID(uuid: String)

    /**
     * The user UUID of the spot creator.
     */
    fun setSpotOwnerID(userID : String)
    fun getSpotOwnerID() : String


    fun getFavoriteUserID() : String
    fun setFavoriteUserID(userID: String)

    fun getFavoriteDate() : Long
    fun setFavoriteDate(dateLong : Long)

}