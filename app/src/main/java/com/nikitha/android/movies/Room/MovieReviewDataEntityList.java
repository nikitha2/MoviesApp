package com.nikitha.android.movies.Room;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieReviewDataEntityList {

    @SerializedName("id")
    int movie_id;
    List<MovieReviewDataEntity> results;

    public MovieReviewDataEntityList(int movie_id, List<MovieReviewDataEntity> results) {
        this.movie_id = movie_id;
        this.results = results;
    }




    public List<MovieReviewDataEntity> getResults() {
        return results;
    }

    public void setResults(List<MovieReviewDataEntity> results) {
        this.results = results;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }


}

