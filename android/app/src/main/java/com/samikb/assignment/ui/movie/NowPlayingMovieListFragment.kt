package com.samikb.assignment.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.samikb.assignment.adapter.NowPlayingMoviesAdapter
import com.samikb.assignment.databinding.FragmentNowPlayingMovieListBinding
import com.samikb.assignment.model.MovieApiConfiguration
import com.samikb.assignment.network.NetworkUtil
import com.samikb.assignment.network.ServiceHelper
import com.samikb.assignment.viewmodel.NowPlayingMoviesViewModel

private const val ARG_CONFIGURATION = "config"

class NowPlayingMovieListFragment : Fragment() {
    private var configuration: MovieApiConfiguration? = null
    private var binding: FragmentNowPlayingMovieListBinding? = null
    private val viewModel by viewModels<NowPlayingMoviesViewModel> {
        NowPlayingMoviesViewModel.NowPlayingMovieListViewModelFactory(
            ServiceHelper(NetworkUtil.movieInfoService)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            configuration = it.getParcelable(ARG_CONFIGURATION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentNowPlayingMovieListBinding.inflate(inflater).let {
        binding = it
        it.lifecycleOwner = viewLifecycleOwner
        it.viewModel = viewModel
        it.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.nowPlayingMovies.observe(viewLifecycleOwner){
            binding?.adapter = NowPlayingMoviesAdapter(it, configuration!!)
        }
        viewModel.getNowPlayingMovieList()
    }

    companion object {
        val TAG = NowPlayingMovieListFragment::class.qualifiedName

        @JvmStatic
        fun newInstance(config: MovieApiConfiguration) =
            NowPlayingMovieListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CONFIGURATION, config)
                }
            }
    }
}