package com.nikitha.android.movies.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/*@Entity(tableName = "MovieReviewListTable")*/
public class MovieReviewDataEntity {
   /* @PrimaryKey(autoGenerate = true)
    private int row_id;*/
    String author;
    String content;
    String id;
    String url;

    public MovieReviewDataEntity(String author, String content, String id, String url) {
        this.author = author;
        this.content = content;
        this.id = id;
        this.url = url;
    }

    /*public int getRow_id() {
        return row_id;
    }

    public void setRow_id(int row_id) {
        this.row_id = row_id;
    }*/

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
