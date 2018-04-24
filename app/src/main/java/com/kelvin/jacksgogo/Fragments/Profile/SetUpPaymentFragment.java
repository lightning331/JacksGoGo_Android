package com.kelvin.jacksgogo.Fragments.Profile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Profile.SignUpPhoneActivity;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.R;

public class SetUpPaymentFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private TextView btnPayment;

    private AlertDialog alertDialog;

    public SetUpPaymentFragment() {
        // Required empty public constructor
    }

    public static SetUpPaymentFragment newInstance(String param1, String param2) {
        SetUpPaymentFragment fragment = new SetUpPaymentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_set_up_payment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        btnPayment = view.findViewById(R.id.btn_payment);
        btnPayment.setOnClickListener(this);
    }

    private void onShowPaymentSetUpSuccess() {
        JGGAlertView builder = new JGGAlertView(mContext,
                mContext.getResources().getString(R.string.alert_success_title),
                mContext.getResources().getString(R.string.alert_payment_method_desc),
                false,
                "",
                R.color.JGGOrange,
                R.color.JGGOrange10Percent,
                mContext.getResources().getString(R.string.alert_ok),
                R.color.JGGOrange);
        alertDialog = builder.create();
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else {
                    alertDialog.dismiss();

                }
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_payment) {
            onShowPaymentSetUpSuccess();
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
