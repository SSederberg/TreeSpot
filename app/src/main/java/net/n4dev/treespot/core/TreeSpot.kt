package net.n4dev.treespot.core

import androidx.room.Entity
import androidx.room.Fts4
import net.n4dev.treespot.core.api.ITreeSpot
import net.n4dev.treespot.core.api.IUser
import java.util.*

@Fts4
@Entity
class TreeSpot(
    private var spotOwner: IUser,
    private var latNorth: String,
    private var longWest: String
) : ITreeSpot {

    private var creationDate: Date
    private var spotUUID: UUID
    private lateinit var description: String


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

    override fun getSpotDescription(): String {
        return description
    }

    override fun setSpotDescription(description: String) {
        this.description = description
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

    init {
        this.spotUUID = UUID.randomUUID()
        this.creationDate = Date()
    }

}