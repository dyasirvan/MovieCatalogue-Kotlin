package com.android.moviecatalogue.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.android.moviecatalogue.R
import com.android.moviecatalogue.data.source.local.entity.MovieTvEntity
import com.android.moviecatalogue.data.source.remote.network.ApiConfig.Companion.IMAGE_URL
import com.android.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.android.moviecatalogue.data.source.remote.response.DetailTvShowResponse
import com.android.moviecatalogue.databinding.ActivityDetailBinding
import com.android.moviecatalogue.utils.EspressoIdlingResource
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.InternalCoroutinesApi
import kotlin.math.roundToInt

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Detail"

        EspressoIdlingResource.increment()

        val data = intent.getParcelableExtra<MovieTvEntity>(EXTRA_ENTITY)
        if(data != null){
            when(data.type){
                "Movie" -> {
                    data.id?.let { viewModel.setSelectedMovie(it) }
                    viewModel.getMovie().observe(this, {

                        showDetailMovie(it)

                        if(!EspressoIdlingResource.idlingResource.isIdleNow){
                            EspressoIdlingResource.decrement()
                        }

                    })
                    var statusFavorite = data.isFavorite
                    setStatusFavorite(statusFavorite)
                    binding.fabFavorite.setOnClickListener {
                        statusFavorite = !statusFavorite
                        viewModel.setFavorite(data, statusFavorite)
                        setStatusFavorite(statusFavorite)
                    }
                }
                "TvShow" -> {
                    data.id?.let { viewModel.setSelectedTvShow(it) }
                    viewModel.getTvShow().observe(this, {

                        showDetailTvShow(it)

                        if(!EspressoIdlingResource.idlingResource.isIdleNow){
                            EspressoIdlingResource.decrement()
                        }

                    })
                    var statusFavorite = data.isFavorite
                    setStatusFavorite(statusFavorite)
                    binding.fabFavorite.setOnClickListener {
                        statusFavorite = !statusFavorite
                        viewModel.setFavorite(data, statusFavorite)
                        setStatusFavorite(statusFavorite)
                    }
                }
            }

        }

        /*
        val extras = intent.extras
        if (extras != null) {

            val movieId = extras.getInt(EXTRA_MOVIE)

            viewModel.setSelectedMovie(movieId)
            viewModel.getMovie().observe(this, {

                showDetailMovie(it)

                if(!EspressoIdlingResource.idlingResource.isIdleNow){
                    EspressoIdlingResource.decrement()
                }

            })
            var statusFavorite = extras.getBoolean(EXTRA_FAVORITE)
            val movieEntity = intent.getParcelableExtra<MovieTvEntity>("entity")
            setStatusFavorite(statusFavorite)
            binding.fabFavorite.setOnClickListener {
                statusFavorite = !statusFavorite
                if (movieEntity != null) {
                    viewModel.setFavorite(movieEntity, statusFavorite)
                }
                setStatusFavorite(statusFavorite)
            }

            val tvShowId = extras.getInt(EXTRA_TV_SHOW)
            viewModel.setSelectedTvShow(tvShowId)
            viewModel.getTvShow().observe(this, {

                showDetailTvShow(it)
                if(!EspressoIdlingResource.idlingResource.isIdleNow){
                    EspressoIdlingResource.decrement()
                }
            })

        }

         */

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

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24))
        } else {
            binding.fabFavorite.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24))
        }
    }

    companion object{
        const val EXTRA_ENTITY = "entity"
    }
}