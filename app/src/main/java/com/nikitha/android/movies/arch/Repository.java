package com.nikitha.android.movies.arch;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.nikitha.android.movies.Retrofit.MovieReviewList;
import com.nikitha.android.movies.Retrofit.MovieTrailersList;
import com.nikitha.android.movies.Room.AppDatabase;
import com.nikitha.android.movies.Room.MoviesDataEntity;
import com.nikitha.android.movies.Room.MoviesByTopRatedEntity;
import com.nikitha.android.movies.Room.MoviesFavoriteDataEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import static com.nikitha.android.movies.arch.Scheduler.LOG_TAG;
import static com.nikitha.android.movies.arch.UpdateBDs.getMovieReviewsForMovieId;
import static com.nikitha.android.movies.arch.UpdateBDs.getMovieTrailersForMovieId;
import static com.nikitha.android.movies.arch.UpdateBDs.getMoviesByPopularity;
import static com.nikitha.android.movies.arch.UpdateBDs.getMoviesByTopRating;

public class Repository {
    final String MOVIES_BY_TOP_RATED="moviesByTopRated";
    final String MOVIES_BY_POPULARITY="moviesByPopularity";

    Context context;
    String language;
    AppDatabase database;
    ExecutorService executor;
    Long id1=-1L;
    int id2=-1;
    public Repository(Context context,String language) {
        this.context=context;
        this.language=language;
        database = AppDatabase.getInstance(context.getApplicationContext());
        executor = MyApplication.getInstance().executorService;

    }
    public Repository(Context context) {
        this.context=context;
        database = AppDatabase.getInstance(context);
        executor = MyApplication.getInstance().executorService;

    }

    public LiveData<List<MoviesDataEntity>> loadAllFromMoviesByPopularityTable() {
        LiveData<List<MoviesDataEntity>> moviesByPopularity = database.moviesDao().loadAllFromMoviesByPopularityTable();
        return moviesByPopularity;
    }

    public LiveData<List<MoviesByTopRatedEntity>> loadAllFromMoviesByTopRatedTable() {
        LiveData<List<MoviesByTopRatedEntity>> moviesByTopRated = database.moviesDao().loadAllFromMoviesByTopRatedTable();
        return moviesByTopRated;
    }
    public LiveData<List<MoviesFavoriteDataEntity>> loadAllFromMoviesByFavoriteTable() {
        LiveData<List<MoviesFavoriteDataEntity>> moviesByTopRated = database.moviesDao().loadAllFromMoviesByFavoriteTable(true);
        return moviesByTopRated;
    }

    public void updateMoviesByTopRatedTable(final List<MoviesByTopRatedEntity> moviesByPopularity) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.moviesDao().updateMoviesByTopRatedTable(moviesByPopularity);
            }
        });
    }

    public void updateMoviesByPopularityTable(final List<MoviesDataEntity> moviesByPopularity) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.moviesDao().updateMoviesByPopularityTable(moviesByPopularity);
            }
        });
    }

    public void updateBDs(final Context context,String language) {
        final List<MoviesFavoriteDataEntity> moviesFavoriteDataEntity=new ArrayList<>();
        Log.i(LOG_TAG,"----------------------inside UpdateBDs class updating DBs");

        MutableLiveData<List<MoviesDataEntity>> moviesByPopularity= getMoviesByPopularity(language);

        moviesByPopularity.observe((LifecycleOwner) context,  new Observer<List<MoviesDataEntity>>(){
            @Override
            public void onChanged(List<MoviesDataEntity> moviesByPopularity) {
                if(moviesByPopularity.isEmpty()){
                    Toast.makeText(context, "DB for MoviesByPopularity count not be updated...Please try later!", Toast.LENGTH_SHORT).show();
                }
                else{
                    updateMoviesByPopularityTable(moviesByPopularity);
                   // moviesFavoriteDataEntity.addAll(addDataToFav(moviesByPopularity,MOVIES_BY_POPULARITY));
                }
            }
        });

        MutableLiveData<List<MoviesByTopRatedEntity>> moviesByTopRating= getMoviesByTopRating(language);

        moviesByTopRating.observe((LifecycleOwner) context,  new Observer<List<MoviesByTopRatedEntity>>(){
            @Override
            public void onChanged(List<MoviesByTopRatedEntity> moviesByTopRated) {
                if(moviesByTopRated.isEmpty()){
                    Toast.makeText(context, "DB for MoviesByTopRating count not be updated...Please try later!", Toast.LENGTH_SHORT).show();
                }
                else{
                    updateMoviesByTopRatedTable(moviesByTopRated);
                    //moviesFavoriteDataEntity.addAll(addDataToFav(moviesByTopRated,MOVIES_BY_TOP_RATED));
                }
            }
        });

       // updateMovieFavoriteListTable(moviesFavoriteDataEntity);
    }

/*    private List<MoviesFavoriteDataEntity> addDataToFav(Object movies, String movieFilter) {
        List<MoviesFavoriteDataEntity> moviesFavoriteDataEntity=new ArrayList<>();
        if(MOVIES_BY_POPULARITY.equals(movieFilter)){
            List<MoviesDataEntity> m1 = (List<MoviesDataEntity>)(movies);
            for (MoviesDataEntity m:m1){
                moviesFavoriteDataEntity.add(new MoviesFavoriteDataEntity(m.getPopularity(), m.getVote_count(), m.getPoster_path(), m.getId(), m.getBackdrop_path(), m.getOriginal_language(), m.getOriginal_title(), m.getTitle(), m.getVote_average(), m.getOverview(), m.getRelease_date(),false));
            }
        }
        else if(MOVIES_BY_TOP_RATED.equals(movieFilter)){
            List<MoviesByTopRatedEntity> m1 = (List<MoviesByTopRatedEntity>)(movies);
            for (MoviesByTopRatedEntity m:m1){
                moviesFavoriteDataEntity.add(new MoviesFavoriteDataEntity(m.getPopularity(), m.getVote_count(), m.getPoster_path(), m.getId(), m.getBackdrop_path(), m.getOriginal_language(), m.getOriginal_title(), m.getTitle(), m.getVote_average(), m.getOverview(), m.getRelease_date(),false));
            }
        }
        return moviesFavoriteDataEntity;
    }*/

    /*public void updateMovieFavoriteListTable(final List<MoviesFavoriteDataEntity> moviesFavoriteDataEntity) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.moviesDao().updateMovieFavoriteListTable(moviesFavoriteDataEntity);
            }
        });
    }*/
    public void updateDBsPeriodically(Context context) {
        Scheduler schedulejobs = new Scheduler();
        Scheduler.scheduleSyncServiceJob(context);
    }

    public void getMovieReviewsWithMovieIdAndUpdateDB(Integer id) {
        Log.i(LOG_TAG, "----------------------inside UpdateBDs class updating DBs");

        final MutableLiveData<List<MovieReviewList>> movieReviewsForMovieId = getMovieReviewsForMovieId(id);

        movieReviewsForMovieId.observe((LifecycleOwner) context, new Observer<List<MovieReviewList>>() {

            @Override
            public void onChanged(List<MovieReviewList> movieReviewList) {
                updateMovieReviewListTable(movieReviewList);
            }
        });
    }
    public void getMovieTrailersWithMovieIdAndUpdateDB(Integer id) {
        Log.i(LOG_TAG, "----------------------inside UpdateBDs class updating DBs");

        final MutableLiveData<List<MovieTrailersList>> movieTrailersForMovieId = getMovieTrailersForMovieId(id);

        movieTrailersForMovieId.observe((LifecycleOwner) context, new Observer<List<MovieTrailersList>>() {

            @Override
            public void onChanged(List<MovieTrailersList> movieTrailersLists) {
                updateMovieTrailersListTable(movieTrailersLists);
            }
        });
    }
    public void updateMovieReviewListTable(final List<MovieReviewList> movieReviewsForMovieId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.moviesDao().updateMoviesReviewTableforMovieId(movieReviewsForMovieId);
            }
        });
    }

    public void updateMovieTrailersListTable(final List<MovieTrailersList> movieTrailersLists) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                database.moviesDao().updateMoviesTrailersListTableforMovieId(movieTrailersLists);
            }
        });
    }
    public LiveData<List<MovieReviewList>> loadAllMovieReviewsWithMovieId(int id) {
        LiveData<List<MovieReviewList>> movieReviewsWithMovieId = database.moviesDao().loadAllMovieReviewListTable(id);
        return movieReviewsWithMovieId;
    }

    public LiveData<List<MovieTrailersList>> loadAllMovieTrailersWithMovieId(int id) {
        LiveData<List<MovieTrailersList>> movieReviewsWithMovieId = database.moviesDao().loadAllMovieTrailersListTable(id);
        return movieReviewsWithMovieId;
    }

    public LiveData<MoviesFavoriteDataEntity> getMoviePresentinMoviesByFavoriteTable( int id) {
        LiveData<MoviesFavoriteDataEntity> movieWithMovieId= database.moviesDao().loadAllFromMoviesByFavoriteTableWithMovieId(id);
        return movieWithMovieId;
    }
    public Long insertMovieInMoviesByFavoriteTable(final MoviesFavoriteDataEntity data) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                id1 =database.moviesDao().insertMovieInMoviesByFavoriteTable(data);
            }
        });
        return id1;
    }
    public int deleteMovieInMoviesByFavoriteTable(final int id) {
        executor.execute(new Runnable() {
                @Override
                public void run() {
                    id2= database.moviesDao().deleteMovieInMoviesByFavoriteTable(id);
                }
            });
        return id2;
    }

}
