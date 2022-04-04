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

    override fun buildFromBundle(bundle: Bundle) {
        accountID = bundle.getString(ARG_USER_UUID)
        email = bundle.getString(ARG_USER_EMAIL)
        username = bundle.getString(ARG_USER_USERNAME)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyEmailBinding.inflate(layoutInflater)
        verifyViewModel = ViewModelProvider(this).get(VerifyAccountViewModel::class.java)

        if (intent.extras != null) {
            buildFromBundle(intent.extras!!)
            verifyViewModel.init(this)
        } else {
            Logger.e("Failed to verify email/account! The required Bundle was null!")
        }

        verifyViewModel.createVerification()
        binding.emailAddress = email
        setContentView(binding.root)
        binding.verifyEmailAuth.setOnClickListener {
            binding.verifyEmailLoading.visibility = View.VISIBLE

            val bundle = Bundle()
            bundle.putString(MainActivity.ARG_USER_ID, accountID)
            bundle.putString(MainActivity.ARG_USER_EMAIL, username)
            startActivity(bundle, MainActivity::class.java, this)
        }
    }


    companion object {
        const val ARG_USER_USERNAME = "ARG_USER_USERNAME"
        const val ARG_USER_UUID = "ARG_USER_UUID"
        const val ARG_USER_EMAIL = "ARG_USER_EMAIL"
    }
}