package com.costinett

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.costinett.adapters.RouteAdapter
import com.costinett.databinding.FragmentRoutesBinding

class RouteFragment : Fragment() {

    private var _binding: FragmentRoutesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var routeAdapter: RouteAdapter
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRoutesBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]

        val recycler = binding.routes
        routeAdapter = RouteAdapter()
        recycler.layoutManager = LinearLayoutManager(binding.root.context)
        recycler.adapter = routeAdapter
        recycler.addItemDecoration(DividerItemDecoration(binding.root.context, 1))

        initObservers()

        return binding.root
    }

    private fun initObservers() {
        viewModel.routeList.observe(viewLifecycleOwner) {
            routeAdapter.routes = it
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}