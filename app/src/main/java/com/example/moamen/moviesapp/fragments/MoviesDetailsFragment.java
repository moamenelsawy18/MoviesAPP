package com.example.moamen.moviesapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moamen.moviesapp.activities.TrailerPlay;
import com.example.moamen.moviesapp.classes.KeysJason;
import com.example.moamen.moviesapp.classes.MovieClass;
import com.example.moamen.moviesapp.classes.MovieClassOperations;
import com.example.moamen.moviesapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MoviesDetailsFragment extends Fragment {

    RequestQueue requestQueue;
    Context context;
    MovieClass movieClass ;
    long ID;
    String title;
    String poster1;
    ImageView poster;
    ImageView panner;
    ImageView fav;
    TextView mtitle;
    TextView relasedate;
    TextView vote_avg;
    TextView overview;
    MovieClassOperations movieClassOperations ;
    LinearLayout trailerLayout;
    LinearLayout reviewLayout;

    Bundle b = null;
    public MoviesDetailsFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestQueue = Volley.newRequestQueue(getActivity());

        b= getArguments();
        ID = b.getLong("id");
        title = b.getString("title");
        poster1 = b.getString("poster");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_movies_details, container, false);

//        ID = getActivity().getIntent().getExtras().getLong("id");
//        title = getActivity().getIntent().getExtras().getString("title");
//        poster1 = getActivity().getIntent().getExtras().getString("poster");


        poster = (ImageView) v.findViewById(R.id.poster_path);
        panner = (ImageView) v.findViewById(R.id.backdrop_path);
        fav = (ImageView) v.findViewById(R.id.fav);
        mtitle = (TextView) v.findViewById(R.id.movie_tiltle);
        relasedate = (TextView) v.findViewById(R.id.release_date);
        overview = (TextView) v.findViewById(R.id.overview);
        vote_avg = (TextView) v.findViewById(R.id.vote_average);

        trailerLayout = (LinearLayout) v.findViewById(R.id.tlayout);
        reviewLayout = (LinearLayout) v.findViewById(R.id.rlayout);

        movieClassOperations = new MovieClassOperations(getActivity());

        final ArrayList<MovieClass> movie;
        movie = movieClassOperations.retriveByID(ID);

        for (int i =0 ; i<movie.size() ; i++)
        {
            if (ID == movie.get(i).getId())
            {
                fav.setImageResource(android.R.drawable.star_big_on);
            }
        }

        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fav.setImageResource(android.R.drawable.star_big_on);
                movieClassOperations.insert(ID , title , poster1);
            }
        });
        return v;
    }

    public void MovieDetailsRequest(String mUrl)
    {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, mUrl, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject jsonObject) {

              MovieClass movieClass = MovieDetailsResponse(jsonObject);
                    Picasso.with(getActivity())
                            .load(Uri.parse("http://image.tmdb.org/t/p/w342" + movieClass.getPoster()))
                            .into(poster);
                    Picasso.with(getActivity())
                            .load(Uri.parse("http://image.tmdb.org/t/p/w342" + movieClass.getPanner()))
                            .into(panner);
                mtitle.setText(movieClass.getTitle());
                relasedate.setText(movieClass.getRelase_Date());
                overview.setText(movieClass.getOverview());
                vote_avg.setText(movieClass.getVote_Avg() + "");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }

        }
        );

        requestQueue.add(request);
    }

    public  MovieClass MovieDetailsResponse(JSONObject jsonObject) {


        if (jsonObject == null || jsonObject.length() == 0)
        {
            return null;
        }
        else {
            try {
                    long id = jsonObject.getLong(KeysJason.ID);
                    String title = jsonObject.getString(KeysJason.MovieTitle);
                    String poster  = jsonObject.getString(KeysJason.MoviePoster);
                    String panner = jsonObject.getString(KeysJason.MoviePanner);
                    String ReleaseDate = jsonObject.getString(KeysJason.MovieRelease_Date);
                    double vote_avg = jsonObject.getDouble(KeysJason.MovieVote_average);
                    String overview = jsonObject.getString(KeysJason.MovieMverview);

                MovieClass movie = new MovieClass(id , panner, poster , overview , ReleaseDate , vote_avg ,title);
                return movie;
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
        return null;
    }


    public void TrailerRequest(String mUrl)
    {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, mUrl, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject jsonObject) {

                if (jsonObject == null || jsonObject.length() == 0)
                {
                    return ;
                }
                else {
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            final String key = jsonObject1.getString("key");
                            String name = jsonObject1.getString("name");

                            View v = LayoutInflater.from(getActivity()).inflate(R.layout.trailer, null);

                            TextView tname = (TextView) v.findViewById(R.id.trailer_name);
                            ImageView play = (ImageView) v.findViewById(R.id.trailer_button);

                            tname.setText(name);
                            play.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(getActivity() , TrailerPlay.class);
                                    i.putExtra("key" , key);
                                    startActivity(i);
                                }
                            });

                            trailerLayout.addView(v);

                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }

        }
        );

        requestQueue.add(request);
    }



    public void ReviewRequest(String mUrl)
    {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, mUrl, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject jsonObject) {

                if (jsonObject == null || jsonObject.length() == 0)
                {
                    return ;
                }
                else {
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            String auth = jsonObject1.getString("author");
                            String con = jsonObject1.getString("content");

                            View v = LayoutInflater.from(getActivity()).inflate(R.layout.review, null);

                            TextView author = (TextView) v.findViewById(R.id.review_author);
                            TextView content = (TextView) v.findViewById(R.id.review_content);

                            author.setText(auth);
                            content.setText(con);

                            reviewLayout.addView(v);

                        }
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
            }

        }
        );

        requestQueue.add(request);
    }


    public void onResume()
    {
        super.onResume();
        if (b!=null)
        {
            MovieDetailsRequest("http://api.themoviedb.org/3/movie/" + ID + "?api_key=6291b787b5285071e3150d7fa51ccf45");
            TrailerRequest("http://api.themoviedb.org/3/movie/" + ID + "/videos?api_key=6291b787b5285071e3150d7fa51ccf45");
            ReviewRequest("http://api.themoviedb.org/3/movie/" + ID + "/reviews?api_key=6291b787b5285071e3150d7fa51ccf45");
        }

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }
    @Override
    public void onDetach() {
        super.onDetach();
    }


}
