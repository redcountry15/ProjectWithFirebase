package com.example.firebase;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class TrackList   extends ArrayAdapter<Track> {
    private Activity context;
    List<Track> tracks;

    public  TrackList(Activity context,List<Track> tracks){
        super(context,R.layout.track_list,tracks);
        this.context = context;
        this.tracks = tracks;

    }

    @NonNull

    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View ListItemView = inflater.inflate(R.layout.artist_list,null,true);

        TextView tvName = (TextView) ListItemView.findViewById(R.id.TVName);
        TextView tvRating = (TextView) ListItemView.findViewById(R.id.TVRating);

        Track track = tracks.get(position);

        tvName.setText(track.getTrackName());
        tvRating.setText(String.valueOf(track.getTrackRating()));

        return  ListItemView;

    }
}
