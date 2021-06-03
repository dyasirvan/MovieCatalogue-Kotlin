package com.android.moviecatalogue.ui.tvshow

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.moviecatalogue.R
import com.android.moviecatalogue.data.source.remote.network.ApiConfig.Companion.API_KEY
import com.android.moviecatalogue.databinding.FragmentTvShowBinding
import com.android.moviecatalogue.ui.movie.MovieAdapter
import com.android.moviecatalogue.utils.CustomMarginItemDecoration
import com.android.moviecatalogue.vo.Status
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
class TvShowFragment : Fragment() {
    private lateinit var binding: FragmentTvShowBinding
    private val tvShowViewModel: TvShowViewModel by viewModels()

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity != null) {
//            showLoading(true)

            getTvShows()

            /*
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

             */
        }
    }

    private fun getTvShows() {
        val tvShowAdapter = TvShowAdapter()
        tvShowViewModel.getTvShows().observe(viewLifecycleOwner, { tvShows ->
            if(tvShows != null){
                when(tvShows.status){
                    Status.LOADING -> showLoading(true)
                    Status.SUCCESS -> {
                        showLoading(false)
                        tvShowAdapter.submitList(tvShows.data)
                    }
                    Status.ERROR -> {
                        showLoading(false)
                        Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        with(binding.rvTvShow) {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(CustomMarginItemDecoration(resources.getDimension(R.dimen.margin_16).toInt()))
            setHasFixedSize(true)
            adapter = tvShowAdapter
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