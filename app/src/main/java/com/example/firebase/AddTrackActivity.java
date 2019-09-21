package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddTrackActivity extends AppCompatActivity {

    TextView textViewArtistName;
    TextView editTextTrackName;
    SeekBar seekbar;

    Button buttonAdtrack;

    ListView lTrack;
    List<Track> tracks;
    DatabaseReference databaseTracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

        textViewArtistName = findViewById(R.id.tvArtistsName);
        editTextTrackName = findViewById(R.id.editTextTrackName);
        buttonAdtrack = findViewById(R.id.buttonAddTrack);

        seekbar = findViewById(R.id.Seekbar);
        lTrack = findViewById(R.id.listViewTrack);

        Intent intent = getIntent();
        tracks  = new ArrayList<>();

        String id = intent.getStringExtra(MainActivity.ARTIST_ID);
        String name = intent.getStringExtra(MainActivity.ARTIST_NAME);

        textViewArtistName.setText(name);

        databaseTracks = FirebaseDatabase.getInstance().getReference("tracks").child(id);

        buttonAdtrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTrack();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseTracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tracks.clear();

                for (DataSnapshot trackSnapshot : dataSnapshot.getChildren()){
                    Track track = trackSnapshot.getValue(Track.class);

                    tracks.add(track);
                }
                TrackList trackListAdapter = new TrackList(AddTrackActivity.this,tracks);
                lTrack.setAdapter(trackListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public  void saveTrack(){

        String trackName = editTextTrackName.getText().toString().trim();
        int Rating = seekbar.getProgress();

        if (!TextUtils.isEmpty(trackName)){
            String id = databaseTracks.push().getKey();
            Track track = new Track(id,trackName,Rating);

            databaseTracks.child(id).setValue(track);

            Toast.makeText(this,"Track Succesfully Aded1",Toast.LENGTH_LONG).show();

        }else {
            Toast.makeText(this,"Track Name Should'nt Be Empty",Toast.LENGTH_LONG).show();
        }

    }
}
