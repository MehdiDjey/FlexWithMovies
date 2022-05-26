package com.druide.flexwithmovies.ui.fragment.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.druide.flexwithmovies.`interface`.IOnMovie
import com.druide.flexwithmovies.model.Movie
import com.druide.flexwithmovies.model.Movies
import com.druide.flexwithmovies.repository.MovieRepository
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailsViewModel (private val movieRepository: MovieRepository) : ViewModel(), IOnMovie {
    private var _movieDetails: MutableLiveData<Movie?> = MutableLiveData()
    val movieDetails: LiveData<Movie?> = _movieDetails

    override fun getMovieDetailWithId(idMovie: Int) {
        viewModelScope.launch {
            val response  = movieRepository.getMovie(idMovie)

            response.onSuccess {
                _movieDetails.value = data
            }

            response.onError {
                Log.d("TAG", "getMovieDetailWithId: Err "+message())
            }

            response.onException {
                Log.d("TAG", "getMovieDetailWithId: Ex "+message())
            }
        }
    }
}