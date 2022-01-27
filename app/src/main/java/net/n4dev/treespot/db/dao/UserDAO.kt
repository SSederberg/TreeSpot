package net.n4dev.treespot.db.dao

import androidx.room.Dao
import net.n4dev.treespot.core.api.IUser

@Dao
abstract class UserDAO : IEntityDAO<IUser> {

}