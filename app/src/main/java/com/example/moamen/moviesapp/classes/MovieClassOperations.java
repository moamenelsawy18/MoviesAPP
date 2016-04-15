package com.example.moamen.moviesapp.classes;

import android.content.Context;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by MoaMeN on 3/31/2016.
 */
public class MovieClassOperations {

    Realm realm;
    RealmResults<MovieClass> result;
    Context context;

    public MovieClassOperations(Context context){
        realm= Realm.getInstance(context);
        this.context = context;
    }

    public void insert(Long ID , String Title , String Poster)
    {
                realm.beginTransaction();
                MovieClass data = new MovieClass(ID , Poster , Title );
                realm.copyToRealm(data);
                realm.commitTransaction();
        }


    public ArrayList<MovieClass> retrive()
    {
        if(realm==null)
        {
            realm = Realm.getInstance(context);
        }
        ArrayList<MovieClass> mydat = new ArrayList<>();

        result = realm.where(MovieClass.class).findAll();
        for (int i = 0; i<result.size() ; i++){

            mydat.add(result.get(i));
        }
        return mydat;
    }

    public ArrayList<MovieClass> retriveByID(long id)
    {
        if(realm==null)
        {
            realm = Realm.getInstance(context);
        }
        ArrayList<MovieClass> mydat = new ArrayList<>();

        result = realm.where(MovieClass.class).equalTo("id", id).findAll();
        for (int i = 0; i<result.size() ; i++)
        {
            mydat.add(result.get(i));
        }
        return mydat;
    }
}
