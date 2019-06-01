package com.example.pypoh.healthlywithherb.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pypoh.healthlywithherb.CategoryActivity;
import com.example.pypoh.healthlywithherb.R;
import com.example.pypoh.healthlywithherb.constant.Constant;
import com.example.pypoh.healthlywithherb.model.DataItemsList;
import com.example.pypoh.healthlywithherb.viewholder.CarouselBgParallaxViewHolder;
import com.example.pypoh.healthlywithherb.viewholder.CarouselViewHolder;
import com.example.pypoh.healthlywithherb.widget.GlideApp;

import java.util.ArrayList;
import java.util.List;

public class CarouselAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<DataItemsList> mDataSet = new ArrayList<>();

    private final static int CAROUSEL_NORMAL_VIEW_TYPE = 100;
    private final static int CAROUSEL_PARALLAX_BG_VIEW_TYPE = 101;

    public CarouselAdapter(Context context, List<DataItemsList> mDataSet) {
        this.context = context;
        this.mDataSet = mDataSet;
    }

    public void setmDataSet(List<DataItemsList> mDataSet) {
        this.mDataSet = mDataSet;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == CAROUSEL_PARALLAX_BG_VIEW_TYPE)
            return new CarouselBgParallaxViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewholder_carousel_bg_parallax, parent, false));
        else
            return new CarouselViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewholder_carousel, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final DataItemsList dataItemsList = mDataSet.get(position);
        if (holder instanceof CarouselViewHolder) {
            CarouselViewHolder vh = (CarouselViewHolder) holder;
            vh.titleTv.setText(dataItemsList.getItemTitle());
            if (vh.gridAdapter == null) {
                vh.setCarouselAdapter(context, dataItemsList.getItems(),false);
            } else {
                vh.updateGridAdapter(dataItemsList.getItems());
            }
            vh.li.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "See More Clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if(holder instanceof CarouselBgParallaxViewHolder)
        {
            final CarouselBgParallaxViewHolder vh = (CarouselBgParallaxViewHolder) holder;
            vh.titleTv.setText(dataItemsList.getItemTitle());

            if (vh.gridAdapter == null) {
                GlideApp.with(context)
                        .load(dataItemsList.getItemBgUrl())
                        .placeholder(R.drawable.place_holder)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .into(vh.bgIv);
//                vh.alphaView.setBackgroundColor(Color.parseColor(dataItemsList.getItemFgColor()));
                vh.horizontalRv.setOnScrollListener(new RecyclerView.OnScrollListener() {

                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);

                        // Check if the the visible item is the first item
                        if (vh.linearLayoutManager.findFirstVisibleItemPosition()==0)
                        {
                            // Get view of the first item
                            View firstVisibleItem = vh.linearLayoutManager.findViewByPosition(vh.linearLayoutManager.findFirstVisibleItemPosition());
                            float distanceFromLeft = firstVisibleItem.getLeft(); // distance from the left
                            float translateX = (int)distanceFromLeft * 0.2f; // move x distance

                            vh.bgIv.setTranslationX(translateX);

                            // If the view scroll pass the starting position, change color view alpha
                            if (distanceFromLeft <= 0) {
                                float itemSize = firstVisibleItem.getWidth(); // view size
                                float alpha = (Math.abs(distanceFromLeft) / itemSize * 0.80f); // view transparency

                                //Set alpha to image to bring 'fade out' and 'fade in' effect
                                vh.bgIv.setAlpha(1 - alpha);
                                //Set alpha to color view to bring 'darker' and 'clearer' effect
//                                vh.alphaView.setAlpha(alpha);
                            }
                        }
                    }
                });

                vh.setCarouselAdapter(context, dataItemsList.getItems(), true);

            } else {
                vh.updateGridAdapter(dataItemsList.getItems());
            }
            vh.li.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // See More Click
                    Intent toCategory = new Intent(context, CategoryActivity.class);
                    toCategory.putExtra("KATEGORI_KEY", dataItemsList.getItemTitle());
                    context.startActivity(toCategory);
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return mDataSet.size();
//        if (itemsListModel == null)
//            return 0;
//        else if (itemsListModel.getItemsList() == null)
//            return 0;
//        return itemsListModel.getItemsList().size();
    }

    @Override
    public int getItemViewType(int position) {
//        try {
//            if (itemsListModel.getItemsList().get(position).getType().equals(Constant.CarouselType.NORMAL))
//                return CAROUSEL_NORMAL_VIEW_TYPE;
//            else if (itemsListModel.getItemsList().get(position).getType().equals(Constant.CarouselType.BG_PARALLAX))
//                return CAROUSEL_PARALLAX_BG_VIEW_TYPE;
//            else
//                return CAROUSEL_NORMAL_VIEW_TYPE;
//        }
//        catch (Exception e)
//        {
//            return CAROUSEL_NORMAL_VIEW_TYPE;
//        }
        try {
            if (mDataSet.get(position).getType().equals(Constant.CarouselType.NORMAL))
                return CAROUSEL_NORMAL_VIEW_TYPE;
            else if (mDataSet.get(position).getType().equals(Constant.CarouselType.BG_PARALLAX))
                return CAROUSEL_PARALLAX_BG_VIEW_TYPE;
            else
                return CAROUSEL_NORMAL_VIEW_TYPE;
        }
        catch (Exception e)
        {
            return CAROUSEL_NORMAL_VIEW_TYPE;
        }
    }

    // Add blur to bitmap
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private Bitmap createBlurBitmap(Bitmap src, float r) {
        if (r <= 0) {
            r = 0.1f;
        } else if (r > 25) {
            r = 25.0f;
        }

        Bitmap bitmap = Bitmap.createBitmap(src.getWidth(), src.getHeight(), Bitmap.Config.ARGB_8888);
        RenderScript renderScript = RenderScript.create(context);

        Allocation blurInput = Allocation.createFromBitmap(renderScript, src);
        Allocation blurOutput = Allocation.createFromBitmap(renderScript, bitmap);

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        blur.setInput(blurInput);
        blur.setRadius(r);
        blur.forEach(blurOutput);

        blurOutput.copyTo(bitmap);
        renderScript.destroy();

        return bitmap;
    }
}
