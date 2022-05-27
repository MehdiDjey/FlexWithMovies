package com.druide.flexwithmovies.ui.fragment.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.druide.flexwithmovies.`interface`.IOnMovie
import com.druide.flexwithmovies.model.Movie
import com.druide.flexwithmovies.repository.MovieRepository
import com.druide.flexwithmovies.utils.TAG
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieDetailsViewModel(private val movieRepository: MovieRepository) : ViewModel(), IOnMovie {
    private var _movieDetails: MutableLiveData<Movie?> = MutableLiveData()
    val movieDetails: LiveData<Movie?> = _movieDetails

    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    override fun getMovieDetailWithId(idMovie: Int) {
        Timber.tag(TAG).d("getMovieDetailWithId() called with: idMovie = $idMovie")
        viewModelScope.launch {
            val response = movieRepository.getMovie(idMovie)
            Timber.tag(TAG).d("getMovieDetailWithId() called with response = $response")
            response.onSuccess {
                _movieDetails.value = data
            }

            response.onError {
                _error.value =
                    "${this.message()} [ Code : ${this.statusCode.code}], check your internet connection and retry"
            }

            response.onException {
                _error.value = "Something wrong with : ${this.message()}"
            }
        }
    }
}