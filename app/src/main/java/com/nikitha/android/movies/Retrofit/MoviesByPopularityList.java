package com.nikitha.android.movies.Retrofit;

import com.nikitha.android.movies.Room.MoviesDataEntity;

import java.util.List;

public class MoviesByPopularityList {

    List<MoviesDataEntity> results;

    public MoviesByPopularityList(List<MoviesDataEntity> results) {
        this.results = results;
    }

    public List<MoviesDataEntity> getResults() {
        return results;
    }

    public void setResults(List<MoviesDataEntity> results) {
        this.results = results;
    }
}
