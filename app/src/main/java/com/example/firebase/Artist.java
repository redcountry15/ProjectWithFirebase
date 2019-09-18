package com.example.firebase;

public class Artist {
    String artistId;
    String Name;
    String Genre;


    public Artist(){}

    public  Artist(String artistId,String Name,String Genre){
        this.artistId = artistId;
        this.Name = Name;
        this.Genre =Genre;
    }

    public String getArtistId() {
        return artistId;
    }

    public String getName() {
        return Name;
    }

    public String getGenre() {
        return Genre;
    }


    public void setArtistId(String artistId) {
        this.artistId = artistId;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setGenre(String genre) {
        Genre = genre;
    }
}
