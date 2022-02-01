package net.n4dev.treespot.ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.orhanobut.logger.PrettyFormatStrategy
import io.zeko.db.sql.Query
import net.n4dev.treespot.core.TreeSpot
import net.n4dev.treespot.core.User
import net.n4dev.treespot.db.TreeSpotDatabase


open class TreeSpotActivity : AppCompatActivity() {

    private var db : TreeSpotDatabase? = null
    val PREFS_NAME = "TreeSpotPrefsFile"
    val PREF_VERSION_CODE_KEY = "version_code"
    val DOESNT_EXIST = -1
    val PREF_ACTIVE_USERNAME_ID = "active_username"
    val PREF_ACTIVE_SESSION_ID = "active_session"
    val PREF_ACTIVE_JWT = "active_jwt"

    val developmentFormatStrategy = PrettyFormatStrategy.newBuilder()
        .showThreadInfo(true)
        .tag("TreeSpot")
        .methodCount(3)
        .build()

    fun loadUser(query: Query) : ArrayList<User> {
        var returnedList: ArrayList<User> = ArrayList()
        val sql = query.toSql()

        val loadThread = Thread {

        }

        loadThread.start()
        loadThread.join()

        return returnedList
    }

    fun loadTreeSpot(query: Query) : ArrayList<TreeSpot> {
        var returnedList: ArrayList<TreeSpot> = ArrayList()

        val loadThread = Thread {

        }

        loadThread.start()
        loadThread.join()

        return returnedList

    }


    fun getSharedPreferences() : SharedPreferences{
        return getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
    }
}