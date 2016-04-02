package com.example.moamen.moviesapp.classes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moamen.moviesapp.adapters.MovieAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by MoaMeN on 3/24/2016.
 */
public class MovieParsing {

    RequestQueue requestQueue;
    ArrayList<MovieClass> movieClasses;
    Context context;
    RecyclerView recyclerView;


    public MovieParsing(Context context , RecyclerView recyclerView)
    {
        this.recyclerView = recyclerView;
        this.context =context;

        requestQueue = Volley.newRequestQueue(context);
    }

    public void SendjsonRequest(String mUrl)
    {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, mUrl, null, new Response.Listener<JSONObject>()
        {
            @Override
            public void onResponse(JSONObject jsonObject) {

                movieClasses = reponse(jsonObject);
                if(movieClasses!=null)
                {
                    recyclerView.setAdapter(new MovieAdapter(context , movieClasses));
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

    public ArrayList<MovieClass> reponse(JSONObject jsonObject) {


        ArrayList<MovieClass> mydata = new ArrayList<>();
        if (jsonObject == null || jsonObject.length() == 0)
        {
            return null;
        }
        else {
            try {
                JSONArray results = jsonObject.getJSONArray(KeysJason.results);
                for (int i = 0; i < results.length(); i++) {
                    JSONObject jsonObject1 = results.getJSONObject(i);
                    long id = jsonObject1.getLong(KeysJason.ID);
                    String title = jsonObject1.getString(KeysJason.MovieTitle);
                    String poster  = jsonObject1.getString(KeysJason.MoviePoster);
                    String panner = jsonObject1.getString(KeysJason.MoviePanner);
                    String ReleaseDate = jsonObject1.getString(KeysJason.MovieRelease_Date);
                    double vote_avg = jsonObject1.getDouble(KeysJason.MovieVote_average);
                    String overview = jsonObject1.getString(KeysJason.MovieMverview);

                    mydata.add(new MovieClass(id , panner, poster , overview , ReleaseDate , vote_avg ,title));
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
        return mydata;
    }

}
