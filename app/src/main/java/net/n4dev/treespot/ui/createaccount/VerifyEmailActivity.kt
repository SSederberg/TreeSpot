package net.n4dev.treespot.ui.createaccount

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.orhanobut.logger.Logger
import io.appwrite.services.Account
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.n4dev.treespot.databinding.ActivityVerifyEmailBinding
import net.n4dev.treespot.ui.TreeSpotActivity

class VerifyEmailActivity : TreeSpotActivity() {

    companion object {
        val ARG_USER_USERNAME = "ARG_USER_USERNAME"
        val ARG_USER_UUID = "ARG_USER_UUID"
        val ARG_USER_SECRET = "ARG_USER_SECRET"
        val ARG_USER_EMAIL = "ARG_USER_EMAIL"
    }

    private lateinit var binding: ActivityVerifyEmailBinding
    private lateinit var secret : String
    private lateinit var uuid : String
    private lateinit var email : String
    private lateinit var username : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.extras != null) {
            setupFromArgs(intent.extras!!)
        } else {
            Logger.e("Failed to verify email/account! The required Bundle was null!")
        }

        binding.verifyEmailAuth.setOnClickListener {
            val confirmationText = binding.verifyEmailCodeText.editableText.toString()

            if(confirmationText != null && confirmationText.isNotEmpty()) {
                binding.verifyEmailLoading.visibility = View.VISIBLE
                val account = Account(getAppWrite(secret!!))

                lifecycleScope.launch(Dispatchers.IO){
                    val verifyResponse = account.updateVerification(uuid, secret)
                }
            }

        }
    }

    private fun setupFromArgs(bundle: Bundle) {
        secret = bundle.getString(ARG_USER_SECRET, null)
        uuid = bundle.getString(ARG_USER_UUID, null)
        email = bundle.getString(ARG_USER_EMAIL, null)
        username = bundle.getString(ARG_USER_USERNAME, null)
    }
}