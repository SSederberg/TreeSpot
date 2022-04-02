package net.n4dev.treespot.ui.main

import android.Manifest
import android.content.IntentSender
import android.os.Bundle
import android.view.Menu
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.orhanobut.logger.Logger
import net.n4dev.treespot.R
import net.n4dev.treespot.core.ZoomOutPageTransformer
import net.n4dev.treespot.databinding.ActivityMainBinding
import net.n4dev.treespot.db.entity.User
import net.n4dev.treespot.db.query.GetSingleUserQuery
import net.n4dev.treespot.ui.TreeSpotActivity
import net.n4dev.treespot.ui.friends.add.AddFriendsActivity
import net.n4dev.treespot.ui.main.fragments.capture.CaptureSpotFragment
import net.n4dev.treespot.ui.main.fragments.friends.MyFriendsFragment
import net.n4dev.treespot.ui.main.fragments.spots.MySpotsFragment
import net.n4dev.treespot.ui.settings.SettingsActivity
import net.n4dev.treespot.util.ActivityUtil
import net.n4dev.treespot.util.GPSUtils


class MainActivity : TreeSpotActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager

    private lateinit var mySpotsFragment : Fragment
    private lateinit var captureSpotFragment : Fragment
    private lateinit var myFriendsFragment : Fragment

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val REQUEST_CHECK_SETTINGS = 2327

    private var user : User? = null

    companion object {
        const val ARG_USER_ID = "ARG_USER_ID"
        const val ARG_USER_EMAIL = "ARG_USER_NAME"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        if(intent.extras != null) {
            buildFromBundle(intent.extras!!)
        } else if(savedInstanceState != null) {
            buildFromBundle(savedInstanceState)
        }

        val id = user?.getUserID()
        mySpotsFragment = MySpotsFragment(id.toString())
        captureSpotFragment = CaptureSpotFragment(id.toString())
        myFriendsFragment = MyFriendsFragment(id.toString())

        locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)



        binding.mainIncludeTopbar.mainAppbarBar.setOnMenuItemClickListener { menuItem ->
            val itemID = menuItem.itemId

            if(itemID == R.id.menu_main_capture_settings) {
                ActivityUtil.startActivity(SettingsActivity::class.java, this)
            } else if(itemID == R.id.menu_main_friends_add) {
                val bundle = Bundle()
                bundle.putString(AddFriendsActivity.ARG_USER_ID, user!!.getUserID().toString())
                ActivityUtil.startActivity(bundle, AddFriendsActivity::class.java, this)
            } else false

             true
        }
        setContentView(binding.root)

        setupViewPager()
        fragmentManager = supportFragmentManager

        GPSUtils.checkSettings(this)
            .addOnFailureListener { exception ->
                if (exception is ResolvableApiException){
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        exception.startResolutionForResult(this@MainActivity, REQUEST_CHECK_SETTINGS)
                    } catch (sendEx: IntentSender.SendIntentException) {
                        // Ignore the error.
                        Logger.e(sendEx, "")
                    }
                }


            }
            .addOnSuccessListener { Logger.i("SUCCESS!") }
    }

    override fun buildFromBundle(bundle: Bundle) {
        val userID = bundle.getString(ARG_USER_ID)

        getUserFromDB(userID!!)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.clear()
        menuInflater.inflate(R.menu.menu_main_capture_spot, menu)
        return true
    }

    private fun setupViewPager() {
        val pagerAdapter = MainPagerAdapter(this)
        binding.mainFragmentViewpager.adapter = pagerAdapter
        binding.mainFragmentViewpager.currentItem = 1
        binding.mainFragmentViewpager.setPageTransformer(ZoomOutPageTransformer())

        binding.mainFragmentViewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

               when(position) {
                   0 -> {
                       binding.mainIncludeTopbar.mainAppbarBar.title = "My Tree Spots"
                       binding.mainIncludeTopbar.mainAppbarBar.menu.getItem(1).isVisible = false
                   }
                   1 -> {
                       binding.mainIncludeTopbar.mainAppbarBar.title = ""
                       binding.mainIncludeTopbar.mainAppbarBar.menu.getItem(1).isVisible = false
                   }
                   2 -> {
                       binding.mainIncludeTopbar.mainAppbarBar.title = "My Friends"

                       binding.mainIncludeTopbar.mainAppbarBar.menu.getItem(1).isVisible = true
                   }

                   else -> {
                       Logger.e("Failed to OnPageSelected, position was $position")
                   }
               }
            }

        })
    }

    private fun getUserFromDB(userID : String) {
      try {
        val query = GetSingleUserQuery.getFromUser(userID).find()
          user = query[0]
      }catch (ex : Exception) {
          ex.printStackTrace()
          user = null
      }

    }

    val locationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                // Precise location access granted.
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Only approximate location access granted.
            } else -> {
            // No location access granted.
        }
        }

    }

    private inner class MainPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

        private val DETAIL_PAGES = 3

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> mySpotsFragment
                1 -> captureSpotFragment
                2 -> myFriendsFragment
                else -> {
                    Logger.e("Position given by createFragment was not in range! Position: $position")
                    captureSpotFragment
                }
            }
        }

        override fun getItemCount(): Int {
            return DETAIL_PAGES
        }



    }
}