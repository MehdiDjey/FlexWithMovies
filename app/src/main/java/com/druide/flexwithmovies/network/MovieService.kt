package com.druide.flexwithmovies.network

import com.druide.flexwithmovies.model.Movie
import com.druide.flexwithmovies.model.Movies
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @Headers("Accept: application/json")
    @GET("discover/movie")
    suspend fun getMovies(@Query("page") page: Int): ApiResponse<Movies>

    @Headers("Accept: application/json")
    @GET("movie/{idMovie}")
    suspend fun getMovieById(@Path("idMovie") idMovie: Int): ApiResponse<Movie>
}