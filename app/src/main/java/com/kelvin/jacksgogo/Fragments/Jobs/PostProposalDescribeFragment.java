package com.kelvin.jacksgogo.Fragments.Jobs;

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
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;

import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedProposal;

public class PostProposalDescribeFragment extends Fragment implements TextWatcher, View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private TextView lbljobDesc;
    private EditText txtProposal;
    private TextView btnNext;

    private JGGProposalModel proposal;

    public PostProposalDescribeFragment() {
        // Required empty public constructor
    }

    public static PostProposalDescribeFragment newInstance(String param1, String param2) {
        PostProposalDescribeFragment fragment = new PostProposalDescribeFragment();
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
        View view = inflater.inflate(R.layout.fragment_post_proposal_describe, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        lbljobDesc = view.findViewById(R.id.lbl_description);
        txtProposal = view.findViewById(R.id.txt_proposal);
        txtProposal.addTextChangedListener(this);
        btnNext = view.findViewById(R.id.btn_post_proposal_next);

        if (selectedAppointment != null)
            lbljobDesc.setText(selectedAppointment.getDescription());
        proposal = selectedProposal;
        if (proposal.getDescription() != null) {
            txtProposal.setText(proposal.getDescription());
            onNextButtonEnable();
        }
    }

    private void onNextButtonEnable() {
        btnNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
        btnNext.setBackgroundResource(R.drawable.cyan_background);
        btnNext.setOnClickListener(this);
    }

    private void onNextButtonDisable() {
        btnNext.setTextColor(ContextCompat.getColor(mContext, R.color.JGGGrey2));
        btnNext.setBackgroundResource(R.drawable.grey_background);
        btnNext.setClickable(false);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (txtProposal.length() > 0) {
            onNextButtonEnable();
        } else {
            onNextButtonDisable();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_post_proposal_next) {
            selectedProposal.setDescription(txtProposal.getText().toString());
            listener.onNextButtonClick();
        }
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onNextButtonClick();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
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
