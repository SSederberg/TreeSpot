package net.n4dev.treespot.ui.main.fragments.spots

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import net.n4dev.treespot.databinding.AdapteritemTreespotLocationBinding
import net.n4dev.treespot.databinding.FragmentMySpotsBinding

class MySpotsFragment() : Fragment() {

    private var _binding : FragmentMySpotsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mySpotsAdapter: MySpotsAdapter
    private lateinit var mySpotsViewHolder: MySpotViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        mySpotsViewHolder = MySpotViewHolder(adapterItemBinding)
        mySpotsAdapter = MySpotsAdapter(mySpotsViewHolder)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}