package com.example.instagramclone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.instagramclone.databinding.ActivityLoginScreenBinding;
import com.example.instagramclone.db.DBHandler;
import com.example.instagramclone.db.DBHandlerPost;
import com.example.instagramclone.fragments.FirstFragment;
import com.example.instagramclone.user.Username;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class LoginScreen extends AppCompatActivity implements Serializable {
ActivityLoginScreenBinding binding;
String name ,password ;

public static final int PICK_IMAGE = 1;
ArrayList<Username> artList;

int m = 0;

private DBHandler dbHandler;
private DBHandlerPost dbHandlerPost;

public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginScreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        dbHandler = new DBHandler(LoginScreen.this);
        dbHandlerPost = new DBHandlerPost(LoginScreen.this);
       //dbHandlerPost.deleteDatabase();  //use this if u wanna delete the contents of a table
        artList = new ArrayList<>();

        setContentView(view);



    }



    public void signUpButtonClicked(View view) {
        String name = binding.editTextTextPersonName.getText().toString();
        String password = binding.editTextTextPersonName2.getText().toString();

        if (name.isEmpty() || password.isEmpty()) {
            Snackbar.make(view, "Kullanıcı veya şifre alanı boş olamaz", BaseTransientBottomBar.LENGTH_LONG).show();
            return;
        }

        ArrayList<Username> users = dbHandlerPost.readUsers();

        for (Username user : users) {
            if (user.getUsername().equals(name)) {
                Snackbar.make(view, "Bu kullanıcı adı zaten kullanılıyor", BaseTransientBottomBar.LENGTH_LONG).show();
                return;
            }
        }

        dbHandlerPost.addNewUser(name, password);
        Snackbar.make(view, "Kullanıcı Kaydedildi", BaseTransientBottomBar.LENGTH_LONG).show();
    }





    public void loginButtonClicked(View view){

        Intent intent = new Intent(LoginScreen.this, MainActivity.class);

        if(!binding.editTextTextPersonName.getText().toString().matches("") && !binding.editTextTextPersonName2.getText().toString().matches("")) {

            name = binding.editTextTextPersonName.getText().toString();

            password = binding.editTextTextPersonName2.getText().toString();

        if(dbHandlerPost.readUsers().size() == 0){
            Snackbar.make(view,"Hiç kullanıcı yok", BaseTransientBottomBar.LENGTH_LONG).show();
        }

            for(int i = 0; i< dbHandlerPost.readUsers().size();i++){

                if( dbHandlerPost.readUsers().get(i).getUsername().matches(name)    && dbHandlerPost.readUsers().get(i).getPassword().matches(password) && m==0   ){
                    Bundle bundle = new Bundle();
                    bundle.putString("username", name);
                    bundle.putInt("value",0);
             // set Fragmentclass Arguments
                    m++;
                    FirstFragment fragobj = new FirstFragment();
                    fragobj.setArguments(bundle);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
              else if(dbHandlerPost.readUsers().get(i).getUsername().matches(name)    && dbHandlerPost.readUsers().get(i).getPassword().matches(password)){
                    Bundle bundle = new Bundle();
                    bundle.putString("username", name);
                    bundle.putInt("value",1);
                    // set Fragmentclass Arguments
                    FirstFragment fragobj = new FirstFragment();
                    fragobj.setArguments(bundle);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
                else{

                    Snackbar.make(view, "Kullanıcı adı veya şifre yanlış", BaseTransientBottomBar.LENGTH_LONG).show();
                }

            }


        }
        else  Snackbar.make(view,"Kullanıcı ve şifre alanı boş olamaz ", BaseTransientBottomBar.LENGTH_LONG).show();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
                //TODO: action

            try {
                @SuppressLint("Recycle") InputStream inputStream =  getApplicationContext().getContentResolver().openInputStream(data.getData());
                //Now you can do whatever you want with your inpustream, save it as file, upload to a server, decode a bitmap...

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }





}