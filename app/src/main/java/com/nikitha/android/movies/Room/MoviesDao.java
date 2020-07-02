package com.nikitha.android.movies.Room;

import com.nikitha.android.movies.Retrofit.MovieReviewList;
import com.nikitha.android.movies.Retrofit.MovieTrailersList;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class MoviesDao {
boolean TRUE=true;
    @Query("SELECT * FROM MoviesByPopularityTable ORDER BY popularity")
    public abstract LiveData<List<MoviesDataEntity>> loadAllFromMoviesByPopularityTable();

    @Query("SELECT * FROM MoviesByTopRatedTable ORDER BY vote_average")
    public abstract LiveData<List<MoviesByTopRatedEntity>> loadAllFromMoviesByTopRatedTable();

    @Query("SELECT * FROM MovieFavoriteListTable WHERE isfavorite = :isfavorite ORDER BY popularity")
    public abstract LiveData<List<MoviesFavoriteDataEntity>> loadAllFromMoviesByFavoriteTable(boolean isfavorite);

    @Query("SELECT * FROM MovieFavoriteListTable WHERE movie_id=:movie_id")
    public abstract LiveData<MoviesFavoriteDataEntity> loadAllFromMoviesByFavoriteTableWithMovieId(Integer movie_id);

    @Transaction
    public void updateMoviesByPopularityTable(List<MoviesDataEntity> moviesByPopularityList) {
        deleteAllEntriesFromMoviesByPopularityTable();
        insertAllToMoviesByPopularityTable(moviesByPopularityList);
    }

    @Insert
    public abstract void insertAllToMoviesByPopularityTable(List<MoviesDataEntity> tasks);

    @Query("DELETE FROM MoviesByPopularityTable")
    public abstract void  deleteAllEntriesFromMoviesByPopularityTable();

    @Transaction
    public void updateMoviesByTopRatedTable(List<MoviesByTopRatedEntity> moviesByTopRatedList) {
        if(moviesByTopRatedList.size()>0) {
            deleteAllEntriesFromMoviesByTopRatedTable();
            insertAllToMoviesByTopRatedTable(moviesByTopRatedList);
        }
    }

    @Insert
    public abstract void insertAllToMoviesByTopRatedTable(List<MoviesByTopRatedEntity> tasks);

    @Query("DELETE FROM MoviesByTopRatedTable")
    public abstract void  deleteAllEntriesFromMoviesByTopRatedTable();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertMoviesByPopularityTable(MovieReviewList movieReviewList);

    @Query("SELECT * FROM MovieReviewListTable WHERE movie_id=:movie_id")
    public abstract LiveData<List<MovieReviewList>> loadAllMovieReviewListTable(int movie_id);

    @Transaction
    public void updateMoviesReviewTableforMovieId(List<MovieReviewList> movieReviewsForMovieId) {
        if(movieReviewsForMovieId.size()>0){
            deleteAllEntriesFromMoviesReviewTableforMovieId(movieReviewsForMovieId.get(0).getMovie_id());
            insertAllToMoviesReviewTableforMovieId(movieReviewsForMovieId);
        }
    }

    @Query("DELETE FROM MovieReviewListTable WHERE movie_id=:movie_id")
    public abstract void  deleteAllEntriesFromMoviesReviewTableforMovieId(int movie_id);

    @Insert
    public abstract void insertAllToMoviesReviewTableforMovieId(List<MovieReviewList> tasks);

    //loadAllMovieTrailersListTable
    @Query("SELECT * FROM MoviesTrailersListTable WHERE movie_id=:movie_id")
    public abstract LiveData<List<MovieTrailersList>> loadAllMovieTrailersListTable(int movie_id);

    @Transaction
    public void updateMoviesTrailersListTableforMovieId(List<MovieTrailersList> movieTrailersForMovieId) {
        if(movieTrailersForMovieId.size()>0) {
            deleteAllEntriesFromMoviesTrailersListTable(movieTrailersForMovieId.get(0).getMovie_id());
            insertAllToMoviesTrailersListTableforMovieId(movieTrailersForMovieId);
        }
    }

    @Query("DELETE FROM MoviesTrailersListTable WHERE movie_id=:movie_id")
    public abstract void  deleteAllEntriesFromMoviesTrailersListTable(int movie_id);

    @Insert
    public abstract void insertAllToMoviesTrailersListTableforMovieId(List<MovieTrailersList> tasks);

    @Query("SELECT * FROM MovieFavoriteListTable WHERE movie_id=:movie_id")
    public abstract MoviesFavoriteDataEntity loadMoviePresentinMoviesByFavoriteTable(int movie_id);

    @Query("SELECT EXISTS (SELECT 1 FROM MovieFavoriteListTable WHERE movie_id=:id)")
    public abstract int isFavorite(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Long insertMovieInMoviesByFavoriteTable(MoviesFavoriteDataEntity tasks);

    @Query("DELETE FROM MovieFavoriteListTable WHERE movie_id=:movie_id")
    public abstract int  deleteMovieInMoviesByFavoriteTable(int movie_id);

    @Transaction
    public void updateMovieFavoriteListTable(List<MoviesFavoriteDataEntity> moviesFavoriteDataEntity) {
        if(moviesFavoriteDataEntity.size()>0) {
            deleteAllEntriesFromMovieFavoriteListTable();
            insertAllToMovieFavoriteListTable(moviesFavoriteDataEntity);
        }
    }
    @Query("DELETE FROM MovieFavoriteListTable")
    public abstract void  deleteAllEntriesFromMovieFavoriteListTable();

    @Insert
    public abstract void insertAllToMovieFavoriteListTable(List<MoviesFavoriteDataEntity> tasks);
}
