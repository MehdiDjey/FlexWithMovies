package com.druide.flexwithmovies.ui.fragment.movie

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.druide.flexwithmovies.R
import com.druide.flexwithmovies.databinding.FragmentMovieDetailsBinding
import com.druide.flexwithmovies.databinding.FragmentMoviesBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class MovieDetailsFragment : Fragment() {
    private var _biding: FragmentMovieDetailsBinding? = null
    private val binding get() = _biding
    companion object {
        fun newInstance() = MovieDetailsFragment()
    }

    private  val viewModel: MovieDetailsViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _biding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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