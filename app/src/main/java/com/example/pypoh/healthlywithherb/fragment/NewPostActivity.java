package com.example.pypoh.healthlywithherb.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.pypoh.healthlywithherb.MainActivity;
import com.example.pypoh.healthlywithherb.R;
import com.example.pypoh.healthlywithherb.model.DataItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewPostActivity extends AppCompatActivity {

    // Content
    private EditText mEditJudul, mEditPenyakit, mEditDeskripsi;
    private Spinner spinKategori;
    private Button submitBtn;
    private RelativeLayout textAreaDesc;

    private String[] kategoriList = {"Kulit", "Kepala"};
    private String kategoriText = "Default";
    private String userID;

    private FirebaseUser currentUser;

    // Ref
    DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userID = currentUser.getUid();

        bindViews();
    }

    private void bindViews() {
        mEditJudul = findViewById(R.id.et_nama_obat);
        mEditPenyakit = findViewById(R.id.et_penyakit);
        mEditDeskripsi = findViewById(R.id.et_deskripsi);
        spinKategori = findViewById(R.id.spinner_kategori);
        submitBtn = findViewById(R.id.button_submit);
        textAreaDesc = findViewById(R.id.layout_textarea_deskirpsi);

        textAreaDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditDeskripsi.setFocusable(true);
                mEditDeskripsi.requestFocus();
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitData();
            }
        });

        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, kategoriList);
        spinKategori.setAdapter(spinnerAdapter);
        spinKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void submitData() {
        String uid = userRef.child(userID).child("Posts").push().getKey();
        DataItem dataItem = new DataItem(mEditJudul.getText().toString(), mEditPenyakit.getText().toString(),
                mEditDeskripsi.getText().toString(), kategoriText, null);
        dataItem.setStatus("Pending");
        dataItem.setTanggal(getDate());
        userRef.child(userID).child("Posts").child(uid).setValue(dataItem);
        showCustomDialog();
    }

    private String getDate() {
        String date = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
        return date;
    }

    private void showCustomDialog() {
        Button button;
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.success_dialog, viewGroup, false);
        button = dialogView.findViewById(R.id.buttonOk_alert);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toMain();
            }
        });

    }

    private void toMain() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
