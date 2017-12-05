package com.kelvin.jacksgogo.Adapter.Service;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

public class PostServiceDescribeAdapter extends Fragment implements View.OnClickListener, TextWatcher {

    private OnFragmentInteractionListener mListener;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onNextButtonClick(String title, String comment, String tags);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private Context mContext;

    private EditText txtServiceTitle;
    private EditText txtServiceDesc;
    private EditText txtServiceTag;
    private LinearLayout btnTakePhoto;
    private LinearLayout btnNext;
    private TextView lblNext;
    private String strTitle;
    private String strDescription;
    private String strTags;

    public PostServiceDescribeAdapter() {
        // Required empty public constructor
    }

    public static PostServiceDescribeAdapter newInstance(String param1, String param2) {
        PostServiceDescribeAdapter fragment = new PostServiceDescribeAdapter();
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
        View view = inflater.inflate(R.layout.fragment_post_service_describe, container, false);

        initiView(view);

        return view;
    }

    private void initiView(View view) {

        txtServiceTitle = view.findViewById(R.id.txt_post_service_title);
        txtServiceTitle.addTextChangedListener(this);
        txtServiceDesc = view.findViewById(R.id.txt_post_service_description);
        txtServiceDesc.addTextChangedListener(this);
        txtServiceTag = view.findViewById(R.id.txt_post_service_tag);
        txtServiceTag.addTextChangedListener(this);
        btnTakePhoto = view.findViewById(R.id.btn_post_service_take_photo);
        btnTakePhoto.setOnClickListener(this);
        btnNext = view.findViewById(R.id.btn_post_service_next);
        lblNext = view.findViewById(R.id.lbl_post_service_next);
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_post_service_next) {
            listener.onNextButtonClick(strTitle, strDescription, strTags);
        } else if (view.getId() == R.id.btn_post_service_take_photo) {

        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (txtServiceTitle.length() > 0
                && txtServiceDesc.length() > 0) {

            strTitle = txtServiceTitle.getText().toString();
            strDescription = txtServiceDesc.getText().toString();
            strTags = txtServiceTag.getText().toString();

            lblNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
            btnNext.setBackgroundResource(R.drawable.green_background);
            btnNext.setOnClickListener(this);
        } else {
            lblNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey2));
            btnNext.setBackgroundResource(R.drawable.grey_background);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
