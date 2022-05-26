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

class MoviesViewModel(private val moviesRepository: MoviesRepository) : ViewModel(), IOnMovies {
    private var _movies: MutableLiveData<Movies?> = MutableLiveData()
    val movies: LiveData<Movies?> = _movies

    private var _error: MutableLiveData<String> = MutableLiveData()
    val error: LiveData<String> = _error

    private var _canLoadMore: MutableLiveData<Boolean> = MutableLiveData()
    val canLoadMore: LiveData<Boolean> = _canLoadMore

    private var currentPage = -1


    override fun getMovieAtPage(pageIndex: Int) {
        Log.d(TAG, "getMovieAtPage() called with: pageIndex = $pageIndex")
        currentPage = pageIndex
        viewModelScope.launch {
            val response = moviesRepository.getMovies(pageIndex)
            response.onSuccess {
                _movies.value = data
                _canLoadMore.value = currentPage < data.totalPages

                Log.d(TAG, "getMovieAtPage() called with: can load more = $_canLoadMore")
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