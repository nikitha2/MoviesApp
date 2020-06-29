package com.nikitha.android.movies.Room;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class MoviesDao {

    @Query("SELECT * FROM MoviesByPopularityTable ORDER BY popularity")
    public abstract LiveData<List<MoviesByPopularityEntity>> loadAllFromMoviesByPopularityTable();

    @Query("SELECT * FROM MoviesByTopRatedTable ORDER BY vote_average")
    public abstract LiveData<List<MoviesByTopRatedEntity>> loadAllFromMoviesByTopRatedTable();

//    @Insert
//    public abstract void insertTask(TaskEntry taskEntry);

//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    public abstract void updateTask(TaskEntry taskEntry);
//
//    @Delete
//    public abstract void deleteTask(TaskEntry taskEntry);

    @Transaction
    public void updateMoviesByPopularityTable(List<MoviesByPopularityEntity> moviesByPopularityList) {
        deleteAllEntriesFromMoviesByPopularityTable();
        insertAllToMoviesByPopularityTable(moviesByPopularityList);
    }

    @Insert
    public abstract void insertAllToMoviesByPopularityTable(List<MoviesByPopularityEntity> tasks);

    @Query("DELETE FROM MoviesByPopularityTable")
    public abstract void  deleteAllEntriesFromMoviesByPopularityTable();

    @Transaction
    public void updateMoviesByTopRatedTable(List<MoviesByTopRatedEntity> moviesByTopRatedList) {
        deleteAllEntriesFromMoviesByTopRatedTable();
        insertAllToMoviesByTopRatedTable(moviesByTopRatedList);
    }

    @Insert
    public abstract void insertAllToMoviesByTopRatedTable(List<MoviesByTopRatedEntity> tasks);

    @Query("DELETE FROM MoviesByTopRatedTable")
    public abstract void  deleteAllEntriesFromMoviesByTopRatedTable();

}
