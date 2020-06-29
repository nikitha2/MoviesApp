package com.nikitha.android.movies.Utils;

import android.net.Uri;

public class NetworkUtils {
    private static final String MOVIE_IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE = "w185";

    public static String buildImageURL(String relativePath){
            return Uri.parse(MOVIE_IMAGE_BASE_URL).buildUpon()
                    .appendPath(IMAGE_SIZE)
                    .appendEncodedPath(relativePath)
                    .build()
                    .toString();
//        String finalURL = MOVIE_IMAGE_BASE_URL + IMAGE_SIZE + relativePath;
//        return finalURL;
    }
}
