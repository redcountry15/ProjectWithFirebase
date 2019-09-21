package com.example.firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String ARTIST_NAME ="artistname";
    public static final String ARTIST_ID ="artistid";

    EditText edit;
    Button button;
    Spinner spinnerGenre;
    DatabaseReference databaseArtist;
    ListView ListViewArtist;
    List<Artist> ArtistsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseArtist = FirebaseDatabase.getInstance().getReference("artists");

        edit = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        spinnerGenre = findViewById(R.id.spinner2);
        ArtistsList = new ArrayList<>();
        ListViewArtist = findViewById(R.id.listViewArtist);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addArtist();

            }
        });


        ListViewArtist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Artist artist = ArtistsList.get(i);

                Intent intent = new Intent(getApplicationContext(),AddTrackActivity.class);
                intent.putExtra(ARTIST_ID,artist.getArtistId());
                intent.putExtra(ARTIST_NAME,artist.getName());

                startActivity(intent);
            }
        });
       ListViewArtist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

               Artist artist = ArtistsList.get(i);

               showUpdateDialog(artist.getArtistId(),artist.getName());
               return false;
           }
       });
    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseArtist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArtistsList.clear();
                for (DataSnapshot artistSnapshot : dataSnapshot.getChildren()){
                    Artist artist = artistSnapshot.getValue(Artist.class);
                    ArtistsList.add(artist);
                }
                ArtistList adapter  = new ArtistList(MainActivity.this,ArtistsList);
                ListViewArtist.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDialog(final String artistId, final String artistName){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View dialogview = inflater.inflate(R.layout.update_dialog,null);

        dialogBuilder.setView(dialogview);

        final EditText editName = dialogview.findViewById(R.id.edtName);
        final Button btnUpdate = dialogview.findViewById(R.id.btnUpdate);
        final Spinner spnUpdate = dialogview.findViewById(R.id.edtSpinner);
        final Button btnDelete = dialogview.findViewById(R.id.btnDelete);


        dialogBuilder.setTitle("Updating Artist"+artistName);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String name = editName.getText().toString().trim();
               String genre = spnUpdate.getSelectedItem().toString();

               if (TextUtils.isEmpty(name)){
                   editName.setError("Name is Required");
                   return;
               }
               updateArtist(artistId,artistName,genre);
               alertDialog.dismiss();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteArtist(artistId);
            }
        });




    }

    private  void DeleteArtist(String artistId){
        DatabaseReference drArtist = FirebaseDatabase.getInstance().getReference("artists").child(artistId);
        DatabaseReference drTracks = FirebaseDatabase.getInstance().getReference("tracks").child(artistId);

        drArtist.removeValue();
        drTracks.removeValue();

        Toast.makeText(this,"Item Is Deleted",Toast.LENGTH_LONG).show();

    }

    private boolean updateArtist(String id,  String name , String genre){
        DatabaseReference databaseReference  = FirebaseDatabase.getInstance().getReference("artist").child(id);

        Artist artist = new Artist(id,name,genre);

        databaseReference.setValue(artist);
        Toast.makeText(this, "Updated Succesfully", Toast.LENGTH_SHORT).show();
        return  true;
    }

    private  void addArtist(){
        String name = edit.getText().toString().trim();
        String genre  = spinnerGenre.getSelectedItem().toString();

        if (!TextUtils.isEmpty(name)){
            String id =databaseArtist.push().getKey();

            Artist artist = new Artist(id,name,genre);

            databaseArtist.child(id).setValue(artist);

            Toast.makeText(this,"Artist Added",Toast.LENGTH_LONG).show();


        }else {
            Toast.makeText(this,"You Should Enter Your Name First!",Toast.LENGTH_LONG).show();
        }
    }
}
