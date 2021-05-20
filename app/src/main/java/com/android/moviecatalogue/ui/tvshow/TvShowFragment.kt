package com.android.moviecatalogue.ui.tvshow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.moviecatalogue.R
import com.android.moviecatalogue.api.ApiConfig.Companion.API_KEY
import com.android.moviecatalogue.databinding.FragmentTvShowBinding
import com.android.moviecatalogue.utils.CustomMarginItemDecoration
import com.android.moviecatalogue.viewmodel.ViewModelFactory
import kotlinx.coroutines.InternalCoroutinesApi

class TvShowFragment : Fragment() {
    private lateinit var binding: FragmentTvShowBinding

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity != null) {
            showLoading(true)
            val factory = ViewModelFactory.getInstance()
            val viewModel = ViewModelProvider(this, factory)[TvShowViewModel::class.java]

            val tvShowAdapter = TvShowAdapter()

            viewModel.setTvShows(API_KEY)
            viewModel.getTvShows().observe(viewLifecycleOwner, {
                showLoading(false)
                tvShowAdapter.setTvShow(it)
                with(binding.rvTvShow) {
                    layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    addItemDecoration(CustomMarginItemDecoration(resources.getDimension(R.dimen.margin_16).toInt()))
                    adapter = tvShowAdapter
                }
            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTvShowBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun showLoading(state: Boolean){
        if(state){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

}