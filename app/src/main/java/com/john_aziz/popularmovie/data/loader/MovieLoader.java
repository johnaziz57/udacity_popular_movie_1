package com.john_aziz.popularmovie.data.loader;


import android.content.Context;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.john_aziz.popularmovie.data.model.Movie;
import com.john_aziz.popularmovie.utils.Utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 12-Sep-16.
 */
public class MovieLoader extends AsyncTaskLoader<List<Movie>> {
    public MovieLoader(Context context) {
        super(context);
    }

    @Override
    public List<Movie> loadInBackground() {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        List<Movie> movieList = new ArrayList<Movie>();
        try{
            Uri.Builder builder = new Uri.Builder();
            builder.encodedPath("https://api.themoviedb.org/3/discover/movie?api_key=1253f09932a079addc9c30ed6b560cde&sort_by=popularity.desc");
            URL url = new URL(builder.build().toString());
            connection = (HttpURLConnection) url.openConnection();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = "";
            while((line = reader.readLine())!=null)
                stringBuilder.append(line);
            movieList = Utils.parseMovies(stringBuilder.toString());
        }catch(Exception e){
            Log.e("ERROR","ERROR");
        }
        return movieList;
    }
}
