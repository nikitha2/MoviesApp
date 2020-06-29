package com.nikitha.android.movies.Retrofit;

import com.nikitha.android.movies.Room.MoviesByPopularityEntity;

import java.util.List;

public class MoviesByPopularityList {

    List<MoviesByPopularityEntity> results;

    public MoviesByPopularityList(List<MoviesByPopularityEntity> results) {
        this.results = results;
    }

    public List<MoviesByPopularityEntity> getResults() {
        return results;
    }

    public void setResults(List<MoviesByPopularityEntity> results) {
        this.results = results;
    }
}
