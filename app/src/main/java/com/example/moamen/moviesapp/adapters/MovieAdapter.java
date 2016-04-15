package com.example.moamen.moviesapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.moamen.moviesapp.classes.MovieClass;
import com.example.moamen.moviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by MoaMeN on 3/24/2016.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.viewHolder> {

    public LayoutInflater layoutInflater;
    ArrayList<MovieClass> arrayList;
    Click click;
    Context context;


    public MovieAdapter(Context context , ArrayList<MovieClass> arrayList){
        this.arrayList = arrayList;
        layoutInflater= LayoutInflater.from(context);
        this.context = context;
        click = (Click) context;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewHolder(layoutInflater.inflate(R.layout.movies_item , parent , false));
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        Picasso.with(context)
                .load(Uri.parse("http://image.tmdb.org/t/p/w342" + arrayList.get(position).getPoster()))
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;

        public viewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.movie_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    click.onClick(arrayList.get(getPosition()).getId(),
                            arrayList.get(getPosition()).getTitle(),arrayList.get(getPosition()).getPoster());


//                    Intent i = new Intent(context , DetailActivity.class );
//                    i.putExtra("id",arrayList.get(getPosition()).getId());
//                    i.putExtra("title",arrayList.get(getPosition()).getTitle());
//                    i.putExtra("poster",arrayList.get(getPosition()).getPoster());
//
//                    context.startActivity(i);

                }
            });
        }
    }

    public interface Click {

        void onClick(long id , String title , String poster);


    }
}
