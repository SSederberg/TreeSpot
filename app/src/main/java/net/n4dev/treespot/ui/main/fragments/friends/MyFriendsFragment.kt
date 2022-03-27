package net.n4dev.treespot.ui.main.fragments.friends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import net.n4dev.treespot.databinding.AdapteritemFriendsBinding
import net.n4dev.treespot.databinding.FragmentMyFriendsBinding
import net.n4dev.treespot.db.query.GetUserFriendsQuery

class MyFriendsFragment(private val userID : String) : Fragment() {

    private var _binding : FragmentMyFriendsBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : MyFriendsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMyFriendsBinding.inflate(inflater, container, false)

        val adapterItemBinding = AdapteritemFriendsBinding.inflate(LayoutInflater.from(requireContext()))
        val viewHolder = MyFriendsViewHolder(adapterItemBinding)
        val query = GetUserFriendsQuery.get(userID)

        adapter = MyFriendsAdapter(viewHolder, query)

        binding.myFriendsList.layoutManager = LinearLayoutManager(requireContext())
        binding.myFriendsList.adapter = adapter

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}