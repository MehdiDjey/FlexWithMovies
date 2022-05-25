package com.druide.flexwithmovies.ui.fragment.movies

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.druide.flexwithmovies.R
import com.druide.flexwithmovies.databinding.FragmentMoviesBinding
import com.druide.flexwithmovies.ui.fragment.movie.MovieDetailsViewModel
import com.druide.flexwithmovies.utils.TAG
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MoviesFragment : Fragment() {
    private var _biding: FragmentMoviesBinding? = null
    private val binding get() = _biding
    private val viewModel: MoviesViewModel by sharedViewModel()
    private val vmDetails: MovieDetailsViewModel by sharedViewModel()

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




            viewModel.movies.observe(viewLifecycleOwner) {
                Log.d(TAG, "zzzzz: " + it)
            }
            vmDetails.movieDetails.observe(viewLifecycleOwner) {
                Log.d(TAG, "yyy: "+it)
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