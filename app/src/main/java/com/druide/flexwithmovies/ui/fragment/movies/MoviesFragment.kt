package com.druide.flexwithmovies.ui.fragment.movies

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.druide.flexwithmovies.databinding.FragmentMoviesBinding
import com.druide.flexwithmovies.model.Results
import com.druide.flexwithmovies.ui.adapter.movies.MoviesAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MoviesFragment : Fragment(), MoviesAdapter.Interaction {
    private var _biding: FragmentMoviesBinding? = null
    private val binding get() = _biding
    private val viewModel: MoviesViewModel by sharedViewModel()
    //private val vmDetails: MovieDetailsViewModel by sharedViewModel()
    private lateinit var moviesAdapter: MoviesAdapter

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
        viewModel.movies.observe(viewLifecycleOwner) {
            moviesAdapter.movies = it?.results!!
        }
    }

    private fun setupListener() {

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
        Toast.makeText(requireContext(), movie.title, Toast.LENGTH_SHORT).show()
    }
}