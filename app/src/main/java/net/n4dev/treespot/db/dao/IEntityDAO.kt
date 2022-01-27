package net.n4dev.treespot.db.dao

import androidx.room.*
import io.zeko.db.sql.Query
import net.n4dev.treespot.core.api.IEntity

interface IEntityDAO<E> {

    @Insert fun insert(obj : E)

    @RawQuery fun find(query : Query) : ArrayList<IEntity>

    @RawQuery fun find(string: String) : ArrayList<IEntity>

    @Delete fun delete(obj: E)
}