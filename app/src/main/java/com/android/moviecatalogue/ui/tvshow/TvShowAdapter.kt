package com.android.moviecatalogue.ui.tvshow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.moviecatalogue.R
import com.android.moviecatalogue.api.ApiConfig.Companion.IMAGE_URL
import com.android.moviecatalogue.data.source.remote.response.ResultTvShow
import com.android.moviecatalogue.databinding.ItemTvShowBinding
import com.android.moviecatalogue.ui.detail.DetailActivity
import com.android.moviecatalogue.ui.detail.DetailActivity.Companion.EXTRA_TV_SHOW
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlin.math.roundToInt

class TvShowAdapter: RecyclerView.Adapter<TvShowAdapter.TvShowViewHolder>() {
    private var listTvShows = ArrayList<ResultTvShow>()

    fun setTvShow(tvShows: List<ResultTvShow>?) {
        if (tvShows == null) return
        this.listTvShows.clear()
        this.listTvShows.addAll(tvShows)
    }

    class TvShowViewHolder(private val binding: ItemTvShowBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(tvShow: ResultTvShow) {
            with(binding) {
                tvTitle.text = tvShow.name
                val scorePercent = tvShow.voteAverage!! * 10
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
                        it.putExtra(EXTRA_TV_SHOW, tvShow.id)
                    }
                    itemView.context.startActivity(intent)
                }
                Glide.with(itemView.context)
                    .load(IMAGE_URL + tvShow.posterPath)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.ic_loading)
                            .error(R.drawable.ic_error))
                    .into(imgThumbnail)

                tvDate.text = tvShow.firstAirDate
            }
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TvShowViewHolder {
        val itemTvShowBinding = ItemTvShowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(itemTvShowBinding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = listTvShows[position]
        holder.bind(tvShow)
    }

    override fun getItemCount(): Int = listTvShows.size
}