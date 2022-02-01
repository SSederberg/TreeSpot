package net.n4dev.treespot.ui.createaccount

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.n4dev.treespot.databinding.ActivityRegisterAccountBinding
import net.n4dev.treespot.ui.LoginActivity
import net.n4dev.treespot.ui.TreeSpotActivity
import net.n4dev.treespot.util.ActivityUtil
import org.apache.commons.validator.routines.EmailValidator
import java.util.*

class RegisterAccountActivity : TreeSpotActivity() {

    private lateinit var binding : ActivityRegisterAccountBinding
    private lateinit var validator: EmailValidator
    private lateinit var accountID : UUID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterAccountBinding.inflate(layoutInflater)
        validator = EmailValidator.getInstance()
        setContentView(binding.root)

        binding.registerCreateAccount.setOnClickListener(onRegister)
        binding.registerLogin.setOnClickListener(onSignIn)
        binding.registerAccountPasswordText.addTextChangedListener(onGenericText)
        binding.registerAccountPasswordConfirmText.addTextChangedListener(onGenericText)
        binding.registerEmailAddressText.addTextChangedListener(onGenericText)
    }

    private val onRegister = View.OnClickListener { l ->
        val userName = binding.registerUsername.editText?.text.toString()
        val address = binding.registerEmailAddress.editText?.text.toString()
        val password = binding.registerAccountPassword.editText?.text.toString()
        val passwordConfirm = binding.registerAccountPasswordConfirm.editText?.text.toString()
        val context = applicationContext

        if (isValidAddress(address)) {

            if(password == passwordConfirm && (password.length >= 8 && passwordConfirm.length >= 8)) {
                accountID = UUID.randomUUID()
                createAccount(userName, address, password, accountID)

                val emailBundle = Bundle()
                emailBundle.putString(VerifyEmailActivity.ARG_USER_EMAIL, address)
                emailBundle.putString(VerifyEmailActivity.ARG_USER_USERNAME, userName)
                emailBundle.putString(VerifyEmailActivity.ARG_USER_UUID, accountID.toString())

                ActivityUtil.startActivity(emailBundle, VerifyEmailActivity::class.java, context)
            } else {
                binding.registerAccountPasswordConfirmText.error = "Passwords Do Not Match"
            }
        } else {
            binding.registerEmailAddress.error = "Invalid Email Address!"
        }
    }

    private val onSignIn = View.OnClickListener { l ->
        ActivityUtil.startActivity(LoginActivity::class.java, this)
    }

    private val onGenericText: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
        override fun afterTextChanged(editable: Editable) {
            binding.registerEmailAddress.error = null
        }
    }


    private fun isValidAddress(address : String) : Boolean{
        return validator.isValid(address)
    }

    private fun createAccount(userName : String, address: String, password : String, accountID : UUID) = CoroutineScope(Dispatchers.IO).launch  {
      try {

      }catch(exception : Exception) {
          exception.printStackTrace()
          Logger.e(exception, "")
          ActivityUtil.snack(binding.root, "A user is already registered with that email address!", true)
      }
    }

}