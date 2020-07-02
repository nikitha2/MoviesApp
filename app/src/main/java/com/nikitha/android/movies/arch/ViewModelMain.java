package com.nikitha.android.movies.arch;

import android.content.Context;

import com.nikitha.android.movies.Room.MoviesFavoriteDataEntity;
import com.nikitha.android.movies.Room.MoviesDataEntity;
import com.nikitha.android.movies.Room.MoviesByTopRatedEntity;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ViewModelMain extends androidx.lifecycle.ViewModel {
    LiveData<List<MoviesDataEntity>> moviesByPopularity;
    LiveData<List<MoviesByTopRatedEntity>> moviesByTopRated;
    LiveData<List<MoviesFavoriteDataEntity>> moviesByFavorite;
    Repository repository;
    public ViewModelMain(Context context, String language) {
        repository = new Repository(context,language);
        repository.updateBDs(context,language);
        moviesByPopularity = repository.loadAllFromMoviesByPopularityTable();
        moviesByTopRated = repository.loadAllFromMoviesByTopRatedTable();
        moviesByFavorite = repository.loadAllFromMoviesByFavoriteTable();

    }

    public LiveData<List<MoviesDataEntity>> loadAllFromMoviesByPopularityTable(){
        return moviesByPopularity;
    }
    public LiveData<List<MoviesByTopRatedEntity>> loadAllFromMoviesByTopRatedTable(){
        return moviesByTopRated;
    }
    public LiveData<List<MoviesFavoriteDataEntity>> loadAllFromMoviesByFavoriteTable(){
        return moviesByFavorite;
    }


    public void updateMoviesByTopRatedTable(List<MoviesByTopRatedEntity> moviesByTopRated) {
        repository.updateMoviesByTopRatedTable(moviesByTopRated);
    }

    public void updateMoviesByPopularityTable(List<MoviesDataEntity> moviesByPopularity) {
        repository.updateMoviesByPopularityTable(moviesByPopularity);
    }

}
