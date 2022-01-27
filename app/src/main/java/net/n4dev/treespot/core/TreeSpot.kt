package net.n4dev.treespot.core

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import net.n4dev.treespot.core.TreeSpot.Companion.name
import net.n4dev.treespot.core.api.ITreeSpot
import net.n4dev.treespot.core.api.IUser
import java.util.*

@Fts4
@Entity(tableName = name)
class TreeSpot(
    @ColumnInfo(name = SPOT_OWNER) private var spotOwnerID: UUID,
    @ColumnInfo(name = SPOT_LAT_NORTH) private var latNorth: String,
    @ColumnInfo(name = SPOT_LONG_WEST) private var longWest: String
) : ITreeSpot {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = LOCAL_UID) var localUID = 0

    @ColumnInfo(name = CREATION_DATE) private val creationDate: Date = Date()
    @ColumnInfo(name = SPOT_UUID) private val spotUUID: UUID = UUID.randomUUID()
    @ColumnInfo(name = SPOT_DESCRIPTION) private lateinit var description: String

    companion object {
        const val LOCAL_UID = "localuid"
        const val CREATION_DATE = "CREATION_DATE"
        const val SPOT_UUID = "SPOT_UUID"
        const val SPOT_OWNER = "SPOT_OWNER"
        const val SPOT_DESCRIPTION = "SPOT_DESCRIPTION"
        const val SPOT_LAT_NORTH = "SPOT_LAT_NORTH"
        const val SPOT_LONG_WEST = "SPOT_LONG_WEST"
        const val name = "TREESPOT_SPOT"
    }

    override fun getSpotID(): UUID {
        return spotUUID
    }

    override fun setSpotID(uuid: UUID) {}

    override fun setSpotOwnerID(user: IUser) {
        this.spotOwnerID = user.getUserID()
    }

    override fun getSpotOwnerID(): UUID {
        return  spotOwnerID
    }

    override fun getSpotCreationDate(): Date {
        return creationDate
    }

    override fun setSpotCreationDate(date: Date) { }

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
}