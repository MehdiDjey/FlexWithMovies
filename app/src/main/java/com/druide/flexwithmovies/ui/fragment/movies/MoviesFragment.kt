package com.druide.flexwithmovies.ui.fragment.movies

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.druide.flexwithmovies.R
import com.druide.flexwithmovies.databinding.FragmentMoviesBinding
import com.druide.flexwithmovies.model.Results
import com.druide.flexwithmovies.ui.adapter.movies.MoviesAdapter
import com.druide.flexwithmovies.ui.fragment.movie.MovieDetailsViewModel
import com.druide.flexwithmovies.utils.TAG
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MoviesFragment : Fragment(), MoviesAdapter.Interaction {
    private var _biding: FragmentMoviesBinding? = null
    private val binding get() = _biding
    private val viewModel: MoviesViewModel by sharedViewModel()
    private val viewModelDetails: MovieDetailsViewModel by sharedViewModel()
    private lateinit var moviesAdapter: MoviesAdapter

    private var page = 1

    companion object {
        fun newInstance() = MoviesFragment()
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _biding =  FragmentMoviesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupListener()
        subscribeObserver()

    }

    private fun setupAdapter() {
        binding?.apply {
            moviesAdapter = MoviesAdapter(this@MoviesFragment)
            rvMovies.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = moviesAdapter
            }
            moviesAdapter.stateRestorationPolicy =
                RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
    }

    private fun subscribeObserver() {
        with(viewModel) {
            movies.observe(viewLifecycleOwner) {
                if (it?.results?.isEmpty() == true) {
                    onError("Nothing to show here !!")
                } else {
                    onSuccess()
                    moviesAdapter.movies = it!!.results

                    Log.d(TAG, "subscribeObserver() called"+moviesAdapter.movies.size)
                }
            }

            error.observe(viewLifecycleOwner) {
                onError(it)
            }

            canLoadMore.observe(viewLifecycleOwner) {
                if (it) {
                    page += 1
                   // getMovieAtPage(page)


                }
            }
        }
    }

    private fun setupListener() {

    }


    private fun onError(message : String) {
        binding?.apply {
            containerMovies.visibility = GONE
            tvEmptyData.apply {
                visibility = VISIBLE
                text = message
            }
        }
    }

    private fun onSuccess() {
        binding?.apply {
            tvEmptyData.visibility = GONE
            containerMovies.visibility = VISIBLE
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

    override fun onMovieSelected(movie: Results) {
        Toast.makeText(requireContext(), movie.title+" ${movie.id}", Toast.LENGTH_SHORT).show()
        viewModelDetails.getMovieDetailWithId(movie.id)
        Navigation.findNavController(requireView()).navigate(R.id.action_moviesFragment_to_moviesDetailsFragment)
    }
}