package com.example.instagramclone.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.instagramclone.Art;
import com.example.instagramclone.DBHandlerPost;
import com.example.instagramclone.FirstFragment;
import com.example.instagramclone.PostDetails;
import com.example.instagramclone.R;
import com.example.instagramclone.SecondFragment;
import com.example.instagramclone.Username;
import com.example.instagramclone.databinding.RecyclerRowBinding;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PostAdapto    extends RecyclerView.Adapter <PostAdapto.PostHolder>{ // <içindeki yere post holderı kafamıza göre yazdık>
    //Kullanıcıya göstermek için arraylist lazım

    //4
    View.OnClickListener mOnClickListener;
    DBHandlerPost dbHandlerPost;
    private ArrayList <String>  postArrayList;
    private String artName;

     Context context;
    FirstFragment firstFragment;

    public PostAdapto(Context context){
        this.context =context;
    }

    public PostAdapto(ArrayList<String> postArrayList, Context context,    FirstFragment firstFragment){

        this.postArrayList = postArrayList;
        this.context = context;
      this.firstFragment = firstFragment;

    }

    class  PostHolder extends RecyclerView.ViewHolder{
        RecyclerRowBinding recyclerRowBinding; //5
        public PostHolder(RecyclerRowBinding recyclerRowBinding) {      // burada non null view item view yazıyordu sildik bunu yazdık
            super(recyclerRowBinding.getRoot());

              // buradada item view vardı biz yaptık içini
            this.recyclerRowBinding =  recyclerRowBinding ;



        }
//2

    }
    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //3
        //6
        RecyclerRowBinding recyclerRowBinding =RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);

      recyclerRowBinding.recyclerViewArtName.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              artName =recyclerRowBinding.recyclerViewArtName.getText().toString();
              System.out.println("ART NAME BURDA   "+artName);
              SecondFragment secondFragment  = new SecondFragment();
              Bundle args = new Bundle();
              args.putString("artName", artName);
              secondFragment.setArguments(args);
              FragmentTransaction fragmentTransaction = firstFragment.getActivity().getSupportFragmentManager().beginTransaction();

     fragmentTransaction.replace(R.id.fragmentContainerView,secondFragment);
              fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
              fragmentTransaction.addToBackStack(null);
              fragmentTransaction.commit();

          }
      });

        return new PostHolder(recyclerRowBinding);     // içinde  return null yazıyordu

    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {

//3
        //tıkladığımız sikisikinin art nameini alıp aratıcaz


        //son 7

        holder.recyclerRowBinding.recyclerViewArtName.setText(postArrayList.get(position));
        String artName = holder.recyclerRowBinding.recyclerViewArtName.getText().toString();




        // Picasso.get().load(postArrayList.get(position).artName).into(holder.recyclerRowBinding.recyclerViewImageView);

        // holder.recyclerRowBinding.RecyclerViewEmailText.setText(postArrayList.get(position).email);
        // holder.recyclerRowBinding.RecyclerViewCommentText.setText(postArrayList.get(position).comment);
        // Picasso.get().load(postArrayList.get(position).downloadUrl).into(holder.recyclerRowBinding.RecyclerViewImageView);


    }

    @Override
    public int getItemCount() {
        //3
        return postArrayList.size();
    }

//1

}
