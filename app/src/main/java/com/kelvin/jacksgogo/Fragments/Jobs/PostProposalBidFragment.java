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
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;

import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedProposal;

public class PostProposalBidFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private TextView lblBudgetType;
    private TextView lblQuotePrice;
    private TextView lblBudget;
    private EditText txtAmount;
    private EditText txtBidDesc;
    private TextView btnNext;

    private JGGAppointmentModel mJob;
    private JGGProposalModel mProposal;

    public PostProposalBidFragment() {
        // Required empty public constructor
    }

    public static PostProposalBidFragment newInstance(String param1, String param2) {
        PostProposalBidFragment fragment = new PostProposalBidFragment();
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
        View view = inflater.inflate(R.layout.fragment_post_proposal_bid, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        lblBudgetType = view.findViewById(R.id.lbl_budget_type);
        lblQuotePrice = view.findViewById(R.id.lbl_quote_price);
        lblBudget = view.findViewById(R.id.lbl_budget);
        txtAmount = view.findViewById(R.id.txt_bid_amount);
        txtAmount.addTextChangedListener(this);
        txtBidDesc = view.findViewById(R.id.txt_bid);
        txtBidDesc.addTextChangedListener(this);
        btnNext = view.findViewById(R.id.btn_post_proposal_next);

        mJob = selectedAppointment;
        if (mJob != null) {
            String budget = "";
            if (mJob.getBudget() == null && mJob.getBudgetFrom() == null)
                budget =  "Budget: No limit";
            else {
                if (mJob.getBudget() != null) {
                    lblBudgetType.setText("Fixed");
                    budget = "Budget: " + mJob.getBudget().toString() + "/month";
                } else if (mJob.getBudgetFrom() != null && mJob.getBudgetTo() != null) {
                    lblBudgetType.setText("Package");
                    budget = ("Budget: $ " + mJob.getBudgetFrom().toString()
                            + " "
                            + "$ " + mJob.getBudgetTo().toString()
                            + "/month");
                }
            }
            lblBudget.setText(budget);
        }
        mProposal = selectedProposal;
        if (mProposal.getBudget() != null) {
            txtAmount.setText(String.valueOf(selectedProposal.getBudget()));
            txtBidDesc.setText(mProposal.getBreakdown());
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
    public void onClick(View view) {
        if (view.getId() == R.id.btn_post_proposal_next) {
            String amount = txtAmount.getText().toString();
            mProposal.setBudget(Double.parseDouble(amount));
            mProposal.setBreakdown(txtBidDesc.getText().toString());
            selectedProposal = mProposal;
            listener.onNextButtonClick();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (txtAmount.length() > 0
                && txtAmount.length() > 0)
            onNextButtonEnable();
        else
            onNextButtonDisable();
    }

    @Override
    public void afterTextChanged(Editable editable) {

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
