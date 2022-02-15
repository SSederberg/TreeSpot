package net.n4dev.treespot.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import com.google.android.material.navigation.NavigationBarView
import com.orhanobut.logger.Logger
import net.n4dev.treespot.R
import net.n4dev.treespot.databinding.ActivityMainBinding
import net.n4dev.treespot.ui.TreeSpotActivity
import net.n4dev.treespot.ui.friends.AddFriendsActivity
import net.n4dev.treespot.ui.main.fragments.capture.CaptureSpotFragment
import net.n4dev.treespot.ui.main.fragments.capture.CaptureSpotFragmentDirections
import net.n4dev.treespot.ui.main.fragments.friends.MyFriendsFragment
import net.n4dev.treespot.ui.main.fragments.friends.MyFriendsFragmentDirections
import net.n4dev.treespot.ui.main.fragments.spots.MySpotsFragment
import net.n4dev.treespot.ui.main.fragments.spots.MySpotsFragmentDirections
import net.n4dev.treespot.ui.settings.SettingsActivity
import net.n4dev.treespot.util.ActivityUtil

class MainActivity : TreeSpotActivity(), NavigationBarView.OnItemSelectedListener, NavController.OnDestinationChangedListener {

    private lateinit var binding : ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager
    private var activeMenu = R.menu.menu_main_friends
    private var currentFragment : Fragment =  CaptureSpotFragment()
//    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mainIncludeTopbar.mainAppbarBar)

        fragmentManager = supportFragmentManager
        binding.mainBottomNavigation.setOnItemSelectedListener(this)

//       val navHostFragment = fragmentManager.findFragmentById(R.id.main_fragment_container) as NavHostFragment
//        navController = navHostFragment.navController
//
//        binding.mainBottomNavigation.setupWithNavController(navController)
//        navController.addOnDestinationChangedListener(this)

        fragmentManager.commit {
            replace<CaptureSpotFragment>(R.id.main_fragment_container)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(activeMenu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(activeMenu, menu)
        return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {

            R.id.bottom_nav_friends -> {
                navigateToMyFriends(currentFragment)

                activeMenu = R.menu.menu_main_friends
                invalidateOptionsMenu()
                currentFragment = MyFriendsFragment()
                return true
            }

            R.id.bottom_nav_my_spots -> {
                navigateToMySpots(currentFragment)

                activeMenu = R.menu.menu_main_spots
                invalidateOptionsMenu()
                currentFragment = MySpotsFragment()
                return true
            }

            R.id.bottom_nav_take_spot -> {

                navigateToCaptureSpots(currentFragment)

                activeMenu = R.menu.menu_main_capture_spot
                invalidateOptionsMenu()
                currentFragment = CaptureSpotFragment()
                return true
            }
        }

        return false
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemID = item.itemId;

        if(itemID == R.id.menu_main_capture_settings) {
            ActivityUtil.startActivity(SettingsActivity::class.java, this)
        } else if(itemID == R.id.menu_main_friends_add) {
            ActivityUtil.startActivity(AddFriendsActivity::class.java, this)
        }
        return true
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        Logger.i("" + destination.id + " " + destination.navigatorName)
    }

    private fun navigateToMySpots(currentFragment: Fragment) {
        if(currentFragment is MyFriendsFragment) {
//             NavHostFragment.findNavController(binding.mainFragmentContainer.getFragment())
            val action = MyFriendsFragmentDirections.actionMyFriendsFragmentToMySpotsFragment()
//            navController.navigate(action)
        }else if(currentFragment is CaptureSpotFragment) {
            val action = CaptureSpotFragmentDirections.actionCaptureSpotFragmentToMySpotsFragment()
//            navController.navigate(action)
        }
    }

    private fun navigateToCaptureSpots(currentFragment: Fragment) {
        if(currentFragment is MySpotsFragment) {
            val action = MySpotsFragmentDirections.actionMySpotsFragmentToCaptureSpotFragment()
//            navController.navigate(action)
        } else if(currentFragment is MyFriendsFragment) {
            val action = MyFriendsFragmentDirections.actionMyFriendsFragmentToCaptureSpotFragment()
//            navController.navigate(action)
        }
    }

    private fun navigateToMyFriends(currentFragment: Fragment) {
        if(currentFragment is MySpotsFragment) {
            val action = MySpotsFragmentDirections.actionMySpotsFragmentToMyFriendsFragment()
//            navController.navigate(action)
        } else if(currentFragment is CaptureSpotFragment) {
            val action = CaptureSpotFragmentDirections.actionCaptureSpotFragmentToMyFriendsFragment()
//            navController.navigate(action)
        }
    }

}