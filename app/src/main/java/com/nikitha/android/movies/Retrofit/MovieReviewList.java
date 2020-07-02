package com.nikitha.android.movies.Retrofit;

import com.nikitha.android.movies.Room.MovieReviewDataEntity;
import com.nikitha.android.movies.Room.VideosDataEntity;

import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MovieReviewListTable")
public class MovieReviewList {
    @PrimaryKey(autoGenerate = true)
    private int row_id;
    int movie_id;
    String id;
    String author;
    String content;
    String url;

    public MovieReviewList(int movie_id, String id, String author, String content, String url) {
        this.movie_id = movie_id;
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public int getRow_id() {
        return row_id;
    }

    public void setRow_id(int row_id) {
        this.row_id = row_id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
