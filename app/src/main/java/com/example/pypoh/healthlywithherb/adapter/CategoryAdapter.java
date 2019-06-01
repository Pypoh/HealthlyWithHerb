package com.example.pypoh.healthlywithherb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pypoh.healthlywithherb.ItemDetailActivity;
import com.example.pypoh.healthlywithherb.R;
import com.example.pypoh.healthlywithherb.model.DataItem;
import com.example.pypoh.healthlywithherb.widget.GlideApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private Context context;
    private List<DataItem> dataSet;
    private FirebaseDatabase database;
    private DatabaseReference userRef;

    public CategoryAdapter(Context context, List<DataItem> dataSet) {
        this.context = context;
        this.dataSet = dataSet;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.viewholder_carousel_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final DataItem item = dataSet.get(i);
        GlideApp.with(context)
                .load(item.getGambar())
                .placeholder(R.drawable.place_holder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(viewHolder.ivCover);
        viewHolder.titleTv.setText(item.getNama());
        viewHolder.subTitleTv.setText(item.getPenyakit());


        viewHolder.ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDetail = new Intent(context, ItemDetailActivity.class);
                toDetail.putExtra("KEY", item.getUID());
                toDetail.putExtra("SOURCEKEY", item.getNarasumber());
                context.startActivity(toDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivCover;
        public TextView titleTv;
        public TextView subTitleTv;
        public LinearLayout ll;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll = (LinearLayout) itemView.findViewById(R.id.viewholder_carousel_item_ll);
            ivCover = itemView.findViewById(R.id.viewholder_carousel_item_cover_iv);
            titleTv = (TextView) itemView.findViewById(R.id.viewholder_carousel_item_title_tv);
            subTitleTv = (TextView) itemView.findViewById(R.id.viewholder_carousel_item_subTitle_tv);
        }
    }
}
