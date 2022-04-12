package net.n4dev.treespot.ui.main.fragments.spots

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import net.n4dev.treespot.R
import net.n4dev.treespot.core.AbstractViewHolder
import net.n4dev.treespot.databinding.AdapteritemTreespotLocationBinding
import net.n4dev.treespot.databinding.FragmentMySpotsBinding
import net.n4dev.treespot.db.query.GetUserTreeSpotsQuery

class MySpotsFragment() : Fragment() {

    private lateinit var userID : String

    constructor(userID : String) : this() {
        this.userID = userID
    }


    private var _binding : FragmentMySpotsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mySpotsAdapter: MySpotsAdapter
    private lateinit var mySpotsViewHolder: MySpotViewHolder

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
        _binding = FragmentMySpotsBinding.inflate(inflater, container, false)

        val adapterItemBinding = AdapteritemTreespotLocationBinding.inflate(LayoutInflater.from(requireContext()))
        val query = GetUserTreeSpotsQuery.get(userID)
        mySpotsViewHolder = MySpotViewHolder(adapterItemBinding)
        mySpotsAdapter = MySpotsAdapter(mySpotsViewHolder, query)
        val layoutManager = LinearLayoutManager(requireContext())

        binding.switchMaterial.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked) {
                val text = getString(R.string.my_spots)
                binding.textView.text = text

            } else {
                val text = "Favorite Spots"
                binding.textView.text = text



            }
        }

        binding.mySpotsList.adapter = mySpotsAdapter
        binding.mySpotsList.layoutManager = layoutManager

        AbstractViewHolder.generateItemDecoration(binding.mySpotsList, layoutManager)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}