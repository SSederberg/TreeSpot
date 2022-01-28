package net.n4dev.treespot.db

import androidx.room.TypeConverter
import java.util.*

class UUIDConverter {

    @TypeConverter
    fun fromString(string: String) : UUID {
        return UUID.fromString(string)
    }

    @TypeConverter
    fun toString(uuid: UUID) : String {
        return uuid.toString()
    }
}