package net.n4dev.treespot.core.api

interface ITreeSpot : IEntity {

    fun getSpotID() :  String
    fun setSpotID(uuid: String)

    fun setSpotOwnerID(userID : String)
    fun getSpotOwnerID() : String

    fun getCreationDate() : Long
    fun setCreationDate(date: Long)

    fun getLatNorth() : String
    fun setLatNorth(string: String)

    fun getLongWest() : String
    fun setLongWest(string: String)

    fun getDescription() : String
    fun setDescription(description : String)

    fun getPrivateDescription() : String
    fun setPrivateDescription(string : String)
}