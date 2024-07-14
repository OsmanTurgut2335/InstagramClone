package com.example.instagramclone;


import static android.content.Intent.getIntent;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.adapter.PostAdapto;
import com.example.instagramclone.databinding.FragmentFirstBinding;
import com.example.instagramclone.databinding.RecyclerRowBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.auth.User;

import java.io.Serializable;
import java.util.ArrayList;


public class FirstFragment extends Fragment implements Serializable {
    public interface onSomeEventListener {
        public void someEvent(String s);
    }
MainActivity mainActivity ;

    TextView textView ;
    /*
    final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ArrayList<PostDetails> arrayList;



        }
    };
*/


    private FragmentFirstBinding binding;
    String artName;
    String username;
    int controlValue;
ViewGroup parent ;
    ArrayList<Art> amcık;
    ArrayList<String> artList;
    PostAdapto postAdapto;
    Context context;
    View view;
    private ArrayList <String>  postArrayList;
    private DBHandlerPost dbHandlerPost;
   ArrayList<PostDetails> emptyPostList;

    public FirstFragment() {
        // Required empty public constructor
    }





    @Override
    public void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);


        artList = new ArrayList<>();
        amcık = new ArrayList<>();

  emptyPostList = new ArrayList<>();
dbHandlerPost = new DBHandlerPost(getContext());


        view = getActivity().findViewById(android.R.id.content);


        System.out.println(getContext());


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

        //System.out.println(" İNŞ OLMUŞTUR USERNAME  "+ username);
        artList = new ArrayList<>();
        amcık = new ArrayList<>();


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
     //   SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        //  int yarrak = sharedPref.getInt("value", 0);

     emptyPostList =   dbHandlerPost.readUserPosts(username);
           String usernamee =    emptyPostList.get(0).getUsername();

if(  ( usernamee.matches("empty")   )){
               // Snackbar.make(view, "ANAN DERİM DUAM BELLİ DUYAN BELLİ", BaseTransientBottomBar.LENGTH_LONG).show();
}
        else   {

            for (int i = 0; i < dbHandlerPost.readUserPosts(username).size(); i++) {
                artList.add(dbHandlerPost.readUserPosts(username).get(i).getArtName());

            }

        }



    }

}