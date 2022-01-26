package net.n4dev.treespot.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.RawQuery
import io.zeko.db.sql.Query
import net.n4dev.treespot.core.api.IUser

abstract class UserDAO : IEntityDAO<IUser> {

    @Insert abstract override fun insert(obj: IUser)

    @RawQuery abstract override fun find(query: Query)

    @Delete abstract override fun delete(obj: IUser)

}