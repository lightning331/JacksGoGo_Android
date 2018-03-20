package com.kelvin.jacksgogo.Adapter.Jobs;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment.AppFilterOptionCell;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Edit.EditJobAddressCell;
import com.kelvin.jacksgogo.CustomView.Views.SectionTitleView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGQuotationModel;
import com.kelvin.jacksgogo.Utils.Models.System.JGGAddressModel;

/**
 * Created by PUMA on 11/10/2017.
 */

public class PostQuotationAddressAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, TextWatcher {

    private Context mContext;
    private int ITEM_COUNT = 7;

    private JGGQuotationModel mQuotation;
    private String strUnit;
    private String strStreet;
    private String strPostCode;

    AppFilterOptionCell nextButtonCell;
    EditJobAddressCell placeNameCell;
    EditJobAddressCell unitCell;
    EditJobAddressCell streetCell;
    EditJobAddressCell postcodeCell;

    public PostQuotationAddressAdapter(Context context, JGGQuotationModel data) {
        this.mContext = context;
        this.mQuotation = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                View sectionTitle1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
                SectionTitleView sectionTitleView = new SectionTitleView(sectionTitle1);
                sectionTitleView.txtTitle.setText(R.string.edit_job_address_title);
                sectionTitleView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
                return sectionTitleView;
            case 1:
                View descTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
                SectionTitleView descTitleViewHolder = new SectionTitleView(descTitleView);
                descTitleViewHolder.txtTitle.setText(R.string.edit_job_address_desc);
                return descTitleViewHolder;
            case 2:
                View placeNameView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_edit_job_address, parent, false);
                placeNameCell = new EditJobAddressCell(placeNameView);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 35);
                LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 65);
                placeNameCell.titleLayout.setLayoutParams(params);
                placeNameCell.txtLayout.setLayoutParams(param1);

                placeNameCell.hint.setHint(R.string.address_place_name_title);
                placeNameCell.title.setHint("(optional)");
                placeNameCell.title.addTextChangedListener(this);
                return placeNameCell;
            case 3:
                View unitTextView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_edit_job_address, parent, false);
                unitCell = new EditJobAddressCell(unitTextView);
                unitCell.hint.setHint(R.string.address_unit_title);
                if (mQuotation.getAddress().getUnit() == null) {
                    unitCell.title.setHint("e.g.2");
                } else {
                    unitCell.title.setText(mQuotation.getAddress().getUnit());
                    strUnit = mQuotation.getAddress().getUnit();
                }
                unitCell.title.addTextChangedListener(this);
                return unitCell;
            case 4:
                View streetTextView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_edit_job_address, parent, false);
                streetCell = new EditJobAddressCell(streetTextView);
                streetCell.hint.setHint(R.string.address_street_title);
                if (mQuotation.getAddress().getStreet() == null) {
                    if (mQuotation.getAddress().getAddress() == null) {
                        streetCell.title.setHint("e.g. Jurong West Avenue 5");
                    } else
                        strStreet = mQuotation.getAddress().getAddress();
                } else
                    strStreet = mQuotation.getAddress().getStreet();
                streetCell.title.setText(strStreet);
                streetCell.title.addTextChangedListener(this);
                return streetCell;
            case 5:
                View takePhotoView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_edit_job_address, parent, false);
                postcodeCell = new EditJobAddressCell(takePhotoView);
                postcodeCell.hint.setHint(R.string.address_postcode_title);
                if (mQuotation.getAddress().getPostalCode() == null) {
                    postcodeCell.title.setHint("e.g. 34534");
                } else {
                    postcodeCell.title.setText(mQuotation.getAddress().getPostalCode());
                    strPostCode = mQuotation.getAddress().getPostalCode();
                }
                postcodeCell.title.addTextChangedListener(this);
                postcodeCell.title.setImeOptions(EditorInfo.IME_ACTION_DONE);
                return postcodeCell;
            case 6:
                View originalView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_app_filter_option, parent, false);
                nextButtonCell = new AppFilterOptionCell(originalView);
                nextButtonCell.title.setText("Next");
                if (mQuotation.getAddress().getUnit() == null
                        && mQuotation.getAddress().getStreet() == null
                        && mQuotation.getAddress().getPostalCode() == null)
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
        void onNextButtonClick(JGGAddressModel address);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.view_filter_bg) {
            JGGAddressModel address = new JGGAddressModel();
            address.setPlaceName(placeNameCell.title.getText().toString());
            address.setUnit(strUnit);
            address.setStreet(strStreet);
            address.setPostalCode(strPostCode);

            listener.onNextButtonClick(address);
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
