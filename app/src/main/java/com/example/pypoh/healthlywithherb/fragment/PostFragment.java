package com.example.pypoh.healthlywithherb.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pypoh.healthlywithherb.R;
import com.example.pypoh.healthlywithherb.adapter.PostAdapter;
import com.example.pypoh.healthlywithherb.model.DataItem;
import com.example.pypoh.healthlywithherb.model.DataPostUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PostFragment extends Fragment {


    public PostFragment() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private DatabaseReference userPostRef;
    private List<DataItem> dataSet = new ArrayList<>();
    private PostAdapter postAdapter;


    private String userID;
    private FirebaseUser currentUser;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_post, container, false);
        //binding
        recyclerView = v.findViewById(R.id.rv_post);
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = currentUser.getUid();
        userPostRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userID).child("Posts");

        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        postAdapter = new PostAdapter(getContext(), dataSet);
        recyclerView.setAdapter(postAdapter);

        loadData();
        return v;
    }

    private void loadData() {
        userPostRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    DataItem dataItem = ds.getValue(DataItem.class);
                    dataSet.add(dataItem);
                }
                postAdapter.setItems(dataSet);
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
