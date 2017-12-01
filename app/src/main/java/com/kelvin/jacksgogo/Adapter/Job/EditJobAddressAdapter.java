package com.kelvin.jacksgogo.Adapter.Job;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment.AppFilterOptionCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Edit.EditJobAddressCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Edit.EditJobTabbarView;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.SectionTitleView;
import com.kelvin.jacksgogo.Models.Jobs_Services.JGGServiceModel;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/10/2017.
 */

public class EditJobAddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, TextWatcher {

    Context mContext;
    int ITEM_COUNT = 5;

    JGGServiceModel serviceObject;
    String strUnit;
    String strStreet;
    String strPostCode;
    boolean isRequest;

    AppFilterOptionCell nextButtonCell;
    EditJobAddressCell unitCell;
    EditJobAddressCell streetCell;
    EditJobAddressCell postcodeCell;

    public EditJobAddressAdapter(Context context, boolean b, JGGServiceModel data) {
        this.mContext = context;
        this.isRequest = b;
        this.serviceObject = data;
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
                unitCell = new EditJobAddressCell(unitTextView);
                return unitCell;
            case 3:
                View streetTextView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_address_cell, parent, false);
                streetCell = new EditJobAddressCell(streetTextView);
                return streetCell;
            case 4:
                View takePhotoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.edit_job_address_cell, parent, false);
                postcodeCell = new EditJobAddressCell(takePhotoView);
                return postcodeCell;
            case 5:
                View originalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_filter_option_cell, parent, false);
                nextButtonCell = new AppFilterOptionCell(originalView);
                return nextButtonCell;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == 2) {
            unitCell = (EditJobAddressCell)holder;
            unitCell.hint.setText(R.string.address_unit_title);
            if (!isRequest) {
                unitCell.title.setText("2");
            } else {
                if (serviceObject.getUnit() == null) {
                    unitCell.title.setHint("e.g.2");
                } else {
                    unitCell.title.setText(serviceObject.getUnit());
                }
                unitCell.title.addTextChangedListener(this);
            }
        } else if (position == 3) {
            streetCell = (EditJobAddressCell)holder;
            streetCell.hint.setText(R.string.address_street_title);
            if (!isRequest) {
                streetCell.title.setText("Jurong West Avenune 5");
            } else {
                if (serviceObject.getStreet() == null) {
                    streetCell.title.setHint("e.g.Jurong West Avenue 5");
                } else {
                    streetCell.title.setText(serviceObject.getStreet());
                }
                streetCell.title.addTextChangedListener(this);
            }
        } else if (position == 4) {
            postcodeCell = (EditJobAddressCell)holder;
            postcodeCell.hint.setText(R.string.address_postcode_title);
            if (!isRequest) {
                postcodeCell.title.setText("34534");
            } else {
                if (serviceObject.getPostcode() == null) {
                    postcodeCell.title.setHint("e.g.34534");
                } else {
                    postcodeCell.title.setText(serviceObject.getPostcode());
                }
                postcodeCell.title.addTextChangedListener(this);
            }
        } else if (position == 5) {
            nextButtonCell = (AppFilterOptionCell)holder;
            nextButtonCell.title.setText("Next");
            nextButtonCell.title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey2));
            nextButtonCell.btnOriginal.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGrey4));
            nextButtonCell.btnOriginal.setBorderWidth((float)0);
        }
    }

    @Override
    public int getItemCount() {
        if (isRequest) return ITEM_COUNT = 6;
        return ITEM_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onNextButtonClick(EditJobTabbarView.EditTabStatus status, String unit, String street, String postcode);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.view_filter_bg) {
            listener.onNextButtonClick(EditJobTabbarView.EditTabStatus.REPORT, strUnit, strStreet, strPostCode);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (unitCell.title.length() > 0
                && streetCell.title.length() > 0
                && postcodeCell.title.length() > 0) {
            strUnit = unitCell.title.getText().toString();
            strStreet = streetCell.title.getText().toString();
            strPostCode = postcodeCell.title.getText().toString();

            nextButtonCell.title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
            nextButtonCell.btnOriginal.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
            nextButtonCell.btnOriginal.setBorderWidth((float)0);
            nextButtonCell.btnOriginal.setOnClickListener(this);
        } else {
            nextButtonCell.title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey2));
            nextButtonCell.btnOriginal.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGrey4));
            nextButtonCell.btnOriginal.setBorderWidth((float) 0);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
