package com.nikitha.android.movies.Room;

import com.google.gson.annotations.SerializedName;
import com.nikitha.android.movies.Retrofit.MovieTrailersList;

import java.util.List;

public class MovieTrailersDataEntityList {

    @SerializedName("id")
    int movie_id;
    List<MovieTrailerDataEntity> results;

    public MovieTrailersDataEntityList(int movie_id, List<MovieTrailerDataEntity> results) {
        this.movie_id = movie_id;
        this.results = results;
    }

    public List<MovieTrailerDataEntity> getResults() {
        return results;
    }

    public void setResults(List<MovieTrailerDataEntity> results) {
        this.results = results;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

}

