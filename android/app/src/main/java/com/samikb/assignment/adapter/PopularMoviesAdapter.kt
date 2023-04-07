package com.samikb.assignment.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samikb.assignment.databinding.PopularMovieListItemBinding
import com.samikb.assignment.model.MovieApiConfiguration
import com.samikb.assignment.model.MovieDetails
import com.samikb.assignment.ui.movie.MovieDetailsActivity
import com.samikb.assignment.util.Util
import com.google.gson.Gson
import java.util.*

class PopularMoviesAdapter(val configuration: MovieApiConfiguration) :
    PagingDataAdapter<MovieDetails, PopularMoviesAdapter.MovieDetailsViewHolder>(MovieDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieDetailsViewHolder {
        val holder = MovieDetailsViewHolder(
            PopularMovieListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

        return holder
    }

    override fun onBindViewHolder(holder: MovieDetailsViewHolder, position: Int) = holder.bind(getItem(position))

    inner class MovieDetailsViewHolder(
        val binding: PopularMovieListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movieDetails: MovieDetails?) {
            movieDetails?.let {
                binding.title = movieDetails.title
                binding.duration = Util.getFormattedRuntime(movieDetails.movieCompleteDetails?.runtime ?: 0)
                binding.imageUrl = "${configuration.images.secureBaseUrl}/w500${movieDetails.posterPath}"
                binding.releaseDate = Util.getFormattedDate(movieDetails.releaseDate)
                binding.rating = (movieDetails.voteAverage * 10).toInt()
                binding.root.setOnClickListener { view ->
                    movieDetails.movieCompleteDetails?.let { movieCompleteDetails ->
                        val intent = Intent(view.context, MovieDetailsActivity::class.java)
                        intent.putExtra(MovieDetailsActivity.MOVIE_COMPLETE_DETAILS_EXTRA, Gson().toJson(movieCompleteDetails))
                        intent.putExtra(MovieDetailsActivity.API_CONFIGURATION_EXTRA, configuration)
                        ContextCompat.startActivity(view.context, intent, null)
                    }
                }
            }
        }
    }
}