package com.nikitha.android.movies.arch;

import android.content.Context;

import com.nikitha.android.movies.Retrofit.MoviesByPopularity;
import com.nikitha.android.movies.Retrofit.MoviesByPopularityList;
import com.nikitha.android.movies.Retrofit.MoviesByTopRated;
import com.nikitha.android.movies.Retrofit.MoviesByTopRatedList;
import com.nikitha.android.movies.Retrofit.GetMoviesByPopularityService;
import com.nikitha.android.movies.Retrofit.RetrofitClientInstance;
import com.nikitha.android.movies.Room.AppDatabase;
import com.nikitha.android.movies.Room.MoviesByPopularityEntity;
import com.nikitha.android.movies.Room.MoviesByTopRatedEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Repository {
    Context context;
    String language;
    AppDatabase database;
    public Repository(Context context,String language) {
        this.context=context;
        this.language=language;
        database = AppDatabase.getInstance(context.getApplicationContext());
    }

    public LiveData<List<MoviesByPopularityEntity>> loadAllFromMoviesByPopularityTable() {
        LiveData<List<MoviesByPopularityEntity>> moviesByPopularity = database.moviesDao().loadAllFromMoviesByPopularityTable();
        return moviesByPopularity;
    }

    public LiveData<List<MoviesByTopRatedEntity>> loadAllFromMoviesByTopRatedTable() {
        LiveData<List<MoviesByTopRatedEntity>> moviesByTopRated = database.moviesDao().loadAllFromMoviesByTopRatedTable();
        return moviesByTopRated;
    }

    public void updateDBsPeriodically(Context context) {
        Scheduler schedulejobs = new Scheduler();
        Scheduler.scheduleSyncServiceJob(context);
    }

    public void updateMoviesByTopRatedTable(List<MoviesByTopRatedEntity> moviesByPopularity) {
       database.moviesDao().updateMoviesByTopRatedTable(moviesByPopularity);
    }

    public void updateMoviesByPopularityTable(List<MoviesByPopularityEntity> moviesByPopularity) {
        database.moviesDao().updateMoviesByPopularityTable(moviesByPopularity);
    }
}
