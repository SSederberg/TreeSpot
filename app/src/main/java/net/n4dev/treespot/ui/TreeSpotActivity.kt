package net.n4dev.treespot.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import io.zeko.db.sql.Query
import net.n4dev.treespot.core.api.IEntity
import net.n4dev.treespot.core.api.ITreeSpot
import net.n4dev.treespot.core.api.IUser
import net.n4dev.treespot.db.TreeSpotDatabase
import net.n4dev.treespot.db.dao.IEntityDAO


open class TreeSpotActivity : AppCompatActivity() {

    private var db : TreeSpotDatabase? = null

    fun loadUser(query: Query) : ArrayList<IUser> {
        var returnedList: ArrayList<IEntity>
        val convertedList : ArrayList<IUser> = ArrayList()

        val loadThread = Thread {
            returnedList = getDatabase().userDAO().find(query.toSql())

            returnedList.forEach {
                convertedList.add(it as IUser)
            }
        }

        loadThread.start()
        loadThread.join()

        return convertedList
    }

    fun loadTreeSpot(query: Query) : ArrayList<ITreeSpot> {
        var returnedList: ArrayList<IEntity>
        val convertedList : ArrayList<ITreeSpot> = ArrayList()

        val loadThread = Thread {
            returnedList = getDatabase().treeSpotDAO().find(query.toSql())

            returnedList.forEach {
                convertedList.add(it as ITreeSpot)
            }
        }

        loadThread.start()
        loadThread.join()

        return convertedList

    }

    fun getUserDB() : IEntityDAO<IUser> {
        return getDatabase().userDAO()
    }

    fun getTreeSpotDB() : IEntityDAO<ITreeSpot>{
        return getDatabase().treeSpotDAO()
    }

    private fun getDatabase() : TreeSpotDatabase {
        if(db == null) {

        }

        return db as TreeSpotDatabase
    }
}