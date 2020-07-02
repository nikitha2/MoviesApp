package com.nikitha.android.movies.arch;

import android.content.Context;

import com.nikitha.android.movies.Retrofit.GetMoviesByPopularityService;
import com.nikitha.android.movies.Retrofit.MovieReviewList;
import com.nikitha.android.movies.Retrofit.MovieTrailersList;
import com.nikitha.android.movies.Retrofit.MoviesByPopularityList;
import com.nikitha.android.movies.Retrofit.MoviesByTopRatedList;
import com.nikitha.android.movies.Retrofit.MoviesVideosList;
import com.nikitha.android.movies.Retrofit.RetrofitClientInstance;
import com.nikitha.android.movies.Room.AppDatabase;
import com.nikitha.android.movies.Room.MovieReviewDataEntity;
import com.nikitha.android.movies.Room.MovieReviewDataEntityList;
import com.nikitha.android.movies.Room.MovieTrailerDataEntity;
import com.nikitha.android.movies.Room.MovieTrailersDataEntityList;
import com.nikitha.android.movies.Room.MoviesDataEntity;
import com.nikitha.android.movies.Room.MoviesByTopRatedEntity;
import com.nikitha.android.movies.Room.VideosDataEntity;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateBDs {
    private static String LOG_TAG=UpdateBDs.class.getSimpleName();
    private static AppDatabase database;

   public static MutableLiveData<List<MoviesDataEntity>> getMoviesByPopularity(String language) {

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        GetMoviesByPopularityService service= retrofit.create(GetMoviesByPopularityService.class);
        Call<MoviesByPopularityList> call = service.getAllMoviesByPopularity(language,"841d0fa80309aa3e96d864930905571d");

        final MutableLiveData<List<MoviesDataEntity>> moviesByPopularity = new MutableLiveData<>();
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

    public static MutableLiveData<List<MoviesByTopRatedEntity>> getMoviesByTopRating(String language) {
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

    public static void executeTask(Context appContext) {
    }

    public static MutableLiveData<List<MovieReviewList>> getMovieReviewsForMovieId(Integer id) {

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        GetMoviesByPopularityService service= retrofit.create(GetMoviesByPopularityService.class);
        Call<MovieReviewDataEntityList> call = service.getMovieReviewsForMovieId(id,"841d0fa80309aa3e96d864930905571d");

        final MutableLiveData<List<MovieReviewList>> movieReview = new MutableLiveData<>();
        call.enqueue(new Callback<MovieReviewDataEntityList>() {
            @Override
            public void onResponse(Call<MovieReviewDataEntityList> call, Response<MovieReviewDataEntityList> response) {
                List<MovieReviewList> listOf_movieReviewLists= new ArrayList<>();
                MovieReviewDataEntityList body = response.body();
                int movie_id= body.getMovie_id();
                List<MovieReviewDataEntity> result = body.getResults();
                for(int i=0;i<result.size();i++){
                    String author = result.get(i).getAuthor();
                    String content =result.get(i).getContent();
                    String id =result.get(i).getId();
                    String url =result.get(i).getUrl();
                    listOf_movieReviewLists.add(new MovieReviewList(movie_id, id, author, content,  url));
                }

                movieReview.setValue(listOf_movieReviewLists);
            }

            @Override
            public void onFailure(Call<MovieReviewDataEntityList> call, Throwable t) {

            }
        });
        return  movieReview;
    }

    public static MutableLiveData<List<MovieTrailersList>> getMovieTrailersForMovieId(Integer id) {

        Retrofit retrofit = RetrofitClientInstance.getRetrofitInstance();
        GetMoviesByPopularityService service= retrofit.create(GetMoviesByPopularityService.class);
        Call<MovieTrailersDataEntityList> call = service.getMovieTrailersForMovieId(id,"841d0fa80309aa3e96d864930905571d");

        final MutableLiveData<List<MovieTrailersList>> movieTrailers = new MutableLiveData<>();
        call.enqueue(new Callback<MovieTrailersDataEntityList>() {
            @Override
            public void onResponse(Call<MovieTrailersDataEntityList> call, Response<MovieTrailersDataEntityList> response) {
                List<MovieTrailersList> listOf_movieTrailersList= new ArrayList<>();
                MovieTrailersDataEntityList body = response.body();
                int movie_id= body.getMovie_id();
                List<MovieTrailerDataEntity> result = body.getResults();
                for(int i=0;i<result.size();i++){
                    String id=result.get(i).getId();;
                    String iso_639_1=result.get(i).getIso_639_1();
                    String iso_3166_1=result.get(i).getIso_3166_1();
                    String key=result.get(i).getKey();
                    String  name=result.get(i).getName();
                    String site=result.get(i).getSite();
                    String size=result.get(i).getSize();
                    String type=result.get(i).getType();
                    listOf_movieTrailersList.add(new MovieTrailersList(movie_id,id, iso_639_1, iso_3166_1, key, name, site, size, type));
                }

                movieTrailers.setValue(listOf_movieTrailersList);
            }

            @Override
            public void onFailure(Call<MovieTrailersDataEntityList> call, Throwable t) {

            }
        });
        return  movieTrailers;
    }
}
