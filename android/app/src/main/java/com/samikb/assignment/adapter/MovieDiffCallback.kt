package com.samikb.assignment.adapter

import androidx.recyclerview.widget.DiffUtil
import com.samikb.assignment.model.MovieDetails

class MovieDiffCallBack : DiffUtil.ItemCallback<MovieDetails>() {
    override fun areItemsTheSame(oldItem: MovieDetails, newItem: MovieDetails): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieDetails, newItem: MovieDetails): Boolean {
        return oldItem == newItem
    }
}