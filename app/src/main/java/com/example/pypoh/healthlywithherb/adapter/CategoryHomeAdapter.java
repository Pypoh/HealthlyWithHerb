package com.example.pypoh.healthlywithherb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pypoh.healthlywithherb.CategoryActivity;
import com.example.pypoh.healthlywithherb.ItemDetailActivity;
import com.example.pypoh.healthlywithherb.R;
import com.example.pypoh.healthlywithherb.model.DataCategory;
import com.example.pypoh.healthlywithherb.model.DataItem;
import com.example.pypoh.healthlywithherb.widget.GlideApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CategoryHomeAdapter extends RecyclerView.Adapter<CategoryHomeAdapter.ViewHolder> {

    private Context context;
    private List<DataCategory> dataSet;
    private FirebaseDatabase database;
    private DatabaseReference userRef;

    public CategoryHomeAdapter(Context context, List<DataCategory> dataSet) {
        this.context = context;
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public CategoryHomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewholder_category_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final DataCategory item = dataSet.get(i);
//        Log.d("DEBUGNAMA", item.getNama());
        viewHolder.categoryName.setText(item.getNama());

        viewHolder.categoryCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCategory = new Intent(context, CategoryActivity.class);
                toCategory.putExtra("KATEGORI_KEY", item.getNama());
                context.startActivity(toCategory);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView categoryImage;
        public TextView categoryName;
        public CardView categoryCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryCard = itemView.findViewById(R.id.category_card);
            categoryImage = itemView.findViewById(R.id.category_icon);
            categoryName = itemView.findViewById(R.id.category_tv);
        }
    }
}
