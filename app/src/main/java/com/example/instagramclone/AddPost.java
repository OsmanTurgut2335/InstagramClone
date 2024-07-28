package com.example.instagramclone;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import com.example.instagramclone.db.DBHandlerPost;
import com.example.instagramclone.fragments.FirstFragment;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class AddPost extends AppCompatActivity {
    private DBHandlerPost dbHandlerPost;
    private ActivityAddPostBinding binding;
    public static final int PICK_IMAGE = 1;
    private String artName, artistName, artYear, username;
    private int width, height;
    private byte[] imageByteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddPostBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        if (username == null) {
            // Handle the case where username is not passed
            Snackbar.make(view, "Error: Username is missing", BaseTransientBottomBar.LENGTH_LONG).show();
            finish();
            return;
        }

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
        switch (item.getItemId()) {
            case R.id.add_post:
                Snackbar.make(findViewById(android.R.id.content).getRootView(), "Post is being added", BaseTransientBottomBar.LENGTH_LONG).show();
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

    public void imageClicked(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }

    public void submitButtonClicked(View view) {//adds a new post using dbhandler
        artName = binding.editTextArtName.getText().toString();
        artistName = binding.editTextArtistName.getText().toString();
        artYear = binding.editTextYear.getText().toString();

        dbHandlerPost.addNewPost(username, artName, artistName, artYear, width, height, imageByteArray);

        SharedPreferences sharedPref = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("value", 1);
        editor.apply();

        Intent intent = new Intent(AddPost.this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("username", username);
        FirstFragment fragobj = new FirstFragment();
        fragobj.setArguments(bundle);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            try {
                InputStream inputStream = getContentResolver().openInputStream(data.getData());
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);

                imageByteArray = outputStream.toByteArray();
                binding.recyclerViewImageView.setImageBitmap(bitmap);
                width = bitmap.getWidth();
                height = bitmap.getHeight();
            } catch (IOException e) {
                e.printStackTrace();
                Snackbar.make(findViewById(android.R.id.content).getRootView(), "Error processing image", BaseTransientBottomBar.LENGTH_LONG).show();
            }
        } else {
            Snackbar.make(findViewById(android.R.id.content).getRootView(), "Image selection cancelled", BaseTransientBottomBar.LENGTH_LONG).show();
        }
    }
}
