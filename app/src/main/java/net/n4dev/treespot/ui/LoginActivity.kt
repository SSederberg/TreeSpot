package net.n4dev.treespot.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import net.n4dev.treespot.databinding.ActivityLoginBinding
import org.apache.commons.validator.routines.EmailValidator

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var validator: EmailValidator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}