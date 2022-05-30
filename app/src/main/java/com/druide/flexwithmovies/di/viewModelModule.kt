package com.druide.flexwithmovies.di

import com.druide.flexwithmovies.ui.fragment.movie.MovieDetailsViewModel
import com.druide.flexwithmovies.ui.fragment.movies.MoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Provide Movies view model module
 */
val moviesViewModelModule = module { viewModel { MoviesViewModel(get()) } }

/**
 * Provide Movie details view model module
 */
val movieDetailsViewModelModule = module { viewModel { MovieDetailsViewModel(get()) } }