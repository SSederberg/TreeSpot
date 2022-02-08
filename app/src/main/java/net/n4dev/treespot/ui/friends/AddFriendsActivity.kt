package net.n4dev.treespot.ui.friends

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.n4dev.treespot.databinding.ActivityAddFriendsBinding

class AddFriendsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddFriendsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}