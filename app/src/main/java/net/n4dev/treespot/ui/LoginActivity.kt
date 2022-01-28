package net.n4dev.treespot.ui

import android.os.Bundle
import android.view.View
import com.orhanobut.logger.Logger
import io.appwrite.extensions.toJson
import io.appwrite.services.Account
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.n4dev.treespot.databinding.ActivityLoginBinding
import org.apache.commons.validator.routines.EmailValidator
import java.lang.Exception

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

            GlobalScope.launch {
                val account = Account(getAppWrite())

                try {
                    val jsonResponse = account.createSession(email, passwd)
                    Logger.i(jsonResponse.toJson())
                }catch (exception : Exception) {
                   Logger.e(exception, "")
                }
            }
        }
    }
}