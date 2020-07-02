package com.nikitha.android.movies;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nikitha.android.movies.Retrofit.MovieReviewList;
import com.nikitha.android.movies.Retrofit.MovieTrailersList;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptorTrailersRecyclerView extends RecyclerView.Adapter {
    static ListItemClickListener mClickListener;
    List<MovieTrailersList> movieTrailersLists;
    Context context;

    public AdaptorTrailersRecyclerView(Context context, List<MovieTrailersList> movieTrailersLists, ListItemClickListener mClickListener) {
        this.movieTrailersLists=movieTrailersLists;
        this.mClickListener=mClickListener;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        CustomViewHolder customViewHolder;

        View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_listitem, parent, false);
        customViewHolder=new CustomViewHolder(v1);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        View currentView = holder.itemView;
        MovieTrailersList currentItemAtPos = movieTrailersLists.get(position);

        ImageView play=(ImageView) currentView.findViewById(R.id.play);
        play.setImageResource(R.mipmap.playbutton);

        TextView name=(TextView) currentView.findViewById(R.id.name);
        //String[] nameText = currentItemAtPos.getName().split("-");

        name.setText(R.string.trailerVideo+" "+position);
    }

    @Override
    public int getItemCount() {
        return movieTrailersLists.size();
    }

    interface ListItemClickListener{
        void onListItemClickTrailers(int position);
    }
    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View v;
        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.v=itemView;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int pos=getAdapterPosition();
            mClickListener.onListItemClickTrailers(pos);
        }
    }
}
