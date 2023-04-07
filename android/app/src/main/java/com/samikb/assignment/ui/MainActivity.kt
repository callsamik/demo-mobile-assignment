package com.samikb.assignment.ui

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.samikb.assignment.viewmodel.MainActivityViewModel
import com.samikb.assignment.R
import com.samikb.assignment.databinding.ActivityMainBinding
import com.samikb.assignment.databinding.CustomProgressbarBinding
import com.samikb.assignment.model.MovieApiConfiguration
import com.samikb.assignment.network.NetworkUtil
import com.samikb.assignment.network.Response
import com.samikb.assignment.network.ServiceHelper
import com.samikb.assignment.network.Status
import com.samikb.assignment.adapter.PopularMoviesAdapter
import com.samikb.assignment.ui.movie.NowPlayingMovieListFragment
import com.samikb.assignment.ui.movie.PopularMovieListFragment


class MainActivity : AppCompatActivity() {
    private var dialog: AlertDialog? = null
    private lateinit var moviesAdapter: PopularMoviesAdapter
    private lateinit var recyclerView: RecyclerView
    private var binding: ActivityMainBinding? = null
    private val viewModel by viewModels<MainActivityViewModel> {
        MainActivityViewModel.MainActivityViewModelFactory(
            ServiceHelper(NetworkUtil.movieInfoService)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding?.let {
            it.lifecycleOwner = this
            it.viewModel = viewModel
            viewModel.getConfiguration().observe(this, configurationObserver)
            it.retryButton.setOnClickListener {
                viewModel.getConfiguration().observe(this, configurationObserver)
            }
        }
    }

    private val configurationObserver = Observer<Response<MovieApiConfiguration>> {
        it?.let { response ->
            when (response.status) {
                Status.SUCCESS -> {
                    dialog?.dismiss()
                    response.data?.let { init(it) }
                }
                Status.ERROR -> {
                    dialog?.dismiss()
                }
                Status.LOADING -> {
                    dialog = showProgressDialog(
                        this,
                        "Please wait..."
                    )
                }
            }
        }
    }

    private fun init(configuration: MovieApiConfiguration){
        supportFragmentManager.commit {
            replace(R.id.now_playing_movies_fragment_container, NowPlayingMovieListFragment.newInstance(configuration), NowPlayingMovieListFragment.TAG)
            replace(R.id.popular_movies_fragment_container, PopularMovieListFragment.newInstance(configuration))
        }
    }

    private fun getAlertDialog(
        context: Context,
        layout: Int,
        setCancellationOnTouchOutside: Boolean
    ): AlertDialog {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        val binding = CustomProgressbarBinding.inflate(layoutInflater)
        builder.setView(binding.root)
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(setCancellationOnTouchOutside)
        return dialog
    }

    fun showProgressDialog(context: Context, message: String): AlertDialog {
        val dialog = getAlertDialog(
            context,
            R.layout.custom_progressbar,
            setCancellationOnTouchOutside = false
        )
        dialog.show()
        val messageTextView = dialog.findViewById<TextView>(R.id.text_progress_bar)
        messageTextView?.text = message
        return dialog
    }
}
