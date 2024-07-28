package com.example.instagramclone.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.instagramclone.AddPost;
import com.example.instagramclone.Art.PostDetails;
import com.example.instagramclone.db.DBHandlerPost;
import com.example.instagramclone.LoginScreen;
import com.example.instagramclone.MainActivity;
import com.example.instagramclone.R;
import com.example.instagramclone.adapter.PostAdapto;
import com.example.instagramclone.databinding.FragmentFirstBinding;
import java.io.Serializable;
import java.util.ArrayList;



public class FirstFragment extends Fragment implements Serializable {

    MainActivity mainActivity ;

    private FragmentFirstBinding binding;

    String username;
    int controlValue;


    ArrayList<String> artList;
    PostAdapto postAdapto;
    Context context;
    View view;

    private DBHandlerPost dbHandlerPost;
   ArrayList<PostDetails> emptyPostList;

    public FirstFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        artList = new ArrayList<>();
        emptyPostList = new ArrayList<>();
        dbHandlerPost = new DBHandlerPost(getContext());
        view = getActivity().findViewById(android.R.id.content);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

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

                        Intent i = new Intent(getActivity(), AddPost.class);
                         i.putExtra("username",username);
                        startActivity(i);
                        ((Activity) getActivity()).overridePendingTransition(0, 0);


                        getActivity().onBackPressed();
                        return true;
                    case R.id.sign_out:
                        Intent intent = new Intent(getActivity(), LoginScreen.class);
                        startActivity(intent);
                        getActivity().onBackPressed();
                       // fragmentTransaction.remove(firstFragment).commit();
                        return true;
                    default: return false;

                }

            }
        };

        MenuHost menuHost= requireActivity();
       menuHost.addMenuProvider(menuProvider);

        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle extras = getActivity().getIntent().getExtras();

         username = extras.getString("username");
controlValue = extras.getInt("value");

      //  username = getArguments().getString("username");
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        artList = new ArrayList<>();
        postAdapto = new PostAdapto(artList,getContext(),FirstFragment.this);
        binding.recylerView.setAdapter(postAdapto);

        binding.recylerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recylerView.setBackgroundColor(getResources().getColor(R.color.white));

       getData();

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Activity){
            mainActivity =(MainActivity) context;
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void getData() {
     emptyPostList =   dbHandlerPost.readUserPosts(username);
           String usernamee =    emptyPostList.get(0).getUsername();

if(  ( usernamee.matches("empty")   )){

}
        else   {

            for (int i = 0; i < dbHandlerPost.readUserPosts(username).size(); i++) {
                artList.add(dbHandlerPost.readUserPosts(username).get(i).getArtName());

            }

        }



    }

}