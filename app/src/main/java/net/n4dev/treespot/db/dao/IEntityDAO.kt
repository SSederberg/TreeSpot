package net.n4dev.treespot.db.dao

import androidx.room.*
import io.zeko.db.sql.Query
import net.n4dev.treespot.core.api.IEntity

interface IEntityDAO<E> {

     fun insert(obj : E)

    fun find(query : Query) : ArrayList<E>

     fun find(string: String) : ArrayList<E>

    fun delete(obj: E)
}