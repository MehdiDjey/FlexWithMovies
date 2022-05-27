package com.druide.flexwithmovies.repository

import com.druide.flexwithmovies.model.Movie
import com.skydoves.sandwich.ApiResponse

interface MovieRepository {
    suspend fun getMovie(idMovie: Int): ApiResponse<Movie>
}