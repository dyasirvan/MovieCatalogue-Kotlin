package com.android.moviecatalogue.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.android.moviecatalogue.data.source.local.LocalDataSource
import com.android.moviecatalogue.data.source.local.entity.MovieTvEntity
import com.android.moviecatalogue.data.source.remote.ApiResponse
import com.android.moviecatalogue.data.source.remote.response.DetailMovieResponse
import com.android.moviecatalogue.data.source.remote.RemoteDataSource
import com.android.moviecatalogue.data.source.remote.response.DetailTvShowResponse
import com.android.moviecatalogue.data.source.remote.response.ResultMovie
import com.android.moviecatalogue.data.source.remote.response.ResultTvShow
import com.android.moviecatalogue.utils.AppExecutors
import com.android.moviecatalogue.utils.Constant
import com.android.moviecatalogue.vo.Resource
import javax.inject.Inject

class MovieCatalogueRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
):
    MovieCatalogueDataSource {

    override fun getMovies(type: String): LiveData<Resource<PagedList<MovieTvEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieTvEntity>, List<ResultMovie>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<MovieTvEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllData(type), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieTvEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ResultMovie>>> =
                remoteDataSource.getMovies()

            override fun saveCallResult(data: List<ResultMovie>) {
                val movieList = ArrayList<MovieTvEntity>()
                for (response in data) {
                    val entity = MovieTvEntity(
                        id = response.id,
                        title = response.title,
                        posterPath = response.posterPath,
                        releaseDate = response.releaseDate,
                        voteAverage = response.voteAverage,
                        type = Constant.MOVIE
                    )
                    movieList.add(entity)
                }

                localDataSource.insertMovies(movieList)
            }

        }.asLiveData()

    }

    override fun getTvShows(type: String): LiveData<Resource<PagedList<MovieTvEntity>>> {
        return object : NetworkBoundResource<PagedList<MovieTvEntity>, List<ResultTvShow>>(appExecutors){
            override fun loadFromDB(): LiveData<PagedList<MovieTvEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(4)
                    .setPageSize(4)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllData(type), config).build()
            }

            override fun shouldFetch(data: PagedList<MovieTvEntity>?): Boolean =
                data == null || data.isEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ResultTvShow>>> =
                remoteDataSource.getTvShows()

            override fun saveCallResult(data: List<ResultTvShow>) {
                val tvList = ArrayList<MovieTvEntity>()
                for (response in data) {
                    val entity = MovieTvEntity(
                        id = response.id,
                        title = response.name,
                        posterPath = response.posterPath,
                        releaseDate = response.firstAirDate,
                        voteAverage = response.voteAverage,
                        type = Constant.TV_SHOW
                    )
                    tvList.add(entity)
                }

                localDataSource.insertMovies(tvList)
            }

        }.asLiveData()

    }

    override fun getDetailMovie(id: Int): LiveData<DetailMovieResponse> {
        val detailMovie = MutableLiveData<DetailMovieResponse>()
        remoteDataSource.getDetailMovie(id, object : RemoteDataSource.LoadDetailMovieCallback{
            override fun onDetailMovieReceive(detailMovieResponse: DetailMovieResponse) {
                detailMovie.postValue(detailMovieResponse)
            }
        })

        return detailMovie
    }

    override fun getDetailTvShow(id: Int): LiveData<DetailTvShowResponse> {
        val detailTvShow = MutableLiveData<DetailTvShowResponse>()
        remoteDataSource.getDetailTvShow(id, object : RemoteDataSource.LoadDetailTvCallback{
            override fun onDetailTvReceive(detailTvResponse: DetailTvShowResponse) {
                detailTvShow.postValue(detailTvResponse)
            }
        })

        return detailTvShow
    }

    override fun getFavorite(type: String): LiveData<PagedList<MovieTvEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getFavoriteMovies(type), config).build()
    }

    override fun setFavorite(data: MovieTvEntity, state: Boolean) {
        appExecutors.diskIO().execute { localDataSource.updateMovies(data, state) }
    }

    override fun sortByName(type: String, sort: String): LiveData<PagedList<MovieTvEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.sortByName(type, sort), config).build()
    }

}