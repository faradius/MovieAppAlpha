package com.alex.movieapp.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.alex.movieapp.R
import com.alex.movieapp.core.ResourceResult
import com.alex.movieapp.data.local.AppDatabase
import com.alex.movieapp.data.local.LocalMovieDataSource
import com.alex.movieapp.data.model.Movie
import com.alex.movieapp.data.remote.RemoteMovieDataSource
import com.alex.movieapp.databinding.FragmentMovieBinding
import com.alex.movieapp.domain_repository.MovieRepositoryImpl
import com.alex.movieapp.domain_repository.RetrofitClient
import com.alex.movieapp.presentation.MovieViewModel
import com.alex.movieapp.presentation.MovieViewModelFactory
import com.alex.movieapp.ui.movie.adapters.MovieAdapter
import com.alex.movieapp.ui.movie.adapters.concat.PopularMovieConcatAdapter
import com.alex.movieapp.ui.movie.adapters.concat.TopRatedMovieConcatAdapter
import com.alex.movieapp.ui.movie.adapters.concat.UpcomingMovieConcatAdapter


class MovieFragment : Fragment(R.layout.fragment_movie),MovieAdapter.OnMovieClickListener {

    private lateinit var binding:FragmentMovieBinding
    private val viewModel by viewModels<MovieViewModel> {
        MovieViewModelFactory(
            MovieRepositoryImpl(
                RemoteMovieDataSource(RetrofitClient.webService),
                LocalMovieDataSource(AppDatabase.getDatabase(requireContext()).movieDao())
            )) }

    private lateinit var concatAdapter: ConcatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)
        binding.progressBar.visibility = View.GONE

        concatAdapter = ConcatAdapter()

        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, Observer { result->
            when(result){
                is ResourceResult.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is ResourceResult.Success -> {
                    binding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(0,UpcomingMovieConcatAdapter(MovieAdapter(result.data.first.results,this@MovieFragment)))
                        addAdapter(1,TopRatedMovieConcatAdapter(MovieAdapter(result.data.second.results,this@MovieFragment)))
                        addAdapter(2,PopularMovieConcatAdapter(MovieAdapter(result.data.third.results,this@MovieFragment)))
                    }
                    binding.rvMovies.adapter = concatAdapter
                }
                is ResourceResult.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("LiveDataError", "${result.exception}")
                }
            }
        })


    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movie.poster_path,
            movie.backdrop_path,
            movie.vote_average.toFloat(),
            movie.vote_count,
            movie.overview,
            movie.title,
            movie.original_language,
            movie.release_date
        )
        findNavController().navigate(action)
    }
}