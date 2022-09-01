package com.alex.movieapp.data.remote

import com.alex.movieapp.data.model.MovieList
import com.alex.movieapp.domain_repository.WebService
import com.alex.movieapp.utils.AppConstants

class RemoteMovieDataSource(private val webService: WebService) {

    suspend fun getUpcomingMovies(): MovieList = webService.getUpcomingMovies(AppConstants.API_KEY)

    suspend fun getTopRatedMovies(): MovieList = webService.getTopRatedMovies(AppConstants.API_KEY)

    suspend fun getPopularMovies(): MovieList = webService.getPopularMovies(AppConstants.API_KEY)
}