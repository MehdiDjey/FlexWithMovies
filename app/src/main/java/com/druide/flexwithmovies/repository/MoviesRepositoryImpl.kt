package com.druide.flexwithmovies.repository

import com.druide.flexwithmovies.model.Movies
import com.druide.flexwithmovies.network.MovieService
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoviesRepositoryImpl(
    private val  movieService: MovieService,
    private val dispatcher : CoroutineDispatcher = Dispatchers.IO
) : MoviesRepository{
    override suspend fun getMovies(pageIndex: Int): ApiResponse<Movies> {
        return  withContext(dispatcher) {
            movieService.getMovies(pageIndex)
        }
    }
}