package net.n4dev.treespot.db;

import java.util.UUID;

import io.objectbox.converter.PropertyConverter;

public class UUIDConverter implements PropertyConverter<UUID, String> {

    @Override
    public UUID convertToEntityProperty(String databaseValue) {
        return UUID.fromString(databaseValue);
    }

    @Override
    public String convertToDatabaseValue(UUID entityProperty) {
        return entityProperty.toString();
    }
}
