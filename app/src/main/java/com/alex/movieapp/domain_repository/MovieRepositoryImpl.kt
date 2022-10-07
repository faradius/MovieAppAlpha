package com.alex.movieapp.domain_repository

import com.alex.movieapp.core.InternetCheck
import com.alex.movieapp.data.local.LocalMovieDataSource
import com.alex.movieapp.data.model.MovieList
import com.alex.movieapp.data.model.toMovieEntity
import com.alex.movieapp.data.remote.RemoteMovieDataSource

class MovieRepositoryImpl(
    private val dataSourceRemote: RemoteMovieDataSource,
    private val dataSourceLocal:LocalMovieDataSource
    ): MovieRepository {
    //Desde el repositorio(Domain) estamos llamando al datasource a sus metodos
    override suspend fun getUpcomingMovies(): MovieList{
        return if(InternetCheck.isNetworkAvailable()){
            //Buscamos la información a la base de datos online
            dataSourceRemote.getUpcomingMovies().results.forEach{   movie->
                //Guardamos la información a nuestra base de datos local
                dataSourceLocal.saveMovie(movie.toMovieEntity("upcoming"))
            }
            //Mostramos la información que recuperamos de la base de datos online por medio del local
            dataSourceLocal.getUpcomingMovies()
        }else{
            dataSourceLocal.getUpcomingMovies()
        }
    }

    override suspend fun getTopRatedMovies(): MovieList{
        return if(InternetCheck.isNetworkAvailable()){
            dataSourceRemote.getTopRatedMovies().results.forEach{   movie->
                dataSourceLocal.saveMovie(movie.toMovieEntity("toprated"))
            }
            dataSourceLocal.getTopRatedMovies()
        }
        else{
            dataSourceLocal.getTopRatedMovies()
        }


    }

    override suspend fun getPopularMovies(): MovieList {
        return if(InternetCheck.isNetworkAvailable()){
            dataSourceRemote.getPopularMovies().results.forEach{   movie->
                dataSourceLocal.saveMovie(movie.toMovieEntity("popular"))
            }
            dataSourceLocal.getPopularMovies()
        }else{
            dataSourceLocal.getPopularMovies()
        }
    }
}