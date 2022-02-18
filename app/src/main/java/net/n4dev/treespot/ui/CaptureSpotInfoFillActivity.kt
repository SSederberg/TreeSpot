package net.n4dev.treespot.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.n4dev.treespot.databinding.ActivityCaptureSpotInfoFillBinding

class CaptureSpotInfoFillActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCaptureSpotInfoFillBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCaptureSpotInfoFillBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}