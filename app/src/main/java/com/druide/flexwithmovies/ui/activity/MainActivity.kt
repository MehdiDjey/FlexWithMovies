package com.druide.flexwithmovies.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.druide.flexwithmovies.databinding.ActivityMainBinding
import com.druide.flexwithmovies.ui.fragment.movie.MovieDetailsViewModel
import com.druide.flexwithmovies.ui.fragment.movies.MoviesViewModel
import kotlinx.coroutines.runBlocking
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var biding: ActivityMainBinding
    private val moviesViewModel: MoviesViewModel by viewModel()
    private val details: MovieDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biding = ActivityMainBinding.inflate(layoutInflater)
        biding.apply {
            setContentView(root)
            getMovies()
        }
    }

    private fun getMovies() {

        moviesViewModel.getMovieAtPage(1)

        details.getMovieDetailWithId(526896)

    }
}