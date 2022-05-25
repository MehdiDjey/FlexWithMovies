package com.druide.flexwithmovies.di

import com.druide.flexwithmovies.repository.MovieRepository
import com.druide.flexwithmovies.repository.MovieRepositoryImpl
import com.druide.flexwithmovies.repository.MoviesRepository
import com.druide.flexwithmovies.repository.MoviesRepositoryImpl
import org.koin.dsl.module


val moviesRepositoryModule = module { single<MoviesRepository> { MoviesRepositoryImpl(get()) } }

val movieRepositoryModule = module { single<MovieRepository> { MovieRepositoryImpl(get()) } }