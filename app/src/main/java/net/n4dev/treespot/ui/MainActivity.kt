package net.n4dev.treespot.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.navigation.NavigationBarView
import net.n4dev.treespot.R
import net.n4dev.treespot.databinding.ActivityMainBinding
import net.n4dev.treespot.ui.fragments.MyFriendsFragment
import net.n4dev.treespot.ui.fragments.MySpotsFragment

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding : ActivityMainBinding
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentManager = supportFragmentManager
        binding.mainBottomNavigation.setOnItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {

            R.id.bottom_nav_friends -> {
                fragmentManager.commit {
                    replace<MyFriendsFragment>(R.id.main_fragment_container)
                }
                return true
            }

            R.id.bottom_nav_spots -> {
            fragmentManager.commit {
                    replace<MySpotsFragment>(R.id.main_fragment_container)
                }
                return true
            }

            R.id.bottom_nav_take_spot -> {
                fragmentManager.commit {
                    replace<MyFriendsFragment>(R.id.main_fragment_container)
                }
                return true
            }
        }

        return false
    }


}