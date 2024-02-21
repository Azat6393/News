package com.berdimyradov.news.presentation.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.berdimyradov.news.R
import com.berdimyradov.news.databinding.FragmentDashboardBinding
import com.berdimyradov.news.domain.model.Category
import com.berdimyradov.news.presentation.adapter.CategoryAdapter
import com.berdimyradov.news.utils.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private val viewModel: DashboardViewModel by viewModels()
    private val mAdapter: CategoryAdapter by lazy { CategoryAdapter() }
    private var _binding: FragmentDashboardBinding? = null
    private val binding: FragmentDashboardBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getNews()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentDashboardBinding.bind(view)
        initView()
        observe()
    }

    private fun initView() {
        binding.categoryRv.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.newsResponse.collectLatest(::processCategories)
            }
        }
    }

    private fun processCategories(resource: Resource<List<Category>>?) {
        when (resource) {
            is Resource.Error -> showMessage(resource.message ?: return)
            is Resource.Loading -> {
                showMessage("Loading...")
                mAdapter.submitList(resource.data)
            }
            is Resource.Success -> mAdapter.submitList(resource.data)
            null -> Unit
        }
    }

    private fun showMessage(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG).show()
    }
}