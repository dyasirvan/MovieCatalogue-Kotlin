package com.android.moviecatalogue.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.moviecatalogue.R
import com.android.moviecatalogue.api.ApiConfig.Companion.API_KEY
import com.android.moviecatalogue.databinding.FragmentMovieBinding
import com.android.moviecatalogue.utils.CustomMarginItemDecoration
import com.android.moviecatalogue.viewmodel.ViewModelFactory
import kotlinx.coroutines.InternalCoroutinesApi

class MovieFragment : Fragment() {
    private lateinit var fragmentMovieBinding: FragmentMovieBinding

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity != null) {
            showLoading(true)

            val factory = ViewModelFactory.getInstance()
            val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

            val movieAdapter = MovieAdapter()
            viewModel.setMovies(API_KEY)
            viewModel.getMovies().observe(viewLifecycleOwner,{
                showLoading(false)
                movieAdapter.setMovie(it)
                with(fragmentMovieBinding.rvMovie) {
                    layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                    addItemDecoration(CustomMarginItemDecoration(resources.getDimension(R.dimen.margin_16).toInt()))
                    adapter = movieAdapter
                }
            })


        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        fragmentMovieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return fragmentMovieBinding.root
    }

    private fun showLoading(state: Boolean){
        if(state){
            fragmentMovieBinding.progressBar.visibility = View.VISIBLE
        }else{
            fragmentMovieBinding.progressBar.visibility = View.GONE
        }
    }
}