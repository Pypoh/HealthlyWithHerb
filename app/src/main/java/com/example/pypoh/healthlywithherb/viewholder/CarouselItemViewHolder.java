package com.example.pypoh.healthlywithherb.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pypoh.healthlywithherb.R;

public class CarouselItemViewHolder extends RecyclerView.ViewHolder{
    public ImageView ivCover;
    public TextView titleTv;
    public TextView subTitleTv;
    public LinearLayout ll;
    public CarouselItemViewHolder(View itemView) {
        super(itemView);
        ll = (LinearLayout) itemView.findViewById(R.id.viewholder_carousel_item_ll);
        ivCover = itemView.findViewById(R.id.viewholder_carousel_item_cover_iv);
        titleTv = (TextView) itemView.findViewById(R.id.viewholder_carousel_item_title_tv);
        subTitleTv = (TextView) itemView.findViewById(R.id.viewholder_carousel_item_subTitle_tv);

    }
}
