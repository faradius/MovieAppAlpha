package com.alex.movieapp.domain_repository

import com.alex.movieapp.data.model.MovieList
import com.alex.movieapp.data.remote.MovieDataSource

class MovieRepositoryImpl(private val dataSource: MovieDataSource): MovieRepository {
    //Desde el repositorio(Domain) estamos llamando al datasource a sus metodos
    override suspend fun getUpcomingMovies(): MovieList = dataSource.getUpcomingMovies()

    override suspend fun getTopRatedMovies(): MovieList = dataSource.getTopRatedMovies()

    override suspend fun getPopularMovies(): MovieList = dataSource.getPopularMovies()
}