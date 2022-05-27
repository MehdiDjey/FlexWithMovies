package com.druide.flexwithmovies.ui.fragment.movies

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.druide.flexwithmovies.`interface`.IOnMovies
import com.druide.flexwithmovies.model.Movies
import com.druide.flexwithmovies.repository.MoviesRepository
import com.druide.flexwithmovies.utils.TAG
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import kotlinx.coroutines.launch
import timber.log.Timber

class MoviesViewModel(private val moviesRepository: MoviesRepository) : ViewModel(), IOnMovies {
    private var _movies: MutableLiveData<Movies?> = MutableLiveData()
    val movies: LiveData<Movies?> = _movies

    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private var _canLoadMore: MutableLiveData<Boolean> = MutableLiveData()
    val canLoadMore: LiveData<Boolean> = _canLoadMore

    private var currentPage = -1


    /**
     * Get movie according to the selected page
     *
     * @param pageIndex Int by default the value should be 1
     */
    override fun getMovieAtPage(pageIndex: Int) {
        Timber.tag(TAG).d("getMovieAtPage() called with: pageIndex = $pageIndex")
        currentPage = pageIndex
        viewModelScope.launch {
            val response = moviesRepository.getMovies(pageIndex)
            Timber.tag(TAG).d("getMovieAtPage() called with response = $response")
            response.onSuccess {
                _movies.value = data
                _canLoadMore.value = currentPage < data.totalPages
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