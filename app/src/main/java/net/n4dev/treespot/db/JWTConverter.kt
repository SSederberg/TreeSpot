package net.n4dev.treespot.db

import androidx.room.TypeConverter
import io.appwrite.models.Jwt

class JWTConverter {

    @TypeConverter
    fun toString(jwt: Jwt) : String {
        return jwt.jwt;
    }

    @TypeConverter
    fun fromString(string: String) : Jwt {
        return Jwt(string)
    }
}