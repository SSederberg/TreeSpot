package net.n4dev.treespot.ui.friends.add

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import net.n4dev.treespot.databinding.ActivityAddFriendsBinding
import net.n4dev.treespot.viewmodel.AddFriendsViewModel
import org.apache.commons.validator.routines.EmailValidator

class AddFriendsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAddFriendsBinding
    private lateinit var viewModel: AddFriendsViewModel
    private lateinit var emailValidator: EmailValidator
    private lateinit var adapter : AddFriendsAdapter
    private lateinit var userID : String

    companion object {
        const val ARG_USER_ID = "ARG_USER_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.extras != null) {
            setupFromArgs(intent.extras!!)
        }else if (savedInstanceState != null) {
            setupFromArgs(savedInstanceState)
        }

        emailValidator = EmailValidator.getInstance()
        viewModel = ViewModelProvider(this).get(AddFriendsViewModel::class.java)
        viewModel.init(this)

        binding.addFriendButton.setOnClickListener(onAddOrSearchFriendListener)

        adapter = AddFriendsAdapter(viewModel, userID)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.searchByUsername("", adapter, userID)
    }

    private val onAddOrSearchFriendListener = View.OnClickListener {
        val friendNameInput = binding.addFriendInput.text.toString()

        //Search by email address, otherwise by username
        if(emailValidator.isValid(friendNameInput)) {
            viewModel.searchByAddress(friendNameInput)
        } else {
           viewModel.searchByUsername(friendNameInput, adapter, userID)
        }
    }

    private fun setupFromArgs(bundle: Bundle) {
        userID = bundle.getString(ARG_USER_ID)!!
    }
}