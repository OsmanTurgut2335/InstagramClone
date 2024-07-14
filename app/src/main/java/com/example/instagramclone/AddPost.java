package com.example.instagramclone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.instagramclone.databinding.ActivityAddPostBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AddPost extends AppCompatActivity {
DBHandlerPost dbHandlerPost;
private ActivityAddPostBinding binding;
    public static final int PICK_IMAGE = 1;
String artName,artistName,artYear;
    String username;
   String config ;
int x ,y,uri;
byte[] decavüz ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostBinding.inflate(getLayoutInflater());
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        System.out.println("KARDEŞİM ŞİMDİ BURAYI DENİYORUZ  "  + username);

        View view = binding.getRoot();
        setContentView(view);
        dbHandlerPost = new DBHandlerPost(AddPost.this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.add_post:
                Snackbar.make(findViewById(android.R.id.content).getRootView(),"ANAN DERİM DUAM BELLİ DUYAN BELLİ", BaseTransientBottomBar.LENGTH_LONG).show();


                return true;
            case R.id.sign_out:
                Intent myIntent = new Intent(this, LoginScreen.class);
                startActivity(myIntent);
                finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void imageClicked(View view){


           Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
           intent.setType("image/*");
          startActivityForResult(intent,PICK_IMAGE);

    }


    public void submitButtonClicked(View view){
        artName = binding.editTextArtName.getText().toString();
        artistName = binding.editTextArtistName.getText().toString();
        artYear = binding.editTextYear.getText().toString();

        System.out.println("art name şu  "+ artName);
        System.out.println("artist name şu   "  +artistName);
        System.out.println("artyear şu    "+  artYear);


        System.out.println("bitmap x i şu  "  +x);

        System.out.println("bitmap y si şu   "  +y);

        dbHandlerPost.addNewPost(username,artName,artistName,artYear,x,y,decavüz);

        SharedPreferences sharedPref = AddPost.this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("value", 1);
        editor.apply();

        //shared preferences



      Intent intent = new Intent(AddPost.this,MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        // set Fragmentclass Arguments
        FirstFragment fragobj = new FirstFragment();
        fragobj.setArguments(bundle);
        intent.putExtras(bundle);
      startActivity(intent);
      finish();

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

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                  ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                  bitmap.compress(Bitmap.CompressFormat.PNG,0,outputStream);

                decavüz = outputStream.toByteArray();

                binding.recyclerViewImageView.setImageBitmap(bitmap);
                x = bitmap.getWidth();
                y = bitmap.getHeight();



            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}