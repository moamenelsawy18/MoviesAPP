package com.example.moamen.moviesapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.moamen.moviesapp.R;
import com.example.moamen.moviesapp.fragments.MoviesDetailsFragment;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//        MoviesDetailsFragment DF = new MoviesDetailsFragment();
//        getSupportFragmentManager()
//                .beginTransaction()
//                .replace(R.id.movie_detail_fragment , DF)
//                .commit();

        Bundle b = new Bundle();
        b.putLong("id" , getIntent().getExtras().getLong("id"));
        b.putString("title" , getIntent().getExtras().getString("title"));
        b.putString("poster" , getIntent().getExtras().getString("poster"));
            MoviesDetailsFragment details = new MoviesDetailsFragment();
            details.setArguments(b);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_fragment, details)
                    .commit();

    }
}
