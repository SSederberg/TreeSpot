package net.n4dev.treespot.ui

import android.Manifest
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.couchbase.lite.DataSource
import com.couchbase.lite.Expression
import com.couchbase.lite.QueryBuilder
import com.couchbase.lite.SelectResult
import com.orhanobut.logger.PrettyFormatStrategy
import net.n4dev.treespot.core.TreeSpot
import net.n4dev.treespot.core.User
import net.n4dev.treespot.db.TreeSpotDatabases


open class TreeSpotActivity : AppCompatActivity() {

    private val db = TreeSpotDatabases()

    val developmentFormatStrategy = PrettyFormatStrategy.newBuilder()
        .showThreadInfo(true)
        .tag("TreeSpot")
        .methodCount(5)
        .build()

    fun loadUser(string : String) : ArrayList<User> {
        var returnedList: ArrayList<User> = ArrayList()
        val query = QueryBuilder.select(SelectResult.all())
            .from(DataSource.database(db.userDB))
            .where(Expression.property("")
                .equalTo(Expression.property("")));

        val loadThread = Thread {
            val resultSet = query.execute();

        }

        loadThread.start()
        loadThread.join()

        return returnedList
    }

    fun loadTreeSpot(string : String) : ArrayList<TreeSpot> {
        var returnedList: ArrayList<TreeSpot> = ArrayList()

        val loadThread = Thread {

        }

        loadThread.start()
        loadThread.join()

        return returnedList

    }


    fun getSharedPreferences() : SharedPreferences {
        return getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
    }

    companion object {
        val TREESPOT_PERMISSIONS = arrayOf(Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_NETWORK_STATE)

        val PREFS_NAME = "TreeSpotPrefsFile"
        val PREF_VERSION_CODE_KEY = "version_code"
        val DOESNT_EXIST = -1
        val PREF_ACTIVE_USERNAME_ID = "active_username"
        val PREF_ACTIVE_SESSION_ID = "active_session"
        val PREF_ACTIVE_JWT = "active_jwt"
    }
}