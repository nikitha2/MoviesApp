package com.nikitha.android.movies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

import static com.nikitha.android.movies.Utils.NetworkUtils.buildImageURL;

public class DetailActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        intent = getIntent();
//        Serializable data = intent.getSerializableExtra("moviesByPopularity");

        ImageView imageView=findViewById(R.id.imageView1);
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this));
//        builder.build()
//                .load(buildImageURL(data.getPoster_path()))
//                .placeholder((R.drawable.ic_launcher_foreground))
//                .error(R.drawable.ic_launcher_background)
//                .into(imageView);
    }
}