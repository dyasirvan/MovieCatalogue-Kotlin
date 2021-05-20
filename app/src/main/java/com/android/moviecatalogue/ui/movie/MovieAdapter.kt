package com.android.moviecatalogue.ui.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.moviecatalogue.R
import com.android.moviecatalogue.api.ApiConfig.Companion.IMAGE_URL
import com.android.moviecatalogue.data.source.remote.response.ResultMovie
import com.android.moviecatalogue.databinding.ItemMovieBinding
import com.android.moviecatalogue.ui.detail.DetailActivity
import com.android.moviecatalogue.ui.detail.DetailActivity.Companion.EXTRA_MOVIE
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlin.math.roundToInt

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var listMovies = ArrayList<ResultMovie>()

    fun setMovie(data: List<ResultMovie>?) {
        if (data == null) return
        this.listMovies.clear()
        this.listMovies.addAll(data)
    }

    class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: ResultMovie) {
            with(binding) {
                tvTitle.text = data.title
                val scorePercent = data.voteAverage!! * 10
                val toInt = scorePercent.roundToInt()
                when {
                    toInt >= 70 -> {
                        imgBgScore.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.green)
                    }
                    toInt >= 50 -> {
                        imgBgScore.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.yellow)
                    }
                    else -> {
                        imgBgScore.backgroundTintList = ContextCompat.getColorStateList(itemView.context, R.color.red)
                    }
                }
                val scoreDisplay = "${toInt}%"
                tvScore.text = scoreDisplay
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java).also {
                        it.putExtra(EXTRA_MOVIE, data.id)
                    }
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load(IMAGE_URL + data.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error))
                    .into(imgThumbnail)

                tvDate.text = data.releaseDate
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val itemMovieBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemMovieBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = listMovies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = listMovies.size
}