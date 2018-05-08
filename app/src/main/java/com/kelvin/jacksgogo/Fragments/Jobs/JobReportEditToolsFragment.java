package com.kelvin.jacksgogo.Fragments.Jobs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobReportEditToolsFragment extends Fragment {

    @BindView(R.id.ll_photo)            LinearLayout photoLayout;
    @BindView(R.id.txt_photo_title)     TextView txtPhotoTitle;
    @BindView(R.id.txt_photo_desc)      TextView txtPhotoDesc;

    @BindView(R.id.ll_geo)              LinearLayout geoLayout;
    @BindView(R.id.txt_geo_title)       TextView txtGeoTitle;
    @BindView(R.id.txt_geo_desc)        TextView txtGeoDesc;

    @BindView(R.id.ll_pin)              LinearLayout pinLayout;
    @BindView(R.id.txt_pin_title)       TextView txtPinTitle;
    @BindView(R.id.txt_pin_desc)        TextView txtPinDesc;

    public JobReportEditToolsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_report_edit_tools, container, false);
        ButterKnife.bind(this, view);

        return view;
    }



}
