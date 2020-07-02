package com.nikitha.android.movies.Retrofit;

import com.nikitha.android.movies.Room.MoviesByTopRatedEntity;
import com.nikitha.android.movies.Room.MoviesDataEntity;

import java.util.List;

public class MoviesByTopRatedList {

    List<MoviesByTopRatedEntity> results;

    public MoviesByTopRatedList(List<MoviesByTopRatedEntity> results) {
        this.results = results;
    }

    public List<MoviesByTopRatedEntity> getResults() {
        return results;
    }

    public void setResults(List<MoviesByTopRatedEntity> results) {
        this.results = results;
    }
}
