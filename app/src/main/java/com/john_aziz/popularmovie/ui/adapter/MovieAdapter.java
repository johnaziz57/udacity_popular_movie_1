package com.john_aziz.popularmovie.ui.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.john_aziz.popularmovie.R;
import com.john_aziz.popularmovie.data.model.Movie;

import java.util.Collection;
import java.util.List;

/**
 * Created by JAnis on 05-Sep-16.
 */
public class MovieAdapter extends BaseAdapter {
    private List<Movie> mMovieList;
    private Context mContext;
    public MovieAdapter(Context context, List<Movie> movieList) {
        mContext = context;
        mMovieList  =movieList;
    }

    @Override
    public int getCount() {
        return mMovieList==null?0:mMovieList.size();
    }

    @Override
    public Movie getItem(int position) {
        return mMovieList==null?null:mMovieList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private Context getContext(){
        return  mContext;
    }

    public void clear(){
        mMovieList.clear();
        notifyDataSetInvalidated();
    }

    public void addAll(Collection<Movie> movieList){
        mMovieList.addAll(movieList);
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        if(convertView==null)
            convertView = inflater.inflate(R.layout.grid_item_movie,parent,false);

        Movie movie = getItem(position);
        TextView movieTitle = (TextView)convertView.findViewById(R.id.movie_title_textView);
        ImageView moviePoster  = (ImageView) convertView.findViewById(R.id.movie_poster_imageView);

        GridView gridView = (GridView)parent;
        // set the placeholder
        moviePoster.setImageResource(R.drawable.placeholder);
        // set the image height to the aspect ratio of the poster so as not to resize when
        // the image is loaded
        moviePoster.setMinimumHeight(gridView.getColumnWidth()*3>>1);

        movieTitle.setText(position == 3 ? getContext().getString(R.string.long_string):movie.title);

        if(moviePoster!=null)
            Glide
                .with(getContext())
                .load(movie.posterURL)
                .placeholder(R.drawable.placeholder)
                .into(moviePoster);

        return convertView;
    }


}
