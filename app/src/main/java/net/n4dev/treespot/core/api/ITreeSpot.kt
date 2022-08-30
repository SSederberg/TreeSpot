package net.n4dev.treespot.core.api

interface ITreeSpot : IEntity {

    /**
     * The UUID of the spot
     */
    fun getSpotID(): String
    fun setSpotID(uuid: String)

    /**
     * The user UUID of the spot creator.
     */
    fun setSpotOwnerID(userID: String)
    fun getSpotOwnerID(): String

    /**
     * The Unix timestamp of the spot creation date.
     */
    fun getCreationDate(): Long
    fun setCreationDate(date: Long)

    /**
     * The latitude of the location, stored in String form, but is a decimal.
     */
    fun getLatNorth(): String
    fun setLatNorth(string: String)

    /**
     * The longitude of the location, stored in String form, but is a decimal.
     */
    fun getLongWest(): String
    fun setLongWest(string: String)

    /**
     * The public description of the tree spot
     */
    fun getDescription(): String
    fun setDescription(description: String)

    /**
     * The Owner only visible description of this spot.
     */
    fun getPrivateDescription(): String?
    fun setPrivateDescription(string: String)

    fun getSpotPhotos() : List<ITreeSpotMedia>
}