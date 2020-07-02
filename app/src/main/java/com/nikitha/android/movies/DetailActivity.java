package com.nikitha.android.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.nikitha.android.movies.Retrofit.MovieReviewList;
import com.nikitha.android.movies.Retrofit.MovieTrailersList;
import com.nikitha.android.movies.Room.MoviesDataEntity;
import com.nikitha.android.movies.Room.MoviesByTopRatedEntity;
import com.nikitha.android.movies.Room.MoviesFavoriteDataEntity;
import com.nikitha.android.movies.arch.DetailViewModel;
import com.nikitha.android.movies.arch.DetailViewModelFactory;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.nikitha.android.movies.Utils.NetworkUtils.buildImageURL;

public class DetailActivity extends AppCompatActivity implements AdaptorReviewRecyclerView.ListItemClickListener,AdaptorTrailersRecyclerView.ListItemClickListener {
    final String LOG_TAG=DetailActivity.class.getSimpleName();
    Intent intent;
    LiveData<List<MovieReviewList>> movieReviewsWithMovieId;
    LiveData<List<MovieTrailersList>> movieTrailersWithMovieId;
    Boolean isfavorite=false;

    LinearLayoutManager layoutManagerRecyclerView = new LinearLayoutManager(this);
    LinearLayoutManager layoutManagerRecyclerView1 = new LinearLayoutManager(this);
    ImageView favoritesymbol;
    List<MovieReviewList> movieReviewListInfo;
    List<MovieTrailersList> movieTrailersListInfo;
    MoviesFavoriteDataEntity finalDataFav;
    Button favButton;
    int movieid=0;
    DetailViewModel viewModel;
    LiveData<MoviesFavoriteDataEntity> moviePresentinFavMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        viewModel = setupViewModel();
        intent = getIntent();
        String filter = intent.getStringExtra("filter");
        favButton= (Button) findViewById(R.id.markAsFavorite);

        if (filter.equals(getResources().getString(R.string.mostPopular))) {
            MoviesDataEntity data = (MoviesDataEntity) intent.getSerializableExtra("valueAtPosition");
            getSupportActionBar().setTitle(data.getTitle());
            populateImage(data.getPoster_path());
            //populateTitle(data.getTitle());
            populateOverview(data.getOverview());
            populateUserrating(data.getVote_average());
            populateReleasedate(data.getRelease_date().split("-")[0]);
            movieid = data.getId();
            populateReviews(viewModel, movieid);
            populateTrailer(viewModel, movieid);

            isfavorite=populateFavoritesymbol(viewModel,movieid);
            finalDataFav=new MoviesFavoriteDataEntity(data.getPopularity(),data.getVote_count(), data.getPoster_path(),movieid, data.getBackdrop_path(), data.getOriginal_language(), data.getOriginal_title(), data.getTitle(), data.getVote_average(), data.getOverview(), data.getRelease_date(),isfavorite);
       } else if (filter.equals(getResources().getString(R.string.top_rated))) {
            MoviesByTopRatedEntity data = (MoviesByTopRatedEntity) intent.getSerializableExtra("valueAtPosition");
            getSupportActionBar().setTitle(data.getTitle());
            movieid = data.getId();

            populateImage(data.getPoster_path());
            //populateTitle(data.getTitle());
            populateOverview(data.getOverview());
            populateUserrating(data.getVote_average());
            populateReleasedate(data.getRelease_date().split("-")[0]);
            viewModel.getMovieReviewsWithMovieIdAndUpdateDB(movieid);
            populateReviews(viewModel, movieid);
            populateTrailer(viewModel, movieid);

            isfavorite=populateFavoritesymbol(viewModel,movieid);
            finalDataFav=new MoviesFavoriteDataEntity(data.getPopularity(),data.getVote_count(), data.getPoster_path(),movieid, data.getBackdrop_path(), data.getOriginal_language(), data.getOriginal_title(), data.getTitle(), data.getVote_average(), data.getOverview(), data.getRelease_date(),isfavorite);
        }
        else if (filter.equals(getResources().getString(R.string.fav))) {
            MoviesFavoriteDataEntity data = (MoviesFavoriteDataEntity) intent.getSerializableExtra("valueAtPosition");
            finalDataFav=data;
            getSupportActionBar().setTitle(data.getTitle());
            movieid = data.getMovie_id();

            populateImage(data.getPoster_path());
            //populateTitle(data.getTitle());
            populateOverview(data.getOverview());
            populateUserrating(data.getVote_average());
            populateReleasedate(data.getRelease_date().split("-")[0]);
            viewModel.getMovieReviewsWithMovieIdAndUpdateDB(movieid);
            populateReviews(viewModel, movieid);
            populateTrailer(viewModel, movieid);

            isfavorite=populateFavoritesymbol(viewModel,movieid);
            finalDataFav=new MoviesFavoriteDataEntity(data.getPopularity(),data.getVote_count(), data.getPoster_path(),movieid, data.getBackdrop_path(), data.getOriginal_language(), data.getOriginal_title(), data.getTitle(), data.getVote_average(), data.getOverview(), data.getRelease_date(),isfavorite);
        }
        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(isfavorite){
                   int d= viewModel.deleteMovieInMoviesByFavoriteTable(movieid);
                   while(d == -1){
                       d= viewModel.deleteMovieInMoviesByFavoriteTable(movieid);
                   }
                   Log.i(LOG_TAG,"-----------------d---------------"+String.valueOf(d));
               }
               else{
                   finalDataFav.setIsfavorite(true);
                   Long d1= viewModel.insertMovieInMoviesByFavoriteTable(finalDataFav);
                   /*while(d1 == -1){
                       d1= viewModel.insertMovieInMoviesByFavoriteTable(finalDataFav);
                   }*/
                   Log.i(LOG_TAG,"------------------d1--------------"+d1.toString());
               }
            }
        });
    }

    Boolean populateFavoritesymbol(DetailViewModel viewModel,int movieid){
        favoritesymbol  = findViewById(R.id.favoritesymbol);
        LiveData<MoviesFavoriteDataEntity> moviePresentInFavMovies = viewModel.getMoviePresentinMoviesByFavoriteTable(movieid);
//        if(moviePresentInFavMovies !=null){
        moviePresentInFavMovies.observe(this, new Observer<MoviesFavoriteDataEntity>() {
                @Override
                public void onChanged(MoviesFavoriteDataEntity moviesFavoriteDataEntities) {
                    if(moviesFavoriteDataEntities!=null){
                        isfavorite= moviesFavoriteDataEntities.getIsfavorite(); // should be true if it is present in fav table
                    }
                    else{
                        isfavorite=false;
                    }
                    fillFavShape();
                }
            });
 //       }
        fillFavShape();
        return isfavorite;
    }

    private void fillFavShape() {
        if(isfavorite){
            favoritesymbol.setImageResource(R.mipmap.favorite);
        }
        else {
            favoritesymbol.setImageResource(R.mipmap.nonfavorite);
        }
    }

    private void populateTrailer(DetailViewModel viewModel, int movieid) {
        movieTrailersWithMovieId = viewModel.getMovieTrailersWithMovieId(movieid);
        movieTrailersWithMovieId.observe(this, new Observer<List<MovieTrailersList>>() {
            @Override
            public void onChanged(List<MovieTrailersList> movieTrailersList) {
                System.out.println("getMovieTrailersWithMovieId table changed");
                movieTrailersListInfo = movieTrailersList;
                populateTrailersList(movieTrailersList);
            }
        });

        if (movieReviewsWithMovieId.getValue() == null) {
            viewModel.getMovieTrailersWithMovieIdAndUpdateDB(movieid);
        }
    }

    private void populateReviews(DetailViewModel viewModel, int movieid) {
        movieReviewsWithMovieId = viewModel.getMovieReviewsWithMovieId(movieid);
        movieReviewsWithMovieId.observe(this, new Observer<List<MovieReviewList>>() {
            @Override
            public void onChanged(List<MovieReviewList> movieReviewList) {
                System.out.println("movieReviewsWithMovieId table changed");
                movieReviewListInfo = movieReviewList;
                populateReviewList(movieReviewList);
            }
        });

        if (movieReviewsWithMovieId.getValue() == null) {
            viewModel.getMovieReviewsWithMovieIdAndUpdateDB(movieid);
        }
    }

    private void populateReleasedate(String releasedate) {
        TextView textViewReleasedate = findViewById(R.id.releasedate);
        textViewReleasedate.setText(releasedate);
    }

    private void populateOverview(String overview) {
        TextView textViewOverview = findViewById(R.id.overview);
        textViewOverview.setText(overview);
    }

    private void populateUserrating(Double userrating) {
        TextView textViewUserrating = findViewById(R.id.userrating);
        textViewUserrating.setText(userrating.toString());
    }

    private void populateTitle(String title) {
        TextView textViewTitle = findViewById(R.id.title);
        textViewTitle.setText(title);
    }

    private void populateImage(String poster_path) {
        ImageView imageView = findViewById(R.id.imageView1);
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this));
        builder.build()
                .load(buildImageURL(poster_path))
                .placeholder((R.drawable.ic_launcher_foreground))
                .error(R.drawable.ic_launcher_background)
                .into(imageView);
    }

    private DetailViewModel setupViewModel() {
        DetailViewModelFactory factory = new DetailViewModelFactory(this);
        final DetailViewModel viewModel = ViewModelProviders.of(this, factory).get(DetailViewModel.class);
        return viewModel;
    }

    private void populateReviewList(List<MovieReviewList> movieReviewList) {
        RecyclerView reviewRecyclerView = findViewById(R.id.reviews);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(reviewRecyclerView.getContext(),DividerItemDecoration.VERTICAL);
        reviewRecyclerView.addItemDecoration(dividerItemDecoration);

        AdaptorReviewRecyclerView adaptorReviewRecyclerView=new AdaptorReviewRecyclerView(this,movieReviewList,this);
        reviewRecyclerView.setAdapter(adaptorReviewRecyclerView);
        reviewRecyclerView.setLayoutManager(layoutManagerRecyclerView);

    }

    private void populateTrailersList(List<MovieTrailersList> movieTrailersLists) {
        RecyclerView trailersRecyclerView = findViewById(R.id.trailers);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(trailersRecyclerView.getContext(),DividerItemDecoration.VERTICAL);
        trailersRecyclerView.addItemDecoration(dividerItemDecoration);

        AdaptorTrailersRecyclerView adaptorTrailersRecyclerView=new AdaptorTrailersRecyclerView(this,movieTrailersLists,this);
        trailersRecyclerView.setAdapter(adaptorTrailersRecyclerView);
        trailersRecyclerView.setLayoutManager(layoutManagerRecyclerView1);

    }

    @Override
    public void onListItemClickReviews(int position) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(movieReviewListInfo.get(position).getUrl()));
        startActivity(i);
    }

    @Override
    public void onListItemClickTrailers(int position) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + movieTrailersListInfo.get(position).getKey()));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/watch?v=" + movieTrailersListInfo.get(position).getKey()));
        try {
            startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}