package net.n4dev.treespot.db

import io.objectbox.converter.PropertyConverter
import java.util.*

class UUIDConverter : PropertyConverter<UUID, String?> {
    override fun convertToEntityProperty(databaseValue: String?): UUID {
        return UUID.fromString(databaseValue)
    }

    override fun convertToDatabaseValue(entityProperty: UUID): String {
        return entityProperty.toString()
    }
}