package com.android.moviecatalogue.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.android.moviecatalogue.R
import com.android.moviecatalogue.api.ApiConfig.Companion.API_KEY
import com.android.moviecatalogue.api.ApiConfig.Companion.IMAGE_URL
import com.android.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.android.moviecatalogue.data.source.remote.response.DetailTvShowResponse
import com.android.moviecatalogue.databinding.ActivityDetailBinding
import com.android.moviecatalogue.utils.EspressoIdlingResource
import com.android.moviecatalogue.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.InternalCoroutinesApi
import kotlin.math.roundToInt

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail"

        val factory = ViewModelFactory.getInstance()
        val viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        EspressoIdlingResource.increment()
        val extras = intent.extras
        if (extras != null) {

            val movieId = extras.getInt(EXTRA_MOVIE)

            viewModel.setSelectedMovie(movieId, API_KEY)
            viewModel.getMovie().observe(this, {

                showDetailMovie(it)

                if(!EspressoIdlingResource.idlingResource.isIdleNow){
                    EspressoIdlingResource.decrement()
                }

            })

            val tvShowId = extras.getInt(EXTRA_TV_SHOW)
            viewModel.setSelectedTvShow(tvShowId, API_KEY)
            viewModel.getTvShow().observe(this, {

                showDetailTvShow(it)
                if(!EspressoIdlingResource.idlingResource.isIdleNow){
                    EspressoIdlingResource.decrement()
                }
            })


        }
    }

    private fun showDetailMovie(data: DetailMovieResponse) {
        binding.tvTitle.text = data.title
        binding.tvDate.text = data.releaseDate
        binding.tvGenre.text = data.genres?.map { genre -> genre.name }?.joinToString(separator = ", ")
        binding.tvStatus.text = data.status

        if(data.voteAverage != null){
            val scorePercent = data.voteAverage * 10
            val toInt = scorePercent.roundToInt()
            when {
                toInt >= 70 -> {
                    binding.imgBgScore.backgroundTintList = ContextCompat.getColorStateList(this@DetailActivity, R.color.green)
                }
                toInt >= 50 -> {
                    binding.imgBgScore.backgroundTintList = ContextCompat.getColorStateList(this@DetailActivity, R.color.yellow)
                }
                else -> {
                    binding.imgBgScore.backgroundTintList = ContextCompat.getColorStateList(this@DetailActivity, R.color.red)
                }
            }
            val displayScore = "${toInt}%"
            binding.tvScore.text = displayScore
        }else{
            binding.tvScore.text = resources.getString(R.string.nr)
        }

        Glide.with(this)
                .load(IMAGE_URL + data.posterPath)
                .transform(RoundedCorners(20))
                .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                                .error(R.drawable.ic_error))
                .into(binding.imgThumbnail)

        binding.tvOverviewDesc.text = data.overview
    }

    private fun showDetailTvShow(data: DetailTvShowResponse) {
        binding.tvTitle.text = data.name
        binding.tvDate.text = data.firstAirDate
        binding.tvGenre.text = data.genres?.map { genre -> genre.name }?.joinToString(separator = ", ")
        binding.tvStatus.text = data.status

        if(data.voteAverage != null){
            val scorePercent = data.voteAverage * 10
            val toInt = scorePercent.roundToInt()
            when {
                toInt >= 70 -> {
                    binding.imgBgScore.backgroundTintList = ContextCompat.getColorStateList(this@DetailActivity, R.color.green)
                }
                toInt >= 50 -> {
                    binding.imgBgScore.backgroundTintList = ContextCompat.getColorStateList(this@DetailActivity, R.color.yellow)
                }
                else -> {
                    binding.imgBgScore.backgroundTintList = ContextCompat.getColorStateList(this@DetailActivity, R.color.red)
                }
            }
            val displayScore = "${toInt}%"
            binding.tvScore.text = displayScore
        }else{
            binding.tvScore.text = resources.getString(R.string.nr)
        }

        Glide.with(this)
            .load(IMAGE_URL + data.posterPath)
            .transform(RoundedCorners(20))
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_loading)
                    .error(R.drawable.ic_error))
            .into(binding.imgThumbnail)

        binding.tvOverviewDesc.text = data.overview
    }

    companion object{
        const val EXTRA_MOVIE = "extra_movie"
        const val EXTRA_TV_SHOW = "extra_tv_show"
    }
}