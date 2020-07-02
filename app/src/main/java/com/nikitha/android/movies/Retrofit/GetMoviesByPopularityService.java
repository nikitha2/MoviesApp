package com.nikitha.android.movies.Retrofit;

import com.nikitha.android.movies.Room.MovieReviewDataEntityList;
import com.nikitha.android.movies.Room.MovieTrailersDataEntityList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetMoviesByPopularityService {

//    https://api.themoviedb.org/3/movie/popular?api_key=841d0fa80309aa3e96d864930905571d
   // @Headers({"cache-control:public, max-age=21600","content-type:content-type"})
    //@Query("api_key","841d0fa80309aa3e96d864930905571d")
    @GET("/3/movie/popular")
    Call<MoviesByPopularityList> getAllMoviesByPopularity(@Query("language") String language, @Query("api_key") String api_key);

//    https://api.themoviedb.org/3/movie/top_rated?api_key=841d0fa80309aa3e96d864930905571d
    @GET("/3/movie/top_rated")
    Call<MoviesByTopRatedList> getAllMoviesByTopRated(@Query("language") String language, @Query("api_key") String api_key);

    @GET("/3/movie/{id}/videos")
    Call<MovieTrailersDataEntityList> getMovieTrailersForMovieId(@Path("id") Integer id, @Query("api_key") String api_key);

    @GET("/3/movie/{id}/reviews")
    Call<MovieReviewDataEntityList> getMovieReviewsForMovieId(@Path("id") Integer id, @Query("api_key") String api_key);
}
