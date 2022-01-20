package net.n4dev.treespot.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.n4dev.treespot.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

    }
}