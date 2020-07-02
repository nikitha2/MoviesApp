package com.nikitha.android.movies;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nikitha.android.movies.Retrofit.MovieReviewList;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdaptorReviewRecyclerView extends RecyclerView.Adapter {
    static ListItemClickListener mClickListener;
    List<MovieReviewList> movieReviewList;
    Context context;

    public AdaptorReviewRecyclerView(Context context, List<MovieReviewList> movieReviewList, ListItemClickListener mClickListener) {
        this.movieReviewList=movieReviewList;
        this.mClickListener=mClickListener;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        CustomViewHolder customViewHolder;

        View v1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_listitem, parent, false);
        customViewHolder=new CustomViewHolder(v1);
        return customViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        View currentView = holder.itemView;
        MovieReviewList currentItemAtPos = movieReviewList.get(position);

        TextView author=(TextView) currentView.findViewById(R.id.author);
        author.setText(currentItemAtPos.getAuthor());

        TextView content=(TextView) currentView.findViewById(R.id.content);
        content.setText(currentItemAtPos.getContent());
    }

    @Override
    public int getItemCount() {
        return movieReviewList.size();
    }

    interface ListItemClickListener{
        void onListItemClickReviews(int position);
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
            mClickListener.onListItemClickReviews(pos);
        }
    }
}
