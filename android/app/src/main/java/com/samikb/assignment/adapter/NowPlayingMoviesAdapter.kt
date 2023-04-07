package com.samikb.assignment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.samikb.assignment.R
import com.samikb.assignment.databinding.NowPlayingListItemBinding
import com.samikb.assignment.model.MovieApiConfiguration
import com.samikb.assignment.model.MoviesResponse

class NowPlayingMoviesAdapter(val dataSet: MoviesResponse, val configuration: MovieApiConfiguration) :
    RecyclerView.Adapter<NowPlayingMoviesAdapter.ViewHolder>() {
    class ViewHolder(val binding: NowPlayingListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.now_playing_list_item,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.imageUrl = "${configuration.images.secureBaseUrl}/w500${dataSet.results[position].posterPath}"
    }

    override fun getItemCount(): Int = dataSet.results.size
}