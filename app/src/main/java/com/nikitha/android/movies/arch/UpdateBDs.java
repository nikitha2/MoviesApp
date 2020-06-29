package com.nikitha.android.movies.arch;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.nikitha.android.movies.MainActivity;
import com.nikitha.android.movies.Retrofit.GetMoviesByPopularityService;
import com.nikitha.android.movies.Retrofit.MoviesByPopularity;
import com.nikitha.android.movies.Retrofit.MoviesByPopularityList;
import com.nikitha.android.movies.Retrofit.MoviesByTopRated;
import com.nikitha.android.movies.Retrofit.MoviesByTopRatedList;
import com.nikitha.android.movies.Retrofit.RetrofitClientInstance;
import com.nikitha.android.movies.Room.AppDatabase;
import com.nikitha.android.movies.Room.MoviesByPopularityEntity;
import com.nikitha.android.movies.Room.MoviesByTopRatedEntity;

import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateBDs {
    private static String LOG_TAG=UpdateBDs.class.getSimpleName();
    private static String language="en";
    private static AppDatabase database;

    public static void executeTask(final Context appContext) {
        Log.i(LOG_TAG,"----------------------inside UpdateBDs class updating DBs");
        final Repository repository = new Repository(appContext,language);

        database = AppDatabase.getInstance(appContext.getApplicationContext());
        MutableLiveData<List<MoviesByPopularityEntity>> moviesByPopularity= getMoviesByPopularity();

        moviesByPopularity.observe((LifecycleOwner) appContext,  new Observer<List<MoviesByPopularityEntity>>(){
            @Override
            public void onChanged(List<MoviesByPopularityEntity> moviesByPopularity) {
                if(moviesByPopularity.isEmpty()){
                    Toast.makeText(appContext, "DB for MoviesByPopularity count not be updated...Please try later!", Toast.LENGTH_SHORT).show();
                }
                else{
                  //  database.moviesDao().updateMoviesByPopularityTable(moviesByPopularity);
                    repository.updateMoviesByPopularityTable(moviesByPopularity);
                }
            }
        });

        MutableLiveData<List<MoviesByTopRatedEntity>> moviesByTopRating= getMoviesByTopRating();

        moviesByTopRating.observe((LifecycleOwner) appContext,  new Observer<List<MoviesByTopRatedEntity>>(){
            @Override
            public void onChanged(List<MoviesByTopRatedEntity> moviesByTopRated) {
                if(moviesByTopRated.isEmpty()){
                    Toast.makeText(appContext, "DB for MoviesByTopRating count not be updated...Please try later!", Toast.LENGTH_SHORT).show();
                }
                else{
                    //database.moviesDao().updateMoviesByTopRatedTable(moviesByTopRated);
                    repository.updateMoviesByTopRatedTable(moviesByTopRated);
                }
            }
        });
    }

    public static MutableLiveData<List<MoviesByPopularityEntity>> getMoviesByPopularity() {

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        GetMoviesByPopularityService service= retrofit.create(GetMoviesByPopularityService.class);
        Call<MoviesByPopularityList> call = service.getAllMoviesByPopularity(language,"841d0fa80309aa3e96d864930905571d");

        final MutableLiveData<List<MoviesByPopularityEntity>> moviesByPopularity = new MutableLiveData<>();
        call.enqueue(new Callback<MoviesByPopularityList>() {
            @Override
            public void onResponse(Call<MoviesByPopularityList> call, Response<MoviesByPopularityList> response) {
                MoviesByPopularityList body = response.body();
                moviesByPopularity.setValue(body.getResults());
            }

            @Override
            public void onFailure(Call<MoviesByPopularityList> call, Throwable t) {
                System.out.println("onFailure");
                moviesByPopularity.setValue(null);
            }
        });
        return  moviesByPopularity;
    }

    public static MutableLiveData<List<MoviesByTopRatedEntity>> getMoviesByTopRating() {
        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        GetMoviesByPopularityService service= retrofit.create(GetMoviesByPopularityService.class);
        Call<MoviesByTopRatedList> call = service.getAllMoviesByTopRated(language,"841d0fa80309aa3e96d864930905571d");

        final MutableLiveData<List<MoviesByTopRatedEntity>> moviesByTopRated = new MutableLiveData<>();
        call.enqueue(new Callback<MoviesByTopRatedList>() {
            @Override
            public void onResponse(Call<MoviesByTopRatedList> call, Response<MoviesByTopRatedList> response) {
                MoviesByTopRatedList body = response.body();
                moviesByTopRated.setValue(body.getResults());
            }

            @Override
            public void onFailure(Call<MoviesByTopRatedList> call, Throwable t) {
                System.out.println("onFailure");
                moviesByTopRated.setValue(null);
            }
        });
        return moviesByTopRated;
    }
}
