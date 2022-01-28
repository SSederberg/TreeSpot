package net.n4dev.treespot.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.orhanobut.logger.Logger
import io.appwrite.exceptions.AppwriteException
import io.appwrite.services.Account
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.n4dev.treespot.core.User
import net.n4dev.treespot.databinding.ActivityRegisterAccountBinding
import net.n4dev.treespot.util.ActivityUtil
import org.apache.commons.validator.routines.EmailValidator
import java.util.*

class RegisterAccountActivity : TreeSpotActivity() {

    private lateinit var binding : ActivityRegisterAccountBinding
    private lateinit var validator: EmailValidator

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
                createAccount(userName, address, password, context)
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

    private fun createAccount(userName : String, address: String, password : String, context: Context) = GlobalScope.launch  {
      try {
          val client  = getAppWrite()
          val account = Account(client)
          val accountID = UUID.randomUUID().toString()
          val accountResponse = account.create(accountID, address, password, userName)
          val sessionResponse = account.createSession(address, password)
          val sessionID = sessionResponse.id

          val newUser = User(userName, address, UUID.fromString(accountID))

          val prefs = getSharedPreferences()
          prefs.edit().putString(PREF_ACTIVE_USERNAME_ID, accountID).apply()
          prefs.edit().putString(PREF_ACTIVE_SESSION_ID, sessionID).apply()


          ActivityUtil.startActivity(MainActivity::class.java, context)
      }catch(exception : AppwriteException) {
          exception.printStackTrace()
          ActivityUtil.snack(binding.root, "A user is already registered with that email address!", true)
      }
    }


}