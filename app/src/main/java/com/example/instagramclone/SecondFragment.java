package com.example.instagramclone;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.instagramclone.adapter.PostAdapto;
import com.example.instagramclone.databinding.ActivityMainBinding;
import com.example.instagramclone.databinding.FragmentFirstBinding;
import com.example.instagramclone.databinding.FragmentSecondBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

// burası post ekleme kısmı


public class SecondFragment extends Fragment {
    DBHandlerPost dbHandlerPost;
String artName;
String artConfig;
ArrayList<PostDetails> erduvan ;
FragmentSecondBinding binding;
int imageX,imageY,uri;

    public SecondFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHandlerPost = new DBHandlerPost(getContext());
        erduvan = new ArrayList<>();


Bundle bundle = getArguments();
artName = bundle.getString("artName");



        System.out.println("RECEP DAYYİP ERDOĞAN    "+artName);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        erduvan = dbHandlerPost.readSpecificPost(artName);
        byte[] byteArray = erduvan.get(0).getImageBlob();

Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray,0, byteArray.length);

binding.ImageView.setImageBitmap(bitmap);
        imageX = erduvan.get(0).getArtUrlx();
        imageY = erduvan.get(0).getArtUrly();



        binding.editTextArtName.setText( "Art Name: "+erduvan.get(0).getArtName());
        binding.editTextArtistName.setText("Artist : "+erduvan.get(0).getArtistName());
        binding.editTextYear.setText("Art Year: "+erduvan.get(0).getArtYear());


        //Snackbar.make(view, "HOŞGELDİN MY NAME İS ", BaseTransientBottomBar.LENGTH_LONG).show();
        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear();
                menuInflater.inflate(R.menu.option_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.add_post:
                        System.out.println("wqeqwe");
                        return true;
                    case R.id.sign_out:
                        System.out.println("qweeqw");
                        return true;
                    default: return false;

                }

            }
        };

        MenuHost menuHost= requireActivity();
        menuHost.addMenuProvider(menuProvider);

        super.onViewCreated(view, savedInstanceState);

    }

    public void submitButtonClicked(View view) {
        // burada sqlite a yazma işlemi yapıcaz




    }
}