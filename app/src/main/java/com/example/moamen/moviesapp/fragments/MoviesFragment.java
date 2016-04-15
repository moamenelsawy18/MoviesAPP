package com.example.moamen.moviesapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.moamen.moviesapp.classes.KeysJason;
import com.example.moamen.moviesapp.adapters.MovieAdapter;
import com.example.moamen.moviesapp.classes.MovieClass;
import com.example.moamen.moviesapp.classes.MovieClassOperations;
import com.example.moamen.moviesapp.classes.MovieParsing;
import com.example.moamen.moviesapp.R;

import java.util.ArrayList;


public class MoviesFragment extends Fragment {

    MovieParsing movieParsing  ;
    RecyclerView recyclerView;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ArrayList<MovieClass> movieClasses = new ArrayList<MovieClass>();
    MovieClassOperations movieClassOperations;

    public MoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_movies, container, false);

        movieClassOperations = new MovieClassOperations(getActivity());

        preferences = getActivity().getSharedPreferences("Show" , Context.MODE_PRIVATE);
        editor = preferences.edit();

        recyclerView = (RecyclerView) v.findViewById(R.id.movie_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));

        return v;
    }

     public void onResume() {
        super.onResume();

         movieParsing = new MovieParsing(getActivity() , recyclerView);
         int value = preferences.getInt("viewby",0);
         if (value ==0)
         {
             movieParsing.SendjsonRequest(KeysJason.PobularURL);
         }
         else if (value == 1)
         {
             movieParsing.SendjsonRequest(KeysJason.RatedURL);
         }
         else
         {

             movieClasses = movieClassOperations.retrive();

             recyclerView.setAdapter(new MovieAdapter(getActivity() , movieClasses));
         }
     }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.popular)
        {
            editor.putInt("viewby",0);

            movieParsing = new MovieParsing(getActivity() , recyclerView);
            movieParsing.SendjsonRequest(KeysJason.PobularURL);

        }
        else if(id == R.id.rated)
        {
            editor.putInt("viewby",1);

            movieParsing = new MovieParsing(getActivity() , recyclerView);
            movieParsing.SendjsonRequest(KeysJason.RatedURL);
        }

        else if(id == R.id.favo)
        {
            editor.putInt("viewby",2);

            movieClasses = movieClassOperations.retrive();
            recyclerView.setAdapter(new MovieAdapter(getActivity() , movieClasses));

        }

        editor.apply();
        return super.onOptionsItemSelected(item);

    }
}
