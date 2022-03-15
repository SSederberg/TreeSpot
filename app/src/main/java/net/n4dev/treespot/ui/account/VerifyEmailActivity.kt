package net.n4dev.treespot.ui.account

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.orhanobut.logger.Logger
import net.n4dev.treespot.databinding.ActivityVerifyEmailBinding
import net.n4dev.treespot.ui.TreeSpotActivity
import net.n4dev.treespot.ui.main.MainActivity
import net.n4dev.treespot.util.ActivityUtil.Companion.startActivity
import net.n4dev.treespot.viewmodel.VerifyAccountViewModel

class VerifyEmailActivity : TreeSpotActivity() {

    private lateinit var binding: ActivityVerifyEmailBinding
    private var email: String? = null
    private var username: String? = null
    private var accountID: String? = null
    private lateinit var verifyViewModel : VerifyAccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyEmailBinding.inflate(layoutInflater)
        verifyViewModel = ViewModelProvider(this).get(VerifyAccountViewModel::class.java)

        if (intent.extras != null) {
            setupFromArgs(intent.extras)
            verifyViewModel.init(this)
        } else {
            Logger.e("Failed to verify email/account! The required Bundle was null!")
        }

        binding.emailAddress = email
        setContentView(binding.root)
        binding.verifyEmailAuth.setOnClickListener { l: View? ->
            binding.verifyEmailLoading.visibility = View.VISIBLE

            val bundle = Bundle()
            bundle.putString(MainActivity.ARG_USER_ID, accountID)
            bundle.putString(MainActivity.ARG_USER_EMAIL, username)
            startActivity(bundle, MainActivity::class.java, this)
        }
    }


    private fun setupFromArgs(extras: Bundle?) {
        accountID = extras!!.getString(ARG_USER_UUID)
        email = extras.getString(ARG_USER_EMAIL)
        username = extras.getString(ARG_USER_USERNAME)
    }

    companion object {
        const val ARG_USER_USERNAME = "ARG_USER_USERNAME"
        const val ARG_USER_UUID = "ARG_USER_UUID"
        const val ARG_USER_EMAIL = "ARG_USER_EMAIL"
    }
}