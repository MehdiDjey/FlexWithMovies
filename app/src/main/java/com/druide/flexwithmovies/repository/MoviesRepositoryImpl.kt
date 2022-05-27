package com.druide.flexwithmovies.repository

import com.druide.flexwithmovies.model.Movies
import com.druide.flexwithmovies.network.MovieService
import com.druide.flexwithmovies.utils.TAG
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class MoviesRepositoryImpl(
    private val movieService: MovieService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MoviesRepository {

    override suspend fun getMovies(pageIndex: Int): ApiResponse<Movies> {
        Timber.tag(TAG).d("getMovies() called with: pageIndex = $pageIndex")
        return withContext(dispatcher) {
            movieService.getMovies(pageIndex)
        }
    }
}