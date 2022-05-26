package com.druide.flexwithmovies.ui.fragment.movie

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import coil.size.Precision
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.druide.flexwithmovies.R
import com.druide.flexwithmovies.databinding.FragmentMovieDetailsBinding
import com.druide.flexwithmovies.databinding.FragmentMoviesBinding
import com.druide.flexwithmovies.model.Movie
import com.druide.flexwithmovies.utils.TAG
import com.druide.flexwithmovies.utils.formattedBackDropPath
import com.druide.flexwithmovies.utils.formattedPosterPath
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MovieDetailsFragment : Fragment() {
    private var _biding: FragmentMovieDetailsBinding? = null
    private val binding get() = _biding
    private val viewModelDetails: MovieDetailsViewModel by sharedViewModel()
    companion object {
        fun newInstance() = MovieDetailsFragment()
    }
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


    private fun populateUI(movie : Movie) {
        binding?.apply {
            imageViewBackdrop.load(movie.backdropPath?.formattedBackDropPath())  {
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
            tvMovieReleaseDateAndType.text = "${movie.releaseDate.take(4)} - ${movie.genres.joinToString { it.name }}"
            tvMovieNote.text = "Note : ${movie.voteAverage}"
            tvMovieOverview.text = movie.overview
        }
    }


    private fun subscribeObserver() {
        viewModelDetails.movieDetails.observe(viewLifecycleOwner) {
            Log.d(TAG, "bbbb() called"+it)
            if (it != null) {
                populateUI(it)
            }
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