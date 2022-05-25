package com.druide.flexwithmovies.ui.fragment.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.druide.flexwithmovies.`interface`.IOnMovies
import com.druide.flexwithmovies.model.Movies
import com.druide.flexwithmovies.repository.MoviesRepository
import com.skydoves.sandwich.onSuccess
import kotlinx.coroutines.launch

class MoviesViewModel (private val moviesRepository: MoviesRepository) : ViewModel(), IOnMovies {
    private var _movies: MutableLiveData<Movies?> = MutableLiveData()
    val movies: LiveData<Movies?> = _movies


    override fun getMovieAtPage(pageIndex: Int) {
     viewModelScope.launch {
         val response  = moviesRepository.getMovies(pageIndex)

         response.onSuccess {
             _movies.value = data
         }
     }
    }
}