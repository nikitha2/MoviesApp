package com.nikitha.android.movies.arch;

import android.content.Context;

import androidx.lifecycle.ViewModelProvider;

// COMPLETED (1) Make this class extend ViewModel ViewModelProvider.NewInstanceFactory
public class MainViewModelFactory extends ViewModelProvider.NewInstanceFactory {
        Context context;
        String language;
        public MainViewModelFactory(Context context,String language) {
           this.context=context;
           this.language=language;
        }

        @Override
        public <T extends androidx.lifecycle.ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ViewModelMain(context,language);
        }
}

