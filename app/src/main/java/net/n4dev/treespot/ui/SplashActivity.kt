package net.n4dev.treespot.ui

import android.os.Bundle
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import io.appwrite.exceptions.AppwriteException
import io.appwrite.extensions.toJson
import io.appwrite.services.Account
import io.zeko.db.sql.dsl.isNotNull
import kotlinx.coroutines.*
import net.n4dev.treespot.BuildConfig
import net.n4dev.treespot.databinding.ActivitySplashBinding
import net.n4dev.treespot.util.ActivityUtil
import net.n4dev.treespot.util.DeviceConnectionHelper
import java.io.File


class SplashActivity : TreeSpotActivity() {

    private lateinit var binding : ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        Logger.addLogAdapter(AndroidLogAdapter(developmentFormatStrategy))
        setContentView(binding.root)

        initializeFolders()
        performFirstRunCheck()
    }

    private fun performFirstRunCheck() {
        GlobalScope.launch {
            val currentVersionCode = BuildConfig.VERSION_CODE
            val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            val savedVersionCode = prefs.getInt(PREF_VERSION_CODE_KEY, DOESNT_EXIST)

            // Check for first run or upgrade
            if (currentVersionCode == savedVersionCode) {
                // This is just a normal run

                if(doesUserAccountExist() && userIsAuthorized()) {
                    ActivityUtil.startActivity(MainActivity::class.java, applicationContext)
                } else {
                    ActivityUtil.startActivity(RegisterAccountActivity::class.java, applicationContext)
                }

            } else if (savedVersionCode == DOESNT_EXIST) {
                // TODO This is a new install (or the user cleared the shared preferences)

                if(DeviceConnectionHelper.isConnected(applicationContext)) {
                    ActivityUtil.startActivity(RegisterAccountActivity::class.java, applicationContext)
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
        val prefs = getSharedPreferences()
        try {
            val username = prefs.getString(PREF_ACTIVE_USERNAME_ID, null)
            val session = prefs.getString(PREF_ACTIVE_SESSION_ID, null)

            return username != null && session != null
        }catch (exception : Exception) {
            exception.printStackTrace()
        }

        return false
    }

    private suspend fun userIsAuthorized(): Boolean {
        val prefs = getSharedPreferences()

        try {
            val username : String = prefs.getString(PREF_ACTIVE_USERNAME_ID, null) as String
            val session: String = prefs.getString(PREF_ACTIVE_SESSION_ID, null) as String

            if(username != null && session != null) {
                val users = Account(getAppWrite())
                //TODO Verify session is still valid before returning true
//                val userSessionResponse = users.getSession(sessionId = session)
                return true
            }
        }catch (exception : AppwriteException) {
            exception.printStackTrace()
        }

        return false
    }
}