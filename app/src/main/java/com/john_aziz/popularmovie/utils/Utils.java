package com.john_aziz.popularmovie.utils;

import android.util.Log;

import com.john_aziz.popularmovie.BuildConfig;
import com.john_aziz.popularmovie.data.model.Movie;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by John on 12-Sep-16.
 */
public class Utils {

    public static List<Movie> parseMovies(String JSONResponse){
        List<Movie> moviesList = new ArrayList<Movie>();
        try{
            JSONObject root = new JSONObject(JSONResponse);
            JSONArray results = root.getJSONArray("results");
            for(int i= 0;i<results.length();i++){
                JSONObject jsonMovie = (JSONObject)results.get(i);
                Movie movie = new Movie();

                movie.title = jsonMovie.getString("title");
                movie.rating = jsonMovie.getDouble("vote_average");
                movie.posterURL = "http://image.tmdb.org/t/p/w500"+jsonMovie.getString("poster_path");
                moviesList.add(movie);
            }

        }catch(Exception e){
            Log.e(BuildConfig.LOG_TAG,e.getMessage());
        }
        return moviesList;
    }
}
