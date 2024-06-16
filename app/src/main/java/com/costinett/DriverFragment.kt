package com.costinett

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.costinett.adapters.DriverAdapter
import com.costinett.databinding.FragmentDriversBinding


class DriverFragment : Fragment() {

    private var _binding: FragmentDriversBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var driverAdapter: DriverAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDriversBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        val recyclerView = binding.drivers
        driverAdapter = DriverAdapter(viewModel)
        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
        recyclerView.adapter = driverAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(binding.root.context, 1))

        initObservers()

        return binding.root
    }

    fun initObservers() {
        viewModel.driverList.observe(viewLifecycleOwner) {
            driverAdapter.drivers = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sortButton.setOnClickListener {
            viewModel.sortByLastName()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}