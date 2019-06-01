package com.example.pypoh.healthlywithherb.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.pypoh.healthlywithherb.R;
import com.example.pypoh.healthlywithherb.adapter.CarouselAdapter;
import com.example.pypoh.healthlywithherb.adapter.CategoryHomeAdapter;
import com.example.pypoh.healthlywithherb.model.DataCategory;
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
    private RecyclerView recyclerViewCategory;
    private LinearLayoutManager linearLayoutManager;

    private Toolbar toolbar;

    // Adapter
    private CarouselAdapter carouselAdapter = null;
    private CategoryHomeAdapter categoryHomeAdapter = null;

    // Image Carousel
    private CarouselView carouselView;
    private int[] sampleImages = {R.drawable.carousel1, R.drawable.carousel1};

    // User Data
    private String userUID;

    private ProgressBar progressBar;

    // Firebase
    private FirebaseDatabase database;
    private DatabaseReference dataObatRef;
    private DatabaseReference dataCategoryRef;

    private FirebaseUser currentUser;

    // Dataset
    private List<DataItemsList> dataSetList = new ArrayList<>();
    private List<DataItem> dataSetItem = new ArrayList<>();
    private List<DataCategory> dataSetCategory = new ArrayList<>();

    // Empty Constructor
    public HomeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = v.findViewById(R.id.activity_main_rv);
        recyclerViewCategory = v.findViewById(R.id.category_rv);
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

        categoryHomeAdapter = new CategoryHomeAdapter(getContext(), dataSetCategory);
        recyclerViewCategory.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewCategory.setAdapter(categoryHomeAdapter);

        ViewCompat.setNestedScrollingEnabled(recyclerView, false);

        // Get User ID
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userUID = currentUser.getUid();

        // Firebase Ref
        database = FirebaseDatabase.getInstance();
        dataObatRef = database.getReference("DataObat");
        dataCategoryRef = database.getReference("DataKategori");

        loadData();
        loadCategory();

        return v;
    }

    private void loadCategory() {
        dataCategoryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSetCategory.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    DataCategory dataCategory = ds.getValue(DataCategory.class);
//                    Log.d("DEBUGNAMAHOME", dataCategory.getNama());
                    dataSetCategory.add(dataCategory);
                }
                categoryHomeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                dataSetList.add(new DataItemsList(1, "bg-parallax-carousel", "https://firebasestorage.googleapis.com/v0/b/healthyherb-6c168.appspot.com/o/carousel_image%2Fgreen1.png?alt=media&token=fbd10945-c886-431e-bab1-a7c43d48223f", "#450003", "Obat Populer", dataSetItem));
                dataSetList.add(new DataItemsList(2, "bg-parallax-carousel", "https://firebasestorage.googleapis.com/v0/b/healthyherb-6c168.appspot.com/o/carousel_image%2Forange1.png?alt=media&token=96d85e6f-9363-4e08-a804-c3984b7ce20c", "#450003", "Kulit", dataSetItem));
                carouselAdapter.setmDataSet(dataSetList);
                carouselAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
