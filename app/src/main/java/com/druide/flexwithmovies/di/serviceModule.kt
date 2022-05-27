package com.druide.flexwithmovies.di

import com.druide.flexwithmovies.network.MovieService
import org.koin.dsl.module
import retrofit2.Retrofit


val serviceModule = module { single { get<Retrofit>().create(MovieService::class.java) } }