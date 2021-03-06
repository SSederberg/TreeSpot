package net.n4dev.treespot.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import net.n4dev.treespot.BuildConfig
import net.n4dev.treespot.R
import net.n4dev.treespot.core.entity.Friend
import net.n4dev.treespot.core.entity.TreeSpot
import net.n4dev.treespot.databinding.ActivitySplashBinding
import net.n4dev.treespot.db.TreeSpotObjectBox
import net.n4dev.treespot.ui.account.RegisterAccountActivity
import net.n4dev.treespot.ui.main.MainActivity
import net.n4dev.treespot.util.ActivityUtil
import net.n4dev.treespot.util.DeviceConnectionHelper
import net.n4dev.treespot.viewmodel.UserAuthorizedViewModel
import java.io.File
import java.util.*


class SplashActivity : TreeSpotActivity() {

    private val TAG = "Splash_Treebase"
    private lateinit var binding : ActivitySplashBinding
    private lateinit var userAuthorizedViewModel: UserAuthorizedViewModel

    override fun buildFromBundle(bundle: Bundle) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        initializeFolders()
        initFirebase()
//        TreeSpotObjectBox.purgeStores()
        Logger.addLogAdapter(AndroidLogAdapter(developmentFormatStrategy))
        setContentView(binding.root)
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
                    ActivityUtil.startActivity(MainActivity::class.java, this)
                } else {
                    ActivityUtil.startActivity(RegisterAccountActivity::class.java, this)
                }

            } else if (savedVersionCode == DOESNT_EXIST) {
                // TODO This is a new install (or the user cleared the shared preferences)

                if(DeviceConnectionHelper.getConnectionType(this) > 0) {
                    ActivityUtil.startActivity(RegisterAccountActivity::class.java, this)
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
        val friendImagesFolder = File(ActivityUtil.getAppFriendImagesDirectory(this))

        imageFolder.mkdirs()
        videoFolder.mkdirs()
        friendImagesFolder.mkdirs()
    }

    private fun doesUserAccountExist() : Boolean {
        val box = super.getBox(net.n4dev.treespot.core.entity.User::class.java)
        val usersExist = box.all.size > 0
        return usersExist
    }

    private fun userIsAuthorized(): Boolean {
        try {
            val users = super.getBox(net.n4dev.treespot.core.entity.User::class.java).all

            if(users.size == 0) {
                return false
            } else {
                val user = users[0]
                if(DeviceConnectionHelper.getConnectionType(this) > 0) {

                    //TODO Verify session is still valid before returning true

                    val storedSession = user.getCurrentSessionID()
                    return userAuthorizedViewModel.isAuthorized(storedSession);
                } else {
                    // Since we can't verify the user since they are offline,
                    // we will have to trust them until they go online.
                    return user.getCurrentSessionID() != null
                }
            }
        }catch (exception : Exception) {
            exception.printStackTrace()
        }

        return false
    }

    private fun initFirebase() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new FCM registration token
                val token = task.result

                // Log and toast
                val msg = token;
                Log.d(TAG, msg)
//                Toast.makeText(this@SplashActivity, msg, Toast.LENGTH_SHORT).show()
            })

        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("ts_not_id", name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

    }

    private fun generateSampleData() {

        val friendBox = TreeSpotObjectBox.getBoxStore().boxFor(Friend::class.java)
        val spotBox = TreeSpotObjectBox.getBoxStore().boxFor(TreeSpot::class.java)

        try {

            val personalID = super.getBox(net.n4dev.treespot.core.entity.User::class.java).all.get(0).getUserID()

            for(i in 0..10) {
                val loopFriend = Friend()
                val loopSpot = TreeSpot()

                val removeRandom = (1000..1000000).random()
                val randomNorth = (0..999999).random()
                val randomWest = (0..999999).random()

                loopFriend.setFriendID(UUID.randomUUID())
                loopFriend.setUserID(UUID.randomUUID())
                loopFriend.setFriendsSince(System.currentTimeMillis() - removeRandom)
                loopFriend.setFriendPairID(personalID);
                loopFriend.setUsername("Friend #" + i)
                loopFriend.setAccountCreationDate(System.currentTimeMillis() - removeRandom)
                loopFriend.setEmailAddress("friend$i@test.net")


                loopSpot.setCreationDate(System.currentTimeMillis() - removeRandom)
                loopSpot.setDescription("Description for Spot #" + i)
                loopSpot.setPrivateDescription("Private Description for Spot #" + i)
                loopSpot.setSpotOwnerID(personalID.toString())
                loopSpot.setSpotID(UUID.randomUUID().toString())
                loopSpot.setLongWest("-91." + randomWest)
                loopSpot.setLatNorth("47." + randomNorth)

                friendBox.put(loopFriend)
                spotBox.put(loopSpot)
            }
        }catch (ex : java.lang.Exception) {
            ex.printStackTrace()
        }
    }

}