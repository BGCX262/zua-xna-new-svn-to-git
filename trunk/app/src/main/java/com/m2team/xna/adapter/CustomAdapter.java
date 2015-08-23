package com.m2team.xna.adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.etsy.android.grid.util.DynamicHeightImageView;
import com.m2team.xna.R;
import com.m2team.xna.model.Photo;
import com.m2team.xna.utils.Common;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Random;

public class CustomAdapter extends BaseAdapter {
    private static final String TAG = "CustomAdapter";
    private final LayoutInflater mLayoutInflater;
    private final Random mRandom;
    private Context mContext;
    private static final SparseArray<Double> sPositionHeightRatios = new SparseArray<Double>();
    ArrayList<Photo> photoList;

    public CustomAdapter(Context context, ArrayList<Photo> photoList) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mRandom = new Random();
        this.photoList = photoList;
        mContext = context;
    }

    @Override
    public int getCount() {
        return photoList.size();
    }

    @Override
    public Photo getItem(int position) {
        return photoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView,
                        final ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.layout_each_image,
                    parent, false);
            vh = new ViewHolder();
            vh.imgView = (DynamicHeightImageView) convertView
                    .findViewById(R.id.imgView_dynamic);
            vh.titleView = (TextView) convertView.findViewById(R.id.title_picture);
            vh.authorView = (TextView) convertView.findViewById(R.id.author);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        double positionHeight = getPositionRatio(position);
        vh.imgView.setHeightRatio(positionHeight);
        vh.titleView.setText(getItem(position).getTitle());
        vh.authorView.setText(getItem(position).getStrAuthor());
        Common.setFont(mContext, convertView, Common.ROBOTO_REGULAR);
        Picasso.with(mContext)
                .load(photoList.get(position).getUrl()).fit()
                .error(R.drawable.ic_error).placeholder(R.drawable.ic_launcher)
                .into(vh.imgView);
        return convertView;
    }

    static class ViewHolder {
        DynamicHeightImageView imgView;
        TextView titleView;
        TextView authorView;
    }

    private double getPositionRatio(final int position) {
        double ratio = sPositionHeightRatios.get(position, 0.0);
        // if not yet done generate and stash the columns height
        // in our real world scenario this will be determined by
        // some match based on the known height and width of the image
        // and maybe a helpful way to get the column height!
        if (ratio == 0) {
            ratio = getRandomHeightRatio();
            sPositionHeightRatios.append(position, ratio);
            Log.d(TAG, "getPositionRatio:" + position + " ratio:" + ratio);
        }
        return ratio;
    }

    private double getRandomHeightRatio() {
        // return  (mRandom.nextDouble() / 2.0) + 1; // height will be 1.0 - 1.5
        return 1;
    }
}


