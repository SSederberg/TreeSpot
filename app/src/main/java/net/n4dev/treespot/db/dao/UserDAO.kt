package net.n4dev.treespot.db.dao

import androidx.annotation.Nullable
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.RawQuery
import io.zeko.db.sql.Query
import net.n4dev.treespot.core.User


@Dao
abstract class UserDAO : IEntityDAO<User> {

    @Insert
    abstract override fun insert(obj: User)

    override fun find(query: Query): ArrayList<User> {

        val sql = query.toSql()

        val returnedEntities = getUsers(sql)


        return returnedEntities
    }

    @RawQuery
    abstract fun getUsers(string: String) : ArrayList<User>

    @Delete
    abstract override fun delete(obj: User)
}