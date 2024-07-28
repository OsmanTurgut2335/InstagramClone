package com.example.instagramclone.fragments;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.instagramclone.Art.PostDetails;
import com.example.instagramclone.db.DBHandlerPost;
import com.example.instagramclone.LoginScreen;
import com.example.instagramclone.R;
import com.example.instagramclone.databinding.FragmentSecondBinding;

import java.util.ArrayList;


// TODO: 28 Tem 2024 implement a better menu
// TODO: 28 Tem 2024 when a recent created image is clicked it crashes.fix it 
public class SecondFragment extends Fragment {
    DBHandlerPost dbHandlerPost;
String artName;

ArrayList<PostDetails> imagearray ;
FragmentSecondBinding binding;
int imageX,imageY,uri;

    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHandlerPost = new DBHandlerPost(getContext());
        imagearray = new ArrayList<>();


Bundle bundle = getArguments();
artName = bundle.getString("artName");

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
        super.onViewCreated(view, savedInstanceState);

        // Your existing code
        imagearray = dbHandlerPost.readSpecificPost(artName);
        byte[] byteArray = imagearray.get(0).getImageBlob();

        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        binding.ImageView.setImageBitmap(bitmap);

        // Adjust image size programmatically
        ViewGroup.LayoutParams layoutParams = binding.ImageView.getLayoutParams();
        layoutParams.width = 400; // Set desired width in pixels
        layoutParams.height = 400; // Set desired height in pixels
        binding.ImageView.setLayoutParams(layoutParams);

        imageX = imagearray.get(0).getArtUrlx();
        imageY = imagearray.get(0).getArtUrly();

        binding.editTextArtName.setText("Art Name: " + imagearray.get(0).getArtName());
        binding.editTextArtistName.setText("Artist: " + imagearray.get(0).getArtistName());
        binding.editTextYear.setText("Art Year: " + imagearray.get(0).getArtYear());

        MenuProvider menuProvider = new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.clear();
                menuInflater.inflate(R.menu.option_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.sign_out:
                        // TODO: 28 Tem 2024   implement navigation back to the login screen
                        Intent intent = new Intent(getActivity(), LoginScreen.class);
                        startActivity(intent);
                        getActivity().onBackPressed();

                        return true;
                    default:
                        return false;
                }
            }
        };

        MenuHost menuHost = requireActivity();
        menuHost.addMenuProvider(menuProvider);
    }


    public void submitButtonClicked(View view) {
        // burada sqlite a yazma işlemi yapıcaz




    }
}