package com.android.moviecatalogue.ui.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.moviecatalogue.R
import com.android.moviecatalogue.data.source.local.entity.MovieTvEntity
import com.android.moviecatalogue.data.source.remote.network.ApiConfig.Companion.IMAGE_URL
import com.android.moviecatalogue.databinding.ItemMovieBinding
import com.android.moviecatalogue.ui.detail.DetailActivity
import com.android.moviecatalogue.ui.detail.DetailActivity.Companion.EXTRA_FAVORITE
import com.android.moviecatalogue.ui.detail.DetailActivity.Companion.EXTRA_MOVIE
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlin.math.roundToInt

class MovieAdapter: PagedListAdapter<MovieTvEntity, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieTvEntity>() {
            override fun areItemsTheSame(oldItem: MovieTvEntity, newItem: MovieTvEntity): Boolean {
                return oldItem.idFavorite == newItem.idFavorite
            }

            override fun areContentsTheSame(oldItem: MovieTvEntity, newItem: MovieTvEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    class MovieViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(data: MovieTvEntity) {
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
                        it.putExtra(EXTRA_FAVORITE, data.isFavorite)
                        it.putExtra("entity", data)
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
        val movie = getItem(position)
        if(movie != null) {
            holder.bind(movie)
        }
    }

}