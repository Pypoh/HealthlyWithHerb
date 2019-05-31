package com.example.pypoh.healthlywithherb.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pypoh.healthlywithherb.R;
import com.example.pypoh.healthlywithherb.adapter.CarouselAdapter;
import com.example.pypoh.healthlywithherb.model.DataItem;
import com.example.pypoh.healthlywithherb.model.DataItemsList;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    // RecyclerView
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    private Toolbar toolbar;

    // Adapter
    private CarouselAdapter carouselAdapter = null;

    // Image Carousel
    private CarouselView carouselView;
    private int[] sampleImages = {R.drawable.carousel1, R.drawable.carousel1};

    // User Data
    private String userUID;

    private ProgressBar progressBar;

    // Firebase
    private FirebaseDatabase database;
    private DatabaseReference dataObatRef;

    private FirebaseUser currentUser;

    // Dataset
    private List<DataItemsList> dataSetList = new ArrayList<>();
    private List<DataItem> dataSetItem = new ArrayList<>();

    // Empty Constructor
    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = v.findViewById(R.id.activity_main_rv);
        toolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        progressBar = v.findViewById(R.id.activity_main_progressBar);

        carouselView = (CarouselView) v.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setImageResource(sampleImages[position]);
            }
        };
        carouselView.setImageListener(imageListener);
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getContext(), "Carousel Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        // Init Instance
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        carouselAdapter = new CarouselAdapter(getContext(), dataSetList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(carouselAdapter);

        // Get User ID
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userUID = currentUser.getUid();

        // Firebase Ref
        database = FirebaseDatabase.getInstance();
        dataObatRef = database.getReference("DataObat");

        loadData();

        return v;
    }

    private void loadData() {
        dataObatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSetItem.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    DataItem dataItem = ds.getValue(DataItem.class);
                    dataItem.setUID(ds.getKey());
                    dataSetItem.add(dataItem);
                }
                dataSetList.clear();
                dataSetList.add(new DataItemsList(1, "bg-parallax-carousel", "https://firebasestorage.googleapis.com/v0/b/fishgo-7d2ae.appspot.com/o/carousel_image%2Fbackgroundblue.jpg?alt=media&token=f522a333-965a-4f47-aac5-c56d8723e752", "#450003", "Kulit", dataSetItem));
                carouselAdapter.setmDataSet(dataSetList);
                carouselAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
