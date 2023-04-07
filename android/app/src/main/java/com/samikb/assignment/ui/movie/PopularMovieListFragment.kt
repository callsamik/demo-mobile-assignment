package com.samikb.assignment.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.samikb.assignment.adapter.PopularMoviesAdapter
import com.samikb.assignment.databinding.FragmentPopularMovieListBinding
import com.samikb.assignment.model.MovieApiConfiguration
import com.samikb.assignment.network.NetworkUtil
import com.samikb.assignment.network.ServiceHelper
import com.samikb.assignment.ui.custom.SimpleDividerItemDecoration
import com.samikb.assignment.viewmodel.PopularMoviesViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val ARG_CONFIGURATION = "config"

class PopularMovieListFragment : Fragment() {
    private var configuration: MovieApiConfiguration? = null
    private var binding: FragmentPopularMovieListBinding? = null
    private lateinit var adapter: PopularMoviesAdapter
    private val viewModel by viewModels<PopularMoviesViewModel> {
        PopularMoviesViewModel.PopularMoviesViewModelFactory(
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
    ): View? = FragmentPopularMovieListBinding.inflate(layoutInflater).let {
        binding = it
        binding?.lifecycleOwner = viewLifecycleOwner
        adapter = PopularMoviesAdapter(configuration!!)
        binding?.adapter = adapter
        binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.popularMoviesRecyclerView?.addItemDecoration(SimpleDividerItemDecoration(requireActivity()))
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getPopularMovies().collectLatest { movies ->
                adapter.submitData(movies)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        val TAG = PopularMovieListFragment::class.qualifiedName

        @JvmStatic
        fun newInstance(config: MovieApiConfiguration) =
            PopularMovieListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_CONFIGURATION, config)
                }
            }
    }
}