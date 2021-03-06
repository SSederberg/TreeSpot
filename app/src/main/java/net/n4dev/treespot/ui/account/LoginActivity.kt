package net.n4dev.treespot.ui.account

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import net.n4dev.treespot.databinding.ActivityLoginBinding
import net.n4dev.treespot.ui.TreeSpotActivity
import net.n4dev.treespot.util.ActivityUtil
import net.n4dev.treespot.viewmodel.UserLoginViewModel
import org.apache.commons.validator.routines.EmailValidator

class LoginActivity : TreeSpotActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var validator: EmailValidator
    private lateinit var loginModel : UserLoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        validator = EmailValidator.getInstance()
        setContentView(binding.root)

        binding.loginButtonAttempt.setOnClickListener(onLoginAttempt)
        binding.loginResetPassword.setOnClickListener(onResetPassword)
        loginModel = ViewModelProvider(this).get(UserLoginViewModel::class.java)
        loginModel.init(this)
    }

    override fun buildFromBundle(bundle: Bundle) {

    }

    private val onLoginAttempt = View.OnClickListener { l ->
        if(!binding.loginPasswordText.text.toString().isEmpty()
            && !binding.loginUsernameText.text.toString().isEmpty()) {
            val email = binding.loginUsernameText.text.toString()
            val passwd = binding.loginPasswordText.text.toString()

            if(validator.isValid(email)) {
                if(passwd.length >= 8) { //Minimum length required by Appwrite
                    loginModel.attemptLogin(email, passwd, super.getSharedPreferences(), this)

                } else {
                    ActivityUtil.toast(this, "Invalid password provided!", true)
                }

            } else {
                binding.loginUsername.error = "Invalid Email Address!"
            }
        } else {
            ActivityUtil.snack(binding.root, "Missing either the email address or password!", true)
        }
    }

    private val onResetPassword = View.OnClickListener {
        val email = binding.loginUsernameText.text.toString()

        if(email.isNotEmpty()) {

        } else {

        }

        ActivityUtil.startActivity(ResetPasswordActivity::class.java, this)
    }
}