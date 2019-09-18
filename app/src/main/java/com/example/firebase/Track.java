package com.example.firebase;

public class Track {
    private  String TrackID;
    private String trackName;
    private  int TrackRating;

    public Track(){

    }
    public  Track(String Trackid,String Name,int Rating){
        this.TrackID=Trackid;
        this.trackName =Name;
        this.TrackRating=Rating;
    }

    public String getTrackID() {
        return TrackID;
    }

    public void setTrackID(String trackID) {
        TrackID = trackID;
    }

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public int getTrackRating() {
        return TrackRating;
    }

    public void setTrackRating(int trackRating) {
        TrackRating = trackRating;
    }
}
