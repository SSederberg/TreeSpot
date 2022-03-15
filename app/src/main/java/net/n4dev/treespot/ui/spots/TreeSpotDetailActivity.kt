package net.n4dev.treespot.ui.spots

import android.os.Bundle
import net.n4dev.treespot.databinding.ActivityTreeSpotDetailBinding
import net.n4dev.treespot.ui.TreeSpotActivity

class TreeSpotDetailActivity : TreeSpotActivity() {

    companion object {
        const val ARG_LOCATION_ID = "ARG_LOCATION_ID"
    }

    private lateinit var binding : ActivityTreeSpotDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTreeSpotDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}