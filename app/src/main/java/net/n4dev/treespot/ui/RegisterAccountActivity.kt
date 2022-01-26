package net.n4dev.treespot.ui

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import io.appwrite.services.Account
import kotlinx.coroutines.*
import net.n4dev.treespot.TreeSpotApplication
import net.n4dev.treespot.databinding.ActivityRegisterAccountBinding
import net.n4dev.treespot.util.ActivityUtil
import org.apache.commons.validator.routines.EmailValidator
import java.util.*

class RegisterAccountActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding : ActivityRegisterAccountBinding
    private lateinit var validator: EmailValidator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterAccountBinding.inflate(layoutInflater)
        validator = EmailValidator.getInstance()
        setContentView(binding.root)

        binding.registerCreateAccount.setOnClickListener(this)
        binding.registerAccountPasswordText.addTextChangedListener(onGenericText)
        binding.registerAccountPasswordConfirmText.addTextChangedListener(onGenericText)
        binding.registerEmailAddressText.addTextChangedListener(onGenericText)
    }

    override fun onClick(view: View?) {
        val userName = binding.registerUsername.editText?.text.toString()
        val address = binding.registerEmailAddress.editText?.text.toString()
        val password = binding.registerAccountPassword.editText?.text.toString()
        val passwordConfirm = binding.registerAccountPasswordConfirm.editText?.text.toString()
        val context = applicationContext

        if (isValidAddress(address)) {

            if(password == passwordConfirm && (password.length >= 8 && passwordConfirm.length >= 8)) {
                GlobalScope.launch(Dispatchers.IO) {
                    createAccount(userName, address, password, context)
                }
            } else {
                binding.registerAccountPasswordConfirmText.error = "Passwords Do Not Match"
            }
        } else {
            binding.registerEmailAddress.error = "Invalid Email Address!"
        }
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

    private suspend fun createAccount(userName : String, address: String, password : String, context: Context)  {
        val client  = TreeSpotApplication.getClient(context)
        val account = Account(client)
        account.create(UUID.randomUUID().toString(), address, password, userName)
        ActivityUtil.startActivity(MainActivity::class.java, context)
    }


}