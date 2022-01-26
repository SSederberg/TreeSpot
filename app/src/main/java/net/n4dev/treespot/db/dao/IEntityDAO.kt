package net.n4dev.treespot.db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.RawQuery
import io.zeko.db.sql.Query

interface IEntityDAO<E> {

    @Insert fun insert(obj : E)

    @RawQuery fun find(query : Query)

    @Delete fun delete(obj: E)
}