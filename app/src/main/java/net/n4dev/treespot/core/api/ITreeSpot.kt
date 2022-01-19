package net.n4dev.treespot.core.api

import java.util.UUID
import java.util.Date

open interface ITreeSpot : IEntity {

    fun getSpotID() :  UUID
    fun setSpotID(uuid: UUID)

    fun setSpotOwner(user : IUser)
    fun getSpotOwner() : IUser

    fun getSpotCreationDate() : Date
    fun setSpotCreationDate(date: Date)

    fun getLatNorth() : String
    fun setLatNorth(string: String)

    fun getLongWest() : String
    fun setLongWest(string: String)
}