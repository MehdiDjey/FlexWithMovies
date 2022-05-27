package com.druide.flexwithmovies.ui.fragment.movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import coil.size.Precision
import com.druide.flexwithmovies.R
import com.druide.flexwithmovies.databinding.FragmentMovieDetailsBinding
import com.druide.flexwithmovies.model.Movie
import com.druide.flexwithmovies.utils.TAG
import com.druide.flexwithmovies.utils.formattedBackDropPath
import com.druide.flexwithmovies.utils.formattedPosterPath
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber

class MovieDetailsFragment : Fragment() {
    private var _biding: FragmentMovieDetailsBinding? = null
    private val binding get() = _biding
    private val viewModelDetails: MovieDetailsViewModel by sharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _biding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObserver()
    }


    /**
     * Populate UI
     * update and populate views according to the selected movie
     * @param movie
     */
    @SuppressLint("SetTextI18n")
    private fun populateUI(movie: Movie) {
        Timber.tag(TAG).d("populateUI() called with: movie = $movie")
        binding?.apply {
            imageViewBackdrop.load(movie.backdropPath?.formattedBackDropPath()) {
                size(500)
                precision(Precision.EXACT)
                placeholder(R.drawable.ic_the_movie_database)
                error(R.drawable.ic_the_movie_database)
                build()
            }
            ivMoviePoster.load(movie.posterPath?.formattedPosterPath()) {
                size(400)
                precision(Precision.EXACT)
                placeholder(R.drawable.ic_the_movie_database)
                error(R.drawable.ic_the_movie_database)
                build()
            }
            tvMovieTitle.text = movie.title
            tvMovieReleaseDateAndType.text =
                "${movie.releaseDate.take(4)} - ${movie.genres.joinToString { it.name }}"
            tvMovieNote.text = "Note : ${movie.voteAverage}"
            tvMovieOverview.text = movie.overview
        }
    }


    /**
     * Subscribe observer and update view data according to the movie selected
     *
     */
    private fun subscribeObserver() {
        with(viewModelDetails) {
            movieDetails.observe(viewLifecycleOwner) {
                if (it != null) {
                    populateUI(it)
                    onSuccess()
                } else {
                    onError("Something wrong !!")
                }
            }

            error.observe(viewLifecycleOwner) {
                onError(it)
            }
        }
    }


    /**
     * On success
     * update views visibility on success call
     */
    private fun onSuccess() {
        binding?.apply {
            tvMovieDetailsError.visibility = GONE
            containerMovieDetails.visibility = VISIBLE
        }
    }

    /**
     * On error
     * update views visibility on error call
     * @param message
     */
    private fun onError(message: String) {
        Timber.tag(TAG).d("onError() called with: message = $message")
        binding?.apply {
            tvMovieDetailsError.apply {
                visibility = VISIBLE
                text = message
            }
            containerMovieDetails.visibility = GONE
        }
    }

    /**
     * On destroy
     * init binding value
     */
    override fun onDestroy() {
        super.onDestroy()
        _biding = null
    }

}