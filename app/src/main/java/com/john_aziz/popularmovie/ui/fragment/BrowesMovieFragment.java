package com.john_aziz.popularmovie.ui.fragment;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.john_aziz.popularmovie.R;
import com.john_aziz.popularmovie.data.loader.MovieLoader;
import com.john_aziz.popularmovie.data.model.Movie;
import com.john_aziz.popularmovie.ui.adapter.MovieAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BrowesMovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Movie>>{

    private static final int MOVIE_LOADER_ID = 1;
    private MovieAdapter mMovieAdapter;
    public BrowesMovieFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_browes_movie, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.movie_gridView);
        mMovieAdapter = new MovieAdapter(getActivity(),new ArrayList<Movie>());
        if(isConnected())
            getLoaderManager().initLoader(MOVIE_LOADER_ID,null,this);
        else{
            // handle
        }

        return view;
    }


    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected())
            return true;
        return false;
    }
    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        return new MovieLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        mMovieAdapter.clear();
        if(data!=null)
            mMovieAdapter.addAll(data);

    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {
        mMovieAdapter.clear();
    }
}
