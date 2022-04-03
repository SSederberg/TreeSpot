package net.n4dev.treespot.ui.spots

import android.os.Bundle
import net.n4dev.treespot.databinding.ActivityShareSpotBinding
import net.n4dev.treespot.ui.TreeSpotActivity

class ShareSpotActivity : TreeSpotActivity() {

    private lateinit var binding : ActivityShareSpotBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShareSpotBinding.inflate(layoutInflater)

        if(intent.extras != null) {
            buildFromBundle(intent.extras!!)
        }else if(savedInstanceState != null) {
            buildFromBundle(savedInstanceState)
        }

        setContentView(binding.root)
    }

    override fun buildFromBundle(bundle: Bundle) {
        TODO("Not yet implemented")
    }
}