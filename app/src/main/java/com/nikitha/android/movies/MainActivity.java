package com.nikitha.android.movies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nikitha.android.movies.Room.MoviesDataEntity;
import com.nikitha.android.movies.Room.MoviesByTopRatedEntity;
import com.nikitha.android.movies.Room.MoviesFavoriteDataEntity;
import com.nikitha.android.movies.arch.MainViewModelFactory;
import com.nikitha.android.movies.arch.ViewModelMain;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import static com.nikitha.android.movies.Utils.Commons.calculateNoOfColumns;

public class MainActivity extends AppCompatActivity implements MoviesByPopularityAdapter.ListItemClickListener ,SharedPreferences.OnSharedPreferenceChangeListener, MoviesByFavoriteAdapter.ListItemClickListener {
    MoviesByPopularityAdapter moviesByPopularityAdapter;
    MoviesByTopRatedAdapter moviesByTopRatedAdapter;
    MoviesByFavoriteAdapter moviesByFavoriteAdapter;

    RecyclerView recyclerView;
    List<MoviesDataEntity> moviesByPopularity=new ArrayList<>();
    List<MoviesByTopRatedEntity> moviesByTopRated=new ArrayList<>();
    List<MoviesFavoriteDataEntity> moviesByFavorite=new ArrayList<>();
    TextView emptyData;
    static boolean onSharedPreferenceChanged =false;
    int numberOfColumns=2;
//    TextView sortedorder;
    ProgressDialog progressDialog;
    String language;String filters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=(RecyclerView)  findViewById(R.id.moviesList);
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
       // sortedorder= (TextView) findViewById(R.id.sort);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        setupViewModel();

//        Button filter=(Button) findViewById(R.id.filter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(onSharedPreferenceChanged){
            setupViewModel();
            onSharedPreferenceChanged =false;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        progressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    private void setupViewModel() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        language = sharedPreferences.getString(getString(R.string.settings_languageTitle), getResources().getString(R.string.english));
        filters = sharedPreferences.getString(getString(R.string.filters), getResources().getString(R.string.mostPopular));

        if(languageStringlengthGreaterThan2(language)){
            MainViewModelFactory factory = new MainViewModelFactory(this,language);
            final ViewModelMain viewModel = ViewModelProviders.of(this, factory).get(ViewModelMain.class);

            recyclerView.setLayoutManager(new GridLayoutManager(this, calculateNoOfColumns(this)));

            if(filters.equals(getResources().getString(R.string.mostPopular))){
             //   sortedorder.setText(getString(R.string.sort).concat(getString(R.string.mostPopular)));
                moviesByPopularityAdapter = new MoviesByPopularityAdapter(this,moviesByPopularity,this);
                recyclerView.setAdapter(moviesByPopularityAdapter);

                viewModel.loadAllFromMoviesByPopularityTable().observe(this,new Observer<List<MoviesDataEntity>>(){
                    @Override
                    public void onChanged(List<MoviesDataEntity> moviesByPopularityEntities) {
                        progressDialog.dismiss();
                        generateDataListForMoviesByPopularity(moviesByPopularityEntities);
                    }
                });

            }
            else if(filters.equals(getResources().getString(R.string.top_rated))){
               // sortedorder.setText(getString(R.string.sort).concat(getString(R.string.top_rated)));
                moviesByTopRatedAdapter = new MoviesByTopRatedAdapter(this,moviesByTopRated,this);
                recyclerView.setAdapter(moviesByTopRatedAdapter);
                LiveData<List<MoviesByTopRatedEntity>> movies = viewModel.loadAllFromMoviesByTopRatedTable();
                viewModel.loadAllFromMoviesByTopRatedTable().observe(this,new Observer<List<MoviesByTopRatedEntity>>(){
                    @Override
                    public void onChanged(List<MoviesByTopRatedEntity> moviesByTopRatedEntity) {
                        progressDialog.dismiss();
                        generateDataListForMoviesByTopRated(moviesByTopRatedEntity);
                    }
                });
            }
            else if(filters.equals(getResources().getString(R.string.fav))){
                moviesByFavoriteAdapter = new MoviesByFavoriteAdapter(this,moviesByFavorite,this);
                recyclerView.setAdapter(moviesByFavoriteAdapter);
                emptyData= findViewById(R.id.emptyData);
              
                viewModel.loadAllFromMoviesByFavoriteTable().observe(this,new Observer<List<MoviesFavoriteDataEntity>>(){
                    @Override
                    public void onChanged(List<MoviesFavoriteDataEntity> moviesFavoriteDataEntity) {
                        if(moviesFavoriteDataEntity==null){
                            emptyData.setVisibility(View.VISIBLE);
                        }
                        else{
                            emptyData.setVisibility(View.GONE);
                            progressDialog.dismiss();
                            generateDataListForMoviesByFavorite(moviesFavoriteDataEntity);
                        }
                    }
                });
            }
        }

    }

    private void generateDataListForMoviesByFavorite( List<MoviesFavoriteDataEntity> moviesFavoriteDataEntity) {
        moviesByFavorite=moviesFavoriteDataEntity;
        moviesByFavoriteAdapter.setData(moviesFavoriteDataEntity);
    }

    private boolean languageStringlengthGreaterThan2(String language) {
        if(language.length()<2){
            Toast error = Toast.makeText(this, "Please select a number between 0.1 and 3", Toast.LENGTH_SHORT);
            error.show();
            return false;
        }
        return true;
    }

    private void generateDataListForMoviesByPopularity( List<MoviesDataEntity> moviesByPopularityEntities) {
        moviesByPopularity=moviesByPopularityEntities;
        moviesByPopularityAdapter.setData(moviesByPopularityEntities);
    }
    private void generateDataListForMoviesByTopRated( List<MoviesByTopRatedEntity> moviesByTopRatedEntity) {
        moviesByTopRated=moviesByTopRatedEntity;
        moviesByTopRatedAdapter.setData(moviesByTopRatedEntity);
    }

    @Override
    public void onListItemClick(int position) {
        Intent intent=new Intent(this,DetailActivity.class);
        if(filters.equals(getResources().getString(R.string.mostPopular))){
            intent.putExtra("valueAtPosition",moviesByPopularity.get(position));
            intent.putExtra("filter",getResources().getString(R.string.mostPopular));
        }
        else if(filters.equals(getResources().getString(R.string.top_rated))){
            intent.putExtra("valueAtPosition",moviesByTopRated.get(position));
            intent.putExtra("filter",getResources().getString(R.string.top_rated));
        }
        else if(filters.equals(getResources().getString(R.string.fav))){
            intent.putExtra("valueAtPosition",moviesByFavorite.get(position));
            intent.putExtra("filter",getResources().getString(R.string.fav));
        }
        startActivity(intent);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        onSharedPreferenceChanged =true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.filter:
                Intent intent = new Intent(recyclerView.getContext(), SettingsActivity.class);
                startActivity(intent);
        }
        return true;
    }
}