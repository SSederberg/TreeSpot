package net.n4dev.treespot.ui.friends.add

import android.graphics.Bitmap
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddFriendsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        emailValidator = EmailValidator.getInstance()
        viewModel = ViewModelProvider(this).get(AddFriendsViewModel::class.java)
        viewModel.init(this)

        binding.addFriendButton.setOnClickListener(onAddOrSearchFriendListener)

        adapter = AddFriendsAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private val onAddOrSearchFriendListener = View.OnClickListener {
        val friendNameInput = binding.addFriendInput.text.toString()
        val avatarArray = ArrayList<Bitmap>()
        //Search by email address, otherwise by username
        if(emailValidator.isValid(friendNameInput)) {
            viewModel.searchByAddress(friendNameInput)
        } else {
           val returnedUsers =  viewModel.searchByUsername(friendNameInput, adapter)
        }


    }
}