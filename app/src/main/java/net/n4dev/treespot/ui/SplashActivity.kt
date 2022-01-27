package net.n4dev.treespot.ui

import android.os.Bundle
import androidx.room.Room
import io.zeko.db.sql.Query
import net.n4dev.treespot.BuildConfig
import net.n4dev.treespot.core.User
import net.n4dev.treespot.core.api.IUser
import net.n4dev.treespot.databinding.ActivitySplashBinding
import net.n4dev.treespot.db.TreeSpotDatabase
import net.n4dev.treespot.util.ActivityUtil
import net.n4dev.treespot.util.DeviceConnectionHelper
import java.io.File


class SplashActivity : TreeSpotActivity() {

    private lateinit var binding : ActivitySplashBinding
    private val PREFS_NAME = "TreeSpotPrefsFile"
    private val PREF_VERSION_CODE_KEY = "version_code"
    private val DOESNT_EXIST = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room.databaseBuilder(this, TreeSpotDatabase::class.java, "treespot")
            .build()

        initializeFolders()
        performFirstRunCheck()
    }

    private fun performFirstRunCheck() {
        val currentVersionCode = BuildConfig.VERSION_CODE
        var prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST)

        // Check for first run or upgrade
        if (currentVersionCode == savedVersionCode) {
            // This is just a normal run

                if(doesUserAccountExist()) {
                    ActivityUtil.startActivity(MainActivity::class.java, this)
                } else {
                    ActivityUtil.startActivity(RegisterAccountActivity::class.java, this)
                }
            return;

        } else if (savedVersionCode == DOESNT_EXIST) {
            // TODO This is a new install (or the user cleared the shared preferences)

            if(DeviceConnectionHelper.isConnected(this)) {
                ActivityUtil.startActivity(RegisterAccountActivity::class.java, this)
            } else {
                if(doesUserAccountExist()) {

                } else {

                }
            }

        } else if (currentVersionCode > savedVersionCode) {

            // TODO This is an upgrade
        }

        // Update the shared preferences with the current version code
        prefs.edit().putInt(PREF_VERSION_CODE_KEY, currentVersionCode).apply();
    }

    /**
     * Establish app folder structure for media storage
     */
    private fun initializeFolders() {
        val imageFolder  = File(ActivityUtil.getAppImagesDirectory(this))
        val videoFolder = File(ActivityUtil.getAppVideosDirectory(this))

        imageFolder.mkdirs()
        videoFolder.mkdirs()
    }

    private fun doesUserAccountExist() : Boolean {
        val userExistsQuery = Query().fields("*")
            .from(User.name)

        val returnedList = super.loadUser(userExistsQuery)

        return returnedList.size != 0

    }
}