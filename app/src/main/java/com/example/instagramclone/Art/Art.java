package com.example.instagramclone.Art;

public class Art {
   public String artName;
   private String password;

    public Art(String artName,String password) {

        this.artName = artName;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getArtName() {
        return artName;
    }



}
