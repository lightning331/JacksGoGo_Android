package com.kelvin.jacksgogo.Adapter;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.JobDetail.EditJobAddressCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.SectionTitleView;
import com.kelvin.jacksgogo.Fragments.Appointments.EditJobFragment;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/10/2017.
 */

public class EditJobAddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    int IMTE_COUNT = 5;

    public EditJobAddressAdapter(EditJobFragment editJobFragment) {

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View sectionTitle1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_title_view, parent, false);
                SectionTitleView sectionTitleView = new SectionTitleView(sectionTitle1);
                sectionTitleView.txtTitle.setText(R.string.edit_job_address_title);
                sectionTitleView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
                return sectionTitleView;
            case 1:
                View descTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.section_title_view, parent, false);
                SectionTitleView descTitleViewHolder = new SectionTitleView(descTitleView);
                descTitleViewHolder.txtTitle.setText(R.string.edit_job_address_desc);
                return descTitleViewHolder;
            case 2:
                View unitTextView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_address_cell, parent, false);
                EditJobAddressCell unitTextViewHolder = new EditJobAddressCell(unitTextView);
                unitTextViewHolder.hint.setText("Unit");
                unitTextViewHolder.title.setText("2");
                return unitTextViewHolder;
            case 3:
                View streetTextView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_address_cell, parent, false);
                EditJobAddressCell streetTextViewHolder = new EditJobAddressCell(streetTextView);
                streetTextViewHolder.hint.setText("Street");
                streetTextViewHolder.title.setText("Jurong West Avenune 5");
                return streetTextViewHolder;
            case 4:
                View takePhotoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_address_cell, parent, false);
                EditJobAddressCell takePhotoViewHolder = new EditJobAddressCell(takePhotoView);
                takePhotoViewHolder.hint.setText("Postcode");
                takePhotoViewHolder.title.setText("34534");
                return takePhotoViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return IMTE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
