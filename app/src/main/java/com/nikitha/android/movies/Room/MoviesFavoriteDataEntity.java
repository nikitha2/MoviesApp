package com.nikitha.android.movies.Room;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//Extend the favorites database to store the movie poster, synopsis, user rating, and release date, and display them even when offline.
@Entity(tableName = "MovieFavoriteListTable")
public class MoviesFavoriteDataEntity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private Long row_id;
    private Double popularity;
    Integer vote_count;
    private String poster_path;
    @SerializedName("id")
    private Integer movie_id;
    private String backdrop_path;
    private String original_language;
    private String original_title;
    private String title;
    private Double vote_average;
    private String overview;
    private String release_date;
    private Boolean isfavorite;

    public Boolean getIsfavorite() {
        return isfavorite;
    }

    public void setIsfavorite(Boolean isfavorite) {
        this.isfavorite = isfavorite;
    }

    public MoviesFavoriteDataEntity(Double popularity, Integer vote_count, String poster_path, Integer movie_id,  String backdrop_path, String original_language, String original_title, String title, Double vote_average, String overview, String release_date,Boolean isfavorite) {
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.poster_path = poster_path;
        this.movie_id = movie_id;
        this.backdrop_path = backdrop_path;
        this.original_language = original_language;
        this.original_title = original_title;
        this.title = title;
        this.vote_average = vote_average;
        this.overview = overview;
        this.release_date = release_date;
        this.isfavorite=isfavorite;
    }


    public Integer getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(Integer movie_id) {
        this.movie_id = movie_id;
    }
    public Long getRow_id() {
        return row_id;
    }

    public void setRow_id(Long row_id) {
        this.row_id = row_id;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVote_count() {
        return vote_count;
    }

    public void setVote_count(Integer vote_count) {
        this.vote_count = vote_count;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }




    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }




}
