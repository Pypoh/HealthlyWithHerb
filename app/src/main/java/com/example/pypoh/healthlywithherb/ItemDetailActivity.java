package com.example.pypoh.healthlywithherb;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pypoh.healthlywithherb.model.DataItem;
import com.example.pypoh.healthlywithherb.widget.GlideApp;
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

public class ItemDetailActivity extends AppCompatActivity {

    private String KEY;
    private String userID;

    // Dataset
    private List<DataItem> dataSet = new ArrayList<>();

    // Firebase
    private FirebaseDatabase database;
    private DatabaseReference userRef;
    private FirebaseUser currentUser;

    // Content
    private TextView nama, statusStock, harga, info, namaPenjual, lokasiPenjual, reputasiPenjual, waktuPengiriman;
    private Button pesanSekarang;
    private ImageView addToCart, itemImage;

    // Carousel
    private CarouselView carouselView;
    private int[] sampleImages = {R.drawable.sunset, R.drawable.sunset};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                KEY = extras.getString("KEY");
            }
        } else {
            KEY = (String) savedInstanceState.getSerializable("KEY");
        }

        // Get User ID
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = currentUser.getUid();

        bindView();
        getDataUser();
        loadData();
        setupCarousel();
        setView();
    }

    private void bindView() {
        nama = findViewById(R.id.detail_nama);
        statusStock = findViewById(R.id.detail_penyakit);
        info = findViewById(R.id.tv_info_obat);
        namaPenjual = findViewById(R.id.tv_nama_narasumber);
        lokasiPenjual = findViewById(R.id.tv_jumlah_post);
        reputasiPenjual = findViewById(R.id.tv_reputasi);
        carouselView = (CarouselView) findViewById(R.id.carouselView);
    }

    private void setupCarousel() {
        carouselView.setPageCount(1);
        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
//                setImage(imageView);
            }
        };
        carouselView.setImageListener(imageListener);
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(ItemDetailActivity.this, "Carousel Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setImage(ImageView image) {
        GlideApp.with(this)
                .load(dataSet.get(0).getGambar())
                .placeholder(R.drawable.place_holder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image);
    }

    private void getDataUser() {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("Users");
    }

    private void setView() {

    }

    private void loadData() {

    }
}
