package net.n4dev.treespot.ui.friends

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.n4dev.treespot.databinding.ActivityFriendDetailBinding

class FriendDetailActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFriendDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}