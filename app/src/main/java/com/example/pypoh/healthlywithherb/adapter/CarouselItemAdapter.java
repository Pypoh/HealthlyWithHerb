package com.example.pypoh.healthlywithherb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pypoh.healthlywithherb.ItemDetailActivity;
import com.example.pypoh.healthlywithherb.R;
import com.example.pypoh.healthlywithherb.model.DataItem;
import com.example.pypoh.healthlywithherb.viewholder.CarouselInvisbileItemViewHolder;
import com.example.pypoh.healthlywithherb.viewholder.CarouselItemViewHolder;
import com.example.pypoh.healthlywithherb.widget.GlideApp;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CarouselItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DataItem> items;
    private Context context;
    private boolean isInvisible;

    private Locale localeID = new Locale("in", "ID");
    private DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(localeID);
    private NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);

    private final static int INVISIBLE_VIEW_TYPE = 100;
    public CarouselItemAdapter(Context context, boolean isInvisibleStart) {
        this.context = context;
        this.isInvisible= isInvisibleStart;
    }

    public void setDao(List<DataItem> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == INVISIBLE_VIEW_TYPE)
            return new CarouselInvisbileItemViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewholder_carousel_invisible_item, parent, false));
        else
            return new CarouselItemViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewholder_carousel_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CarouselItemViewHolder) {
            if (isInvisible)
                position=position-1;
            final DataItem item = items.get(position);
            CarouselItemViewHolder vh = (CarouselItemViewHolder) holder;
            GlideApp.with(context)
                    .load(item.getGambar())
                    .placeholder(R.drawable.place_holder)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(vh.ivCover);
            vh.titleTv.setText(item.getNama());
            vh.subTitleTv.setText(item.getPenyakit());


            vh.ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toDetail = new Intent(context, ItemDetailActivity.class);
                    toDetail.putExtra("KEY", item.getUID());
                    context.startActivity(toDetail);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        if (items == null)
            return 0;
        if (isInvisible)
            return items.size()+1;
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isInvisible && position==0)
            return INVISIBLE_VIEW_TYPE;
        return super.getItemViewType(position);
    }
}
