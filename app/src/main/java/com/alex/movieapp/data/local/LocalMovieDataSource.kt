package com.alex.movieapp.data.local

import com.alex.movieapp.data.model.MovieEntity
import com.alex.movieapp.data.model.MovieList
import com.alex.movieapp.data.model.toMovieList
import com.alex.movieapp.utils.AppConstants

class LocalMovieDataSource(private val movieDao: MovieDao) {

    suspend fun getUpcomingMovies(): MovieList{
        return movieDao.getAllMovies().filter { it.movie_type == "upcoming" }.toMovieList()
    }

    suspend fun getTopRatedMovies(): MovieList{
        return movieDao.getAllMovies().filter { it.movie_type == "toprated" }.toMovieList()
    }

    suspend fun getPopularMovies(): MovieList{
        return movieDao.getAllMovies().filter { it.movie_type == "popular" }.toMovieList()
    }

    suspend fun saveMovie(movie:MovieEntity){
        movieDao.saveMovie(movie)
    }

}