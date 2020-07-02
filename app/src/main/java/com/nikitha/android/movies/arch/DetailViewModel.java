package com.nikitha.android.movies.arch;

import android.content.Context;

import com.nikitha.android.movies.Retrofit.MovieReviewList;
import com.nikitha.android.movies.Retrofit.MovieTrailersList;
import com.nikitha.android.movies.Room.MoviesByTopRatedEntity;
import com.nikitha.android.movies.Room.MoviesDataEntity;
import com.nikitha.android.movies.Room.MoviesFavoriteDataEntity;

import java.util.List;

import androidx.lifecycle.LiveData;

public class DetailViewModel extends androidx.lifecycle.ViewModel {
    LiveData<List<MoviesDataEntity>> moviesByPopularity;
    LiveData<List<MoviesByTopRatedEntity>> moviesByTopRated;
    Repository repository;

    LiveData<List<MovieReviewList>> movieReviewsWithMovieId;
    LiveData<List<MovieTrailersList>> movieTrailersWithMovieId;
    LiveData<MoviesFavoriteDataEntity> movieWithMovieId;

    public DetailViewModel(Context context) {
        repository = new Repository(context);
    }

    public void getMovieReviewsWithMovieIdAndUpdateDB(Integer id) {
        repository.getMovieReviewsWithMovieIdAndUpdateDB(id);
    }
    public void getMovieTrailersWithMovieIdAndUpdateDB(Integer id) {
        repository.getMovieTrailersWithMovieIdAndUpdateDB(id);
    }

    public LiveData<List<MovieReviewList>> getMovieReviewsWithMovieId(Integer movie_id) {
        movieReviewsWithMovieId = repository.loadAllMovieReviewsWithMovieId(movie_id);
        return movieReviewsWithMovieId;
    }

    public LiveData<List<MovieTrailersList>> getMovieTrailersWithMovieId(Integer movie_id) {
        movieTrailersWithMovieId = repository.loadAllMovieTrailersWithMovieId(movie_id);
        return movieTrailersWithMovieId;
    }

    public LiveData<MoviesFavoriteDataEntity> getMoviePresentinMoviesByFavoriteTable(Integer movie_id) {
        movieWithMovieId = repository.getMoviePresentinMoviesByFavoriteTable(movie_id);
        return movieWithMovieId;
    }

    public Long insertMovieInMoviesByFavoriteTable(MoviesFavoriteDataEntity data ) {
        return repository.insertMovieInMoviesByFavoriteTable(data);
    }

    public int deleteMovieInMoviesByFavoriteTable(Integer movie_id) {
        return repository.deleteMovieInMoviesByFavoriteTable(movie_id);
    }

    public LiveData<List<MoviesFavoriteDataEntity>> loadAllFromMoviesByFavoriteTable(){
        return repository.loadAllFromMoviesByFavoriteTable();
    }
    
}
