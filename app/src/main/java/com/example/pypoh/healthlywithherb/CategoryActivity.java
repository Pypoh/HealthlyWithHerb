package com.example.pypoh.healthlywithherb;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.pypoh.healthlywithherb.adapter.CarouselAdapter;
import com.example.pypoh.healthlywithherb.adapter.CategoryAdapter;
import com.example.pypoh.healthlywithherb.model.DataItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    // Content
    private RecyclerView recyclerView;

    private CategoryAdapter categoryAdapter;

    // Firebase
    private FirebaseDatabase database;
    private DatabaseReference itemDataRef;
    private FirebaseUser currentUser;

    // Dataset
    private List<DataItem> dataSetItem = new ArrayList<>();

    // User Data
    private String userUID;

    private String KATEGORI_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                KATEGORI_KEY = extras.getString("KATEGORI_KEY");
            }
        } else {
            KATEGORI_KEY = (String) savedInstanceState.getSerializable("KATEGORI_KEY");
        }

        firebaseRef();
        bindView();
        setupRV();
        loadData();

    }

    private void loadData() {
        itemDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSetItem.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        DataItem dataItem = ds.getValue(DataItem.class);
                        dataItem.setUID(ds.getKey());
                        if (dataItem.getKategori().equalsIgnoreCase(KATEGORI_KEY)) {
                            dataSetItem.add(dataItem);
                        }
                }
                categoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setupRV() {
        categoryAdapter = new CategoryAdapter(this, dataSetItem);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(categoryAdapter);
    }

    private void bindView() {
        recyclerView = findViewById(R.id.rv_items_category);
    }

    private void firebaseRef() {
        // Get User ID
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userUID = currentUser.getUid();

        database = FirebaseDatabase.getInstance();
        itemDataRef = database.getReference("DataObat");
    }
}
