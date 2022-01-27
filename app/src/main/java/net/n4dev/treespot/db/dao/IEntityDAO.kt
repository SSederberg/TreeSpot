package net.n4dev.treespot.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.RawQuery
import io.zeko.db.sql.Query
import net.n4dev.treespot.core.api.IEntity

@Dao
interface IEntityDAO<E> {

    @Insert fun insert(obj : E)

    @RawQuery fun find(query : Query) : ArrayList<IEntity>

    @RawQuery fun find(string: String) : ArrayList<IEntity>

    @Delete fun delete(obj: E)
}