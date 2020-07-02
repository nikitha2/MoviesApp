package com.nikitha.android.movies.arch;

import android.content.Context;

import androidx.lifecycle.ViewModelProvider;

// COMPLETED (1) Make this class extend ViewModel ViewModelProvider.NewInstanceFactory
public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory {
        Context context;
        String language;
        public DetailViewModelFactory(Context context) {
           this.context=context;
        }

        @Override
        public <T extends androidx.lifecycle.ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new DetailViewModel(context);
        }
}

