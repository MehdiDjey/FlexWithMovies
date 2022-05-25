package com.druide.flexwithmovies.repository

import com.druide.flexwithmovies.model.Movie
import com.druide.flexwithmovies.network.MovieService
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MovieRepositoryImpl(
    private val  movieService: MovieService,
    private val dispatcher : CoroutineDispatcher = Dispatchers.IO
) : MovieRepository{

    override suspend fun getMovie(idMovie: Int): ApiResponse<Movie> {
       return  withContext(dispatcher) {
           movieService.getMovieById(idMovie)
       }
    }
}