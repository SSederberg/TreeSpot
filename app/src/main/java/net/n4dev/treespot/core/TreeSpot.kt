package net.n4dev.treespot.core

import net.n4dev.treespot.core.api.ITreeSpot
import net.n4dev.treespot.core.api.IUser
import java.util.*

class TreeSpot : ITreeSpot {

    private lateinit var creationDate: Date
    private lateinit var spotUUID: UUID
    private lateinit var spotOwner : IUser
    private lateinit var latNorth : String
    private lateinit var longWest : String


    override fun getSpotID(): UUID {
        return spotUUID
    }

    override fun setSpotID(uuid: UUID) {
        this.spotUUID = uuid
    }

    override fun setSpotOwner(user: IUser) {
        this.spotOwner = user
    }

    override fun getSpotOwner(): IUser {
        return  spotOwner
    }

    override fun getSpotCreationDate(): Date {
        return creationDate
    }

    override fun setSpotCreationDate(date: Date) {
        this.creationDate = date
    }

    override fun getLatNorth(): String {
        return latNorth
    }

    override fun setLatNorth(string: String) {
        this.latNorth = string
    }

    override fun getLongWest(): String {
        return longWest
    }

    override fun setLongWest(string: String) {
        this.longWest = string
    }

    override fun getType(): String {
        return TypeConst.TREESPOT
    }


    override fun getEntityID(): String {
        return this.spotUUID.toString()
    }

    override fun isUser(): Boolean {
        return false
    }

    override fun isTreeSpot(): Boolean {
        return true
    }

}