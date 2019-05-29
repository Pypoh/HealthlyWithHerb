package com.example.pypoh.healthlywithherb.viewholder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pypoh.healthlywithherb.R;
import com.example.pypoh.healthlywithherb.adapter.CarouselItemAdapter;
import com.example.pypoh.healthlywithherb.model.DataItem;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;

import java.util.List;

public class CarouselViewHolder extends RecyclerView.ViewHolder{
    public ImageView bgIv;
    public RecyclerView horizontalRv;
    public TextView titleTv;
    public LinearLayout li;
    public CarouselItemAdapter gridAdapter;
    public LinearLayoutManager linearLayoutManager;
    private List<DataItem> items;
    public CarouselViewHolder(View itemView) {
        super(itemView);
        horizontalRv = (RecyclerView) itemView.findViewById(R.id.viewholder_carousel_rv);
        titleTv = (TextView) itemView.findViewById(R.id.viewholder_carousel_title_tv);
        li = (LinearLayout) itemView.findViewById(R.id.viewholder_carousel_li);
        bgIv =  itemView.findViewById(R.id.viewholder_carousel_bg_iv);
        linearLayoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false);
        horizontalRv.setLayoutManager(linearLayoutManager);
        horizontalRv.setHasFixedSize(true);
        horizontalRv.setNestedScrollingEnabled(false);

        //Adding snapping effect onto recycler view
        SnapHelper snapHelperStart = new GravitySnapHelper(Gravity.START);
        snapHelperStart.attachToRecyclerView(horizontalRv);
    }
    public void setCarouselAdapter(Context context, List<DataItem> items, boolean isInvisibleStart) {
        this.items = items;
        gridAdapter = new CarouselItemAdapter(context, isInvisibleStart);
        gridAdapter.setDao(items);
        horizontalRv.setAdapter(gridAdapter);
    }

    public void updateGridAdapter(List<DataItem> itemsLists) {
        this.items = itemsLists;
        gridAdapter.setDao(items);
        gridAdapter.notifyDataSetChanged();
    }
}
