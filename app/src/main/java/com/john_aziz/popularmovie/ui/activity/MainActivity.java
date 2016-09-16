package com.john_aziz.popularmovie.ui.activity;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

import com.john_aziz.popularmovie.BuildConfig;
import com.john_aziz.popularmovie.data.model.Movie;
import com.john_aziz.popularmovie.ui.adapter.MovieAdapter;
import com.john_aziz.popularmovie.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MovieAdapter mMovieAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        GridView movieView = (GridView)findViewById(R.id.movie_gridView);
        mMovieAdapter = new MovieAdapter(this,new ArrayList<Movie>());
        movieView.setAdapter(mMovieAdapter);
        new MovieAsyncTask().execute();
    }

    private class MovieAsyncTask extends AsyncTask<Void,Void,List<Movie>>{

        @Override
        protected List<Movie> doInBackground(Void... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            List<Movie> moviesList = null;
            try {
                Uri.Builder uriBuilder = new Uri.Builder();
                uriBuilder.encodedPath("https://api.themoviedb.org/3/discover/movie?api_key=1253f09932a079addc9c30ed6b560cde&sort_by=popularity.desc");
                URL url = new URL(uriBuilder.build().toString());
                connection = (HttpURLConnection) url.openConnection();
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";
                while((line = reader.readLine())!=null)
                    stringBuilder.append(line);
                moviesList = parseMovies(stringBuilder.toString());
            }catch(Exception e){
                Log.e(BuildConfig.LOG_TAG,e.getMessage());
            }
            return moviesList;
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            mMovieAdapter.clear();
            if(movies!=null)
                mMovieAdapter.addAll(movies);
        }
    }

    private List<Movie> parseMovies(String JSONResponse){
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
