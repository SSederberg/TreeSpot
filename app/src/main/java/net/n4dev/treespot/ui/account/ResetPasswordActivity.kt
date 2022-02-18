package net.n4dev.treespot.ui.account

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import net.n4dev.treespot.databinding.ActivityResetPasswordBinding
import net.n4dev.treespot.viewmodel.ResetPasswordViewModel

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var resetPasswordViewModel: ResetPasswordViewModel

    private lateinit var binding : ActivityResetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        resetPasswordViewModel = ViewModelProvider(this).get(ResetPasswordViewModel::class.java)
        resetPasswordViewModel.init(this)
        setContentView(binding.root)
    }
}