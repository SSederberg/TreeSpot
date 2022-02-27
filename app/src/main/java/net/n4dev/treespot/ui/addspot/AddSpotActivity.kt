package net.n4dev.treespot.ui.addspot

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.n4dev.treespot.databinding.ActivityAddSpotBinding

class AddSpotActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddSpotBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

    }
}