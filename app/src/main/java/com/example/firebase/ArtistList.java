package com.example.firebase;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class ArtistList extends ArrayAdapter<Artist> {

    private Activity context;
    List<Artist> artists;

    public  ArtistList(Activity context,List<Artist> artists){
        super(context,R.layout.artist_list,artists);
        this.context = context;
        this.artists = artists;

    }



    @NonNull
    @Override
    public View getView(int position,  View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View ListItemView = inflater.inflate(R.layout.artist_list,null,true);

        TextView tvName = (TextView) ListItemView.findViewById(R.id.TVName);
        TextView tvGenere = (TextView) ListItemView.findViewById(R.id.TVGenre);

        Artist artist = artists.get(position);

        tvName.setText(artist.getName());
        tvGenere.setText(artist.getGenre());

        return  ListItemView;

    }
}
