package com.android.moviecatalogue.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.moviecatalogue.R
import com.android.moviecatalogue.databinding.FragmentMovieBinding
import com.android.moviecatalogue.utils.CustomMarginItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import androidx.fragment.app.viewModels
import com.android.moviecatalogue.ui.adapter.MovieTvAdapter
import com.android.moviecatalogue.vo.Status

@AndroidEntryPoint
class MovieFragment : Fragment() {
    private lateinit var fragmentMovieBinding: FragmentMovieBinding
    private val movieViewModel: MovieViewModel by viewModels()

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity != null) {
//            showLoading(true)

            getMovies()

            /*
            val factory = ViewModelFactory.getInstance()
            val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]

            val movieAdapter = MovieTvAdapter()
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

             */

        }
    }

    private fun getMovies() {
        val movieAdapter = MovieTvAdapter()
        movieViewModel.getMovies().observe(viewLifecycleOwner, { movies ->
            if(movies != null){
                when(movies.status){
                    Status.LOADING -> showLoading(true)
                    Status.SUCCESS -> {
                        showLoading(false)
                        movieAdapter.submitList(movies.data)
                    }
                    Status.ERROR -> {
                        showLoading(false)
                        Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

        with(fragmentMovieBinding.rvMovie) {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            addItemDecoration(CustomMarginItemDecoration(resources.getDimension(R.dimen.margin_16).toInt()))
            setHasFixedSize(true)
            adapter = movieAdapter
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