package com.nikitha.android.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.nikitha.android.movies.Retrofit.MoviesByPopularity;
import com.nikitha.android.movies.Room.MoviesByPopularityEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.nikitha.android.movies.Utils.NetworkUtils.buildImageURL;

public class MoviesByPopularityAdapter extends RecyclerView.Adapter {
    List<MoviesByPopularityEntity> moviesByPopularity;
    Context context;
    static ListItemClickListener mClickListener;

    public MoviesByPopularityAdapter(Context context, List<MoviesByPopularityEntity> moviesByPopularity) {
        this.moviesByPopularity=moviesByPopularity;
        this.context=context;
    }

    interface ListItemClickListener{
        void onListItemClick(int position);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        View v;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.v=itemView;
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
        MoviesByPopularityEntity currentItemAtPos = moviesByPopularity.get(position);

        ImageView imageView= currentView.findViewById(R.id.imageView);

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
         builder.build()
                .load(buildImageURL(currentItemAtPos.getPoster_path()))
                .placeholder((R.drawable.ic_launcher_foreground))
                .error(R.drawable.ic_launcher_background)
                .into(imageView);
    }

    public void setData(List<MoviesByPopularityEntity> data) {
        moviesByPopularity.clear();
        moviesByPopularity.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return moviesByPopularity.size();
    }


}
