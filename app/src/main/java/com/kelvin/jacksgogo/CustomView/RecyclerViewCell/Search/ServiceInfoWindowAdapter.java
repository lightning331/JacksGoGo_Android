package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Search;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/24/2017.
 */

public class ServiceInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View contentView;
    private final Context mContext;

    public ServiceInfoWindowAdapter(Context context) {
        contentView = getContentView(LayoutInflater.from(context));
        mContext = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        contentView.setLayoutParams(getLayoutParams());
        return contentView;
    }

    private View getContentView(LayoutInflater inflater) {
        return inflater.inflate(R.layout.view_service_info_window_adapter, null);
    }

    private RelativeLayout.LayoutParams getLayoutParams() {
        return (new RelativeLayout.LayoutParams(dpToPx(310), dpToPx(165)));
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public int pxToDp(int px) {
        DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }
}
