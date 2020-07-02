package com.nikitha.android.movies.Retrofit;

import com.nikitha.android.movies.Room.VideosDataEntity;

import java.util.List;

import androidx.room.Entity;

public class MoviesVideosList {

    List<VideosDataEntity> results;

    public MoviesVideosList(List<VideosDataEntity> results) {
        this.results = results;
    }

    public List<VideosDataEntity> getResults() {
        return results;
    }

    public void setResults(List<VideosDataEntity> results) {
        this.results = results;
    }
}
