package net.n4dev.treespot.ui

import android.os.Bundle
import android.view.View
import net.n4dev.treespot.databinding.ActivityLoginBinding
import org.apache.commons.validator.routines.EmailValidator

class LoginActivity : TreeSpotActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var validator: EmailValidator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButtonAttempt.setOnClickListener(onLoginAttempt)
    }

    private val onLoginAttempt = View.OnClickListener { l ->
        if(!binding.loginPasswordText.text.toString().isEmpty()
            && binding.loginUsernameText.text.toString().isEmpty()) {
            val email = binding.loginUsernameText.text.toString()
            val passwd = binding.loginPasswordText.text.toString()

        }
    }
}