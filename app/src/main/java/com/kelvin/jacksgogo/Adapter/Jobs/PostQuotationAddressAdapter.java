package com.kelvin.jacksgogo.Adapter.Jobs;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment.AppFilterOptionCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Edit.EditJobAddressCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs.JobDetailInviteButtonCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Services.QuotationCoordinateCell;
import com.kelvin.jacksgogo.CustomView.Views.HeaderTitleView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.System.JGGAddressModel;

/**
 * Created by PUMA on 11/10/2017.
 */

public class PostQuotationAddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, TextWatcher {

    private Context mContext;
    private int ITEM_COUNT = 9;

    private JGGAddressModel mAddress;
    private String strStreet;

    AppFilterOptionCell nextButtonCell;
    EditJobAddressCell placeNameCell;
    EditJobAddressCell unitCell;
    EditJobAddressCell streetCell;
    EditJobAddressCell postcodeCell;

    public PostQuotationAddressAdapter(Context context, JGGAddressModel data) {
        this.mContext = context;
        this.mAddress = data;
    }

    public void notifyDataChanged(JGGAddressModel addressModel) {
        mAddress = addressModel;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View sectionTitle1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
                HeaderTitleView sectionTitleView = new HeaderTitleView(sectionTitle1);
                sectionTitleView.txtTitle.setText(R.string.edit_job_address_title);
                sectionTitleView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
                return sectionTitleView;
            case 1:
                View descTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
                HeaderTitleView descTitleViewHolder = new HeaderTitleView(descTitleView);
                descTitleViewHolder.txtTitle.setText(R.string.edit_job_address_desc);
                return descTitleViewHolder;
            case 2:     // Select Address button
                View locationView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_job_detail_invite_button, parent, false);
                JobDetailInviteButtonCell locationViewHolder = new JobDetailInviteButtonCell(locationView);
                locationViewHolder.inviteButton.setText("Select Location From Map");
                locationViewHolder.inviteButton.setOnClickListener(this);
                return locationViewHolder;
            case 3:     // Place Name
                View placeNameView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_edit_job_address, parent, false);
                placeNameCell = new EditJobAddressCell(placeNameView);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 35);
                LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 65);
                placeNameCell.titleLayout.setLayoutParams(params);
                placeNameCell.txtLayout.setLayoutParams(param1);

                placeNameCell.hint.setHint(R.string.address_place_name_title);
                placeNameCell.title.setHint("(optional)");
                placeNameCell.title.setInputType(InputType.TYPE_CLASS_TEXT);
                placeNameCell.title.addTextChangedListener(this);
                return placeNameCell;
            case 4:     // Unit
                View unitTextView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_edit_job_address, parent, false);
                unitCell = new EditJobAddressCell(unitTextView);
                unitCell.hint.setHint(R.string.address_unit_title);
                if (mAddress.getUnit() == null) {
                    unitCell.title.setHint("e.g.2");
                } else {
                    unitCell.title.setText(mAddress.getUnit());
                }
                unitCell.title.setInputType(InputType.TYPE_CLASS_TEXT);
                unitCell.title.addTextChangedListener(this);
                return unitCell;
            case 5:     // Street
                View streetTextView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_edit_job_address, parent, false);
                streetCell = new EditJobAddressCell(streetTextView);
                streetCell.hint.setHint(R.string.address_street_title);
                if (mAddress.getStreet() == null) {
                    if (mAddress.getAddress() == null) {
                        streetCell.title.setHint("e.g. Jurong West Avenue 5");
                    } else
                        strStreet = mAddress.getAddress();
                } else
                    strStreet = mAddress.getStreet();
                streetCell.title.setText(strStreet);
                streetCell.title.addTextChangedListener(this);
                return streetCell;
            case 6:     // Postal Code
                View takePhotoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_edit_job_address, parent, false);
                postcodeCell = new EditJobAddressCell(takePhotoView);
                postcodeCell.hint.setHint(R.string.address_postcode_title);
                if (mAddress.getPostalCode() == null) {
                    postcodeCell.title.setHint("e.g. 34534");
                } else {
                    postcodeCell.title.setText(mAddress.getPostalCode());
                }
                postcodeCell.title.setInputType(InputType.TYPE_CLASS_TEXT);
                postcodeCell.title.setImeOptions(EditorInfo.IME_ACTION_DONE);
                postcodeCell.title.addTextChangedListener(this);
                return postcodeCell;
            case 7:
                View coordinate = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_coordinates, parent, false);
                QuotationCoordinateCell coordinateCell = new QuotationCoordinateCell(coordinate);
                if (mAddress.getLat() > 0 && mAddress.getLon() > 0) {
                    coordinateCell.lblCoordinate.setText(String.valueOf(mAddress.getLat()) + "° N,"
                    + String.valueOf(mAddress.getLon()) + "° E");
                }
                return coordinateCell;
            case 8:
                View nextButton = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_app_filter_option, parent, false);
                nextButtonCell = new AppFilterOptionCell(nextButton);
                nextButtonCell.title.setText("Next");
                if (mAddress.getUnit() == null
                        && mAddress.getStreet() == null
                        && mAddress.getPostalCode() == null)
                    onNextButtonDisable();
                else
                    onNextButtonEnable();
                return nextButtonCell;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return ITEM_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onNextButtonClick(View view, JGGAddressModel address);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.view_filter_bg
                || view.getId() == R.id.btn_invite) {
            JGGAddressModel address = new JGGAddressModel();
            address.setPlaceName(placeNameCell.title.getText().toString());
            address.setUnit(unitCell.title.getText().toString());
            address.setStreet(streetCell.title.getText().toString());
            address.setPostalCode(postcodeCell.title.getText().toString());
            address.setLat(mAddress.getLat());
            address.setLon(mAddress.getLon());

            listener.onNextButtonClick(view, address);
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
            onNextButtonEnable();
        } else {
            onNextButtonDisable();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    private void onNextButtonEnable() {
        nextButtonCell.title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
        nextButtonCell.btnOriginal.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
        nextButtonCell.btnOriginal.setBorderWidth((float)0);
        nextButtonCell.btnOriginal.setOnClickListener(this);
    }

    private void onNextButtonDisable() {
        nextButtonCell.title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey2));
        nextButtonCell.btnOriginal.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGrey4));
        nextButtonCell.btnOriginal.setBorderWidth((float) 0);
    }
}
