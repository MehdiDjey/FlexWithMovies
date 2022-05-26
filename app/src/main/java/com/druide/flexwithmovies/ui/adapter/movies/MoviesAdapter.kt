package com.druide.flexwithmovies.ui.adapter.movies

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Precision
import coil.transform.RoundedCornersTransformation
import com.druide.flexwithmovies.R
import com.druide.flexwithmovies.databinding.ItemRowMovieBinding
import com.druide.flexwithmovies.model.Results
import com.druide.flexwithmovies.utils.AutoUpdatableAdapter
import com.druide.flexwithmovies.utils.TAG
import com.druide.flexwithmovies.utils.formattedPosterPath
import kotlin.math.round
import kotlin.properties.Delegates

class MoviesAdapter (private val interaction: Interaction) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>(), AutoUpdatableAdapter {

    var movies: List<Results> by Delegates.observable(emptyList()) { _, oldList, newList ->
        autoNotify(oldList, newList) { o, n -> o.id == n.id }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRowMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), interaction
        )
    }

    override fun onBindViewHolder(holder: MoviesAdapter.ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }


    override fun getItemCount(): Int = movies.size

    override fun getItemId(position: Int): Long {
        return movies[position].id.hashCode().toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return movies[position].id
    }

    interface Interaction {
        fun onMovieSelected(movie: Results)
    }


    inner class ViewHolder(
        private val row: ItemRowMovieBinding,
        private val interaction: Interaction
    ) : RecyclerView.ViewHolder(row.root) {


        fun bind(movie: Results) {
           row.apply {
               tvMovieTitleRow.text =  movie.title
               tvMovieReleaseDateRow.text = movie.releaseDate
               ivMovieRow.load(movie.posterPath?.formattedPosterPath()) {
                   size(500)
                   crossfade(true)
                   precision(Precision.EXACT)
                   placeholder(R.drawable.ic_the_movie_database)
                   error(R.drawable.ic_the_movie_database)
                   build()
               }
               itemView.setOnClickListener {
                   interaction.onMovieSelected(movie)
               }
           }
        }

    }

}
