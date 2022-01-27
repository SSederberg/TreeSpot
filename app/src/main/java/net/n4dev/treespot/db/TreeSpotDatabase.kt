package net.n4dev.treespot.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import net.n4dev.treespot.core.TreeSpot
import net.n4dev.treespot.core.User
import net.n4dev.treespot.db.dao.TreeSpotDAO
import net.n4dev.treespot.db.dao.UserDAO

@Database(entities = [User::class, TreeSpot::class], version = 0, exportSchema = false)
abstract class TreeSpotDatabase : RoomDatabase() {

    abstract fun userDAO(): UserDAO
    abstract fun treeSpotDAO() : TreeSpotDAO

}