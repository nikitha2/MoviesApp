package com.nikitha.android.movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.nikitha.android.movies.Retrofit.MoviesByPopularity;
import com.nikitha.android.movies.Room.AppDatabase;
import com.nikitha.android.movies.Room.MoviesByPopularityEntity;
import com.nikitha.android.movies.Room.MoviesByTopRatedEntity;
import com.nikitha.android.movies.arch.MainViewModelFactory;
import com.nikitha.android.movies.arch.ViewModelMain;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesByPopularityAdapter.ListItemClickListener {
    MoviesByPopularityAdapter adapter;
    List<MoviesByPopularityEntity> moviesByPopularity=new ArrayList<>();
    int numberOfColumns=2;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView=(RecyclerView)  findViewById(R.id.moviesList);

        adapter = new MoviesByPopularityAdapter(this,moviesByPopularity);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView.setAdapter(adapter);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        setupViewModel();
    }


    private void setupViewModel() {
        MainViewModelFactory factory = new MainViewModelFactory(this,"en");
        final ViewModelMain viewModel = ViewModelProviders.of(this, factory).get(ViewModelMain.class);

        viewModel.loadAllFromMoviesByPopularityTable().observe(this,new Observer<List<MoviesByPopularityEntity>>(){

            @Override
            public void onChanged(List<MoviesByPopularityEntity> moviesByPopularityEntities) {
                progressDialog.dismiss();
                generateDataList(moviesByPopularityEntities);
            }
        });

//        viewModel.getMoviesByPopularity().observe(this,  new Observer<List<MoviesByPopularityEntity>>(){
//            @Override
//            public void onChanged(List<MoviesByPopularityEntity> moviesByPopularity) {
//                if(moviesByPopularity.isEmpty()){
//                   // Toast.makeText(this, "DB for MoviesByPopularity count not be updated...Please try later!", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    viewModel.updateMoviesByPopularityTable(moviesByPopularity);
//                }
//            }
//        });
    }
    private void generateDataList( List<MoviesByPopularityEntity> moviesByPopularityEntities) {
        adapter.setData(moviesByPopularityEntities);
    }
//    private void generateDataList( List<MoviesByTopRatedEntity> moviesByPopularityEntities) {
//        adapter.setData(MoviesByTopRatedEntity);
//    }
    @Override
    public void onListItemClick(int position) {
        Intent intent=new Intent(this,DetailActivity.class);
        intent.putExtra("moviesByPopularity",  moviesByPopularity.get(position));
        startActivity(intent);
    }
}