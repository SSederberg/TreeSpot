package net.n4dev.treespot.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import net.n4dev.treespot.BuildConfig
import net.n4dev.treespot.databinding.ActivitySplashBinding
import net.n4dev.treespot.db.queries.GetUserQuery
import net.n4dev.treespot.ui.account.RegisterAccountActivity
import net.n4dev.treespot.ui.main.MainActivity
import net.n4dev.treespot.util.ActivityUtil
import net.n4dev.treespot.util.DeviceConnectionHelper
import net.n4dev.treespot.viewmodel.UserAuthorizedViewModel
import java.io.File


class SplashActivity : TreeSpotActivity() {

    private lateinit var binding : ActivitySplashBinding
    private lateinit var userAuthorizedViewModel: UserAuthorizedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        Logger.addLogAdapter(AndroidLogAdapter(developmentFormatStrategy))
        setContentView(binding.root)

        initializeFolders()

        userAuthorizedViewModel = ViewModelProvider(this).get(UserAuthorizedViewModel::class.java)
        userAuthorizedViewModel.init(this)

        performFirstRunCheck()
    }

    private fun performFirstRunCheck() {
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
                  ActivityUtil.toast(this, "A internet connection is required for new installs and after resetting preferences!", true)
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
        val prefs = getSharedPreferences()
        try {
            val username = prefs.getString(PREF_ACTIVE_USERNAME_ID, null)
            val session = prefs.getString(PREF_ACTIVE_SESSION_ID, null)
            val jwt = prefs.getString(PREF_ACTIVE_JWT, null)

            return username != null && session != null && jwt != null
        }catch (exception : Exception) {
            exception.printStackTrace()
        }

        return false
    }

    private fun userIsAuthorized(): Boolean {
        try {
            val prefs = getSharedPreferences()
            val username : String = prefs.getString(PREF_ACTIVE_USERNAME_ID, null) as String

            val query = GetUserQuery.get(username)
            val users = super.loadUser(query)
            if(DeviceConnectionHelper.isConnected(applicationContext)) {

                //TODO Verify session is still valid before returning true
                    val user = users[0]
                    val storedSession = user.getCurrentSessionID()
                return userAuthorizedViewModel.isAuthorized(storedSession);
            } else {
                // Since we can't verify the user since they are offline,
                // we will have to trust them until they go online.
                return users.size > 0
            }
        }catch (exception : Exception) {
            exception.printStackTrace()
        }

        return false
    }
}