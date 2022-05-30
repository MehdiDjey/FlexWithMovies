package com.druide.flexwithmovies.ui.fragment.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
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
import timber.log.Timber

/**
 * Movies fragment
 *
 * @constructor Create empty Movies fragment
 */
class MoviesFragment : Fragment(), MoviesAdapter.Interaction, SearchView.OnQueryTextListener {
    private var _biding: FragmentMoviesBinding? = null
    private val binding get() = _biding
    private val viewModel: MoviesViewModel by sharedViewModel()
    private val viewModelDetails: MovieDetailsViewModel by sharedViewModel()
    private lateinit var moviesAdapter: MoviesAdapter
    private var pageIndex= 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _biding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        subscribeObserver()
    }

    /**
     * Setup the recyclerview list adapter
     *
     */
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

    override fun onResume() {
        super.onResume()
        // restore stored list on change fragment
        if (viewModel.fetchedList.isNotEmpty())
            moviesAdapter.movies = viewModel.fetchedList
    }

    /**
     * Subscribe observer
     *
     * Listen to the movies::LiveData and update list data
     *
     */
    private fun subscribeObserver() {
        with(viewModel) {
            movies.observe(viewLifecycleOwner) {
                if (it?.results == null || it.results.isEmpty()) {
                    onError("Nothing to show here !!")
                } else {
                    populateList(it.results)

                }
            }

            error.observe(viewLifecycleOwner) {
                onError(it)
            }

            canLoadMore.observe(viewLifecycleOwner) {
                //this@MoviesFragment.canLoadMore = it

                if (it) {
                    lifecycleScope.launch {
                        delay(1000)
                        pageIndex += 1
                        viewModel.getMovieAtPage(pageIndex)
                    }

                }

            }
        }
    }


    private fun populateList(results: List<Results>) {
        val currentList = moviesAdapter.movies
        if (currentList.isEmpty()) {
            // init list
            moviesAdapter.movies = results
            onSuccess()
        } else {
            // on load more item
            val newList = currentList as MutableList
            newList.addAll(results)
            moviesAdapter.movies = newList
        }
    }


    /**
     * On error
     *
     * function called on return failed with Error or Exception
     *
     * @param message
     */
    private fun onError(message: String) {
        Timber.tag(TAG).d("onError() called with: message = $message")
        binding?.apply {
            containerMovies.visibility = GONE
            tvEmptyData.apply {
                visibility = VISIBLE
                text = message
            }
        }
    }

    /**
     * On success
     *
     * function called on fetch data with success status
     *
     * update components visibility on success call
     */
    private fun onSuccess() {
        Timber.tag(TAG).d("onSuccess() called")
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

    /**
     * On movie selected
     *
     * Listener when user select movie
     *
     * get movie details using id  and redirect to movie details view
     *
     * @param movie
     */
    override fun onMovieSelected(movie: Results) {
        Timber.tag(TAG).d("onMovieSelected() called with: movie = $movie")
        //store current list before navigate to prevent from lost data
        viewModel.fetchedList = moviesAdapter.movies

        //call the selected movie by id
        viewModelDetails.getMovieDetailWithId(movie.id)

        // navigate to the details fragment
        Navigation.findNavController(requireView())
            .navigate(R.id.action_moviesFragment_to_moviesDetailsFragment)
    }

    /**
     * On query text submit
     *
     * listen on search query submit action and update movies list
     *
     * @param query
     * @return
     */
    override fun onQueryTextSubmit(query: String?): Boolean {
        Timber.tag(TAG).d("onQueryTextSubmit() called with: query = $query")
        updateListOnQuery(query)

        return false
    }

    /**
     * On query text change
     *
     * listen on search query change and update movies list
     *
     * @param newText
     * @return
     */
    override fun onQueryTextChange(newText: String?): Boolean {
        Timber.tag(TAG).d("onQueryTextChange() called with: newText = $newText")
        updateListOnQuery(newText)

        return false
    }

    /**
     * Update list on query
     * function called to update list according the searchView query
     *
     * @param query
     */
    private fun updateListOnQuery(query: String?) {
        Timber.tag(TAG).d("updateListOnQuery() called with: query = $query")
        // TODO: update movies list

    }

    /**
     * Filter listener
     *
     */
    private fun filterListener() {
        // TODO: update list by filter
    }
}