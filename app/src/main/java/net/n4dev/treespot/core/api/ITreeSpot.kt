package net.n4dev.treespot.core.api

import java.util.UUID
import java.util.Date

interface ITreeSpot : IEntity {

    fun getSpotID() :  UUID
    fun setSpotID(uuid: UUID)

    fun setSpotOwnerID(user : IUser)
    fun getSpotOwnerID() : UUID

    fun getSpotCreationDate() : Date
    fun setSpotCreationDate(date: Date)

    fun getLatNorth() : String
    fun setLatNorth(string: String)

    fun getLongWest() : String
    fun setLongWest(string: String)

    fun getSpotDescription() : String
    fun setSpotDescription(description : String)
}