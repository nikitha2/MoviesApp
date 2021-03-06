package com.nikitha.android.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.nikitha.android.movies.Room.MoviesByTopRatedEntity;
import com.nikitha.android.movies.Room.MoviesDataEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.nikitha.android.movies.Utils.NetworkUtils.buildImageURL;

public class MoviesByTopRatedAdapter extends RecyclerView.Adapter {
    List<MoviesByTopRatedEntity> moviesByTopRated;
    Context context;
    MoviesByPopularityAdapter.ListItemClickListener mClickListener;

    public MoviesByTopRatedAdapter(Context context, List<MoviesByTopRatedEntity> moviesByTopRated, MoviesByPopularityAdapter.ListItemClickListener mClickListener) {
        this.moviesByTopRated =moviesByTopRated;
        this.context=context;
        this.mClickListener=mClickListener;
    }

    interface ListItemClickListener{
        void onListItemClick(int position);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        View v;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.v=itemView;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos=getAdapterPosition();
            mClickListener.onListItemClick(pos);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.listitems_main, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        View currentView = holder.itemView;
        MoviesByTopRatedEntity currentItemAtPos = moviesByTopRated.get(position);

        ImageView imageView= currentView.findViewById(R.id.imageView);

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
         builder.build()
                .load(buildImageURL(currentItemAtPos.getPoster_path()))
                .placeholder((R.drawable.ic_launcher_foreground))
                .error(R.drawable.ic_launcher_background)
                 .fit()
                 .centerCrop()
                .into(imageView);
    }

    public void setData(List<MoviesByTopRatedEntity> data) {
        moviesByTopRated.clear();
        moviesByTopRated.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return moviesByTopRated.size();
    }


}
