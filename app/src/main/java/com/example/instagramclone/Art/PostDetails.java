package com.example.instagramclone.Art;

import java.sql.Blob;

public class PostDetails {

    private String username;
    private String artName;
    private String artistName;
    private String artYear;
   private String artConfig;
    private int uri ;
    private int artUrlx;
    private int artUrly;
    private Blob blob;
     private byte[] imageBlob;
    public PostDetails(String username,String artName, String artistName, String artYear, int artUrlx, int artUrly,byte[] imageBlob) {
        this.username = username;
        this.artName = artName;
        this.artistName = artistName;
        this.artYear = artYear;
        this.artUrlx = artUrlx;
        this.artUrly = artUrly;
        this.imageBlob = imageBlob;

    }

    public String getArtName() {
        return artName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getArtYear() {
        return artYear;
    }

    public int getArtUrlx() {
        return artUrlx;
    }

    public String getUsername() {
        return username;
    }

    public int getArtUrly() {
        return artUrly;
    }

    public String getArtConfig() {
        return artConfig;
    }

    public byte[] getImageBlob() {
        return imageBlob;
    }
}
