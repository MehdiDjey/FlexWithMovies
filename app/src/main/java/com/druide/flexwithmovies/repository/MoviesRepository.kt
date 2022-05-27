package com.druide.flexwithmovies.repository

import com.druide.flexwithmovies.model.Movies
import com.skydoves.sandwich.ApiResponse

interface MoviesRepository {
    suspend fun getMovies(pageIndex: Int): ApiResponse<Movies>
}