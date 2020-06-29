package com.nikitha.android.movies.arch;

import android.content.Context;

import com.nikitha.android.movies.Room.MoviesByPopularityEntity;
import com.nikitha.android.movies.Room.MoviesByTopRatedEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class ViewModelMain extends androidx.lifecycle.ViewModel {
    LiveData<List<MoviesByPopularityEntity>> moviesByPopularity;
    LiveData<List<MoviesByTopRatedEntity>> moviesByTopRated;

    Repository repository;
    String language;
    public ViewModelMain(Context context, String language) {
        repository = new Repository(context,language);
        repository.updateDBsPeriodically(context);
        moviesByPopularity = repository.loadAllFromMoviesByPopularityTable();
        moviesByTopRated = repository.loadAllFromMoviesByTopRatedTable();

    }

    public LiveData<List<MoviesByPopularityEntity>> loadAllFromMoviesByPopularityTable(){
        return moviesByPopularity;
    }
    public LiveData<List<MoviesByTopRatedEntity>> loadAllFromMoviesByTopRatedTable(){
        return moviesByTopRated;
    }



    public void updateMoviesByTopRatedTable(List<MoviesByTopRatedEntity> moviesByTopRated) {
        repository.updateMoviesByTopRatedTable(moviesByTopRated);
    }

    public void updateMoviesByPopularityTable(List<MoviesByPopularityEntity> moviesByPopularity) {
        repository.updateMoviesByPopularityTable(moviesByPopularity);
    }
}
