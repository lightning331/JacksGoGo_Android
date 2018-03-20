package com.kelvin.jacksgogo.CustomView.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.AppointmentType;

/**
 * Created by PUMA on 1/20/2018.
 */

public class SelectAreaDialog extends android.app.AlertDialog.Builder implements View.OnClickListener {

    private Context mContext;

    private ImageView btnAreaClose;
    private ImageView btnCBD;
    private ImageView btnCentral;
    private ImageView btnEunos;
    private ImageView btnOrchard;
    private ImageView btnNewton;
    private ImageView btnToa;

    private AppointmentType mType;
    private int checkButtonImage;
    private int closeButtonImage;
    private boolean cbdSelected = true;
    private boolean centralSelected = true;
    private boolean eunosSelected = true;
    private boolean orchardSelected = true;
    private boolean newtonSelected = true;
    private boolean toaSelected = true;

    public SelectAreaDialog(Context context, AppointmentType type) {
        super(context);
        mContext = context;
        mType = type;
        init();
    }

    private void init() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_select_area, null);

        btnAreaClose = view.findViewById(R.id.btn_area_close);
        btnCBD = view.findViewById(R.id.btn_area_cbd);
        btnCentral = view.findViewById(R.id.btn_area_central);
        btnEunos = view.findViewById(R.id.btn_area_eunos);
        btnOrchard = view.findViewById(R.id.btn_area_orchard);
        btnNewton = view.findViewById(R.id.btn_area_newton);
        btnToa = view.findViewById(R.id.btn_area_toa);

        switch (mType) {
            case SERVICES:
                checkButtonImage = R.mipmap.checkbox_on_green;
                closeButtonImage = R.mipmap.button_tick_area_round_green;
                break;
            case JOBS:
                checkButtonImage = R.mipmap.checkbox_on_blue;
                closeButtonImage = R.mipmap.button_tick_area_round_cyan;
                break;
            case GOCLUB:
                checkButtonImage = R.mipmap.checkbox_on_purple;
                closeButtonImage = R.mipmap.button_tick_area_round_purple;
                break;
        }

        btnAreaClose.setImageResource(closeButtonImage);
        btnCBD.setImageResource(checkButtonImage);
        btnCentral.setImageResource(checkButtonImage);
        btnEunos.setImageResource(checkButtonImage);
        btnOrchard.setImageResource(checkButtonImage);
        btnNewton.setImageResource(checkButtonImage);
        btnToa.setImageResource(checkButtonImage);

        btnAreaClose.setOnClickListener(this);
        btnCBD.setOnClickListener(this);
        btnCentral.setOnClickListener(this);
        btnEunos.setOnClickListener(this);
        btnOrchard.setOnClickListener(this);
        btnNewton.setOnClickListener(this);
        btnToa.setOnClickListener(this);

        this.setView(view);
    }

    private void setOnCheck(ImageView button, boolean selected) {
        if (selected) button.setImageResource(checkButtonImage);
        else button.setImageResource(R.mipmap.checkbox_off);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_area_cbd) {
            cbdSelected = !cbdSelected;
            setOnCheck(btnCBD, cbdSelected);
        } else if (view.getId() == R.id.btn_area_central) {
            centralSelected = !centralSelected;
            setOnCheck(btnCentral, centralSelected);
        } else if (view.getId() == R.id.btn_area_eunos) {
            eunosSelected = !eunosSelected;
            setOnCheck(btnEunos, eunosSelected);
        } else if (view.getId() == R.id.btn_area_orchard) {
            orchardSelected = !orchardSelected;
            setOnCheck(btnOrchard, orchardSelected);
        } else if (view.getId() == R.id.btn_area_newton) {
            newtonSelected = !newtonSelected;
            setOnCheck(btnNewton, newtonSelected);
        } else if (view.getId() == R.id.btn_area_toa) {
            toaSelected = !toaSelected;
            setOnCheck(btnToa, toaSelected);
        }
    }
}
