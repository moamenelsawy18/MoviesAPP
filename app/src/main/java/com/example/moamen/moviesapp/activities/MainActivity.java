package com.example.moamen.moviesapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.moamen.moviesapp.adapters.MovieAdapter;
import com.example.moamen.moviesapp.fragments.MoviesDetailsFragment;
import com.example.moamen.moviesapp.R;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ClickCallback {

    private boolean twoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        MoviesFragment MF = new MoviesFragment();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.ma , MF)
//                .commit();

        twoPane = findViewById(R.id.movie_detail_fragment) != null;

    }

    @Override
    public void onMovieCardClicked(long id , String title , String poster) {


        Bundle b = new Bundle();
        b.putLong("id", id);
        b.putString("title", title);
        b.putString("poster", poster);

        if (twoPane)
        {
            MoviesDetailsFragment DetailFragment = new MoviesDetailsFragment();
            DetailFragment.setArguments(b);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_fragment, DetailFragment)
                    .commit();
        }
        else {
            Intent i = new Intent(this, DetailActivity.class);
            i.putExtra("id", id);
            i.putExtra("title",title);
            i.putExtra("poster",poster);
            startActivity(i);
        }
    }
}
