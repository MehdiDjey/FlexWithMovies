package com.druide.flexwithmovies.repository

import com.druide.flexwithmovies.model.Movie
import com.druide.flexwithmovies.network.MovieService
import com.druide.flexwithmovies.utils.TAG
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class MovieRepositoryImpl(
    private val movieService: MovieService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : MovieRepository {

    override suspend fun getMovie(idMovie: Int): ApiResponse<Movie> {
        Timber.tag(TAG).d("getMovie() called with: idMovie = $idMovie")
        return withContext(dispatcher) {
            movieService.getMovieById(idMovie)
        }
    }
}