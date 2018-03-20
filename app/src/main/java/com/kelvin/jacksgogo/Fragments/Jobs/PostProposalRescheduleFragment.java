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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedProposal;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.convertJobTimeString;
import static com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel.getDays;
import static com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel.getHours;
import static com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel.getMinutes;
import static com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel.getSeconds;

public class PostProposalRescheduleFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private TextView lblJobType;
    private TextView lblJobTime;
    private TextView lblPolicy;
    private TextView btnNoAllowed;
    private TextView btnAllowed;
    private LinearLayout allowedLayout;
    private EditText txtDay;
    private EditText txtHour;
    private EditText txtMinute;
    private TextView lblTerms;
    private EditText txtTerms;
    private TextView btnNext;

    private JGGAppointmentModel mJob;
    private JGGProposalModel mProposal;
    private boolean allowed;
    private boolean noAllowed;
    private boolean isRescheduling;     // true: Rescheduling, false: Cancellation

    public PostProposalRescheduleFragment() {
        // Required empty public constructor
    }

    public static PostProposalRescheduleFragment newInstance() {
        PostProposalRescheduleFragment fragment = new PostProposalRescheduleFragment();
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
        View view = inflater.inflate(R.layout.fragment_post_proposal_reschedule, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        lblJobType = view.findViewById(R.id.lbl_job_type);
        lblJobTime = view.findViewById(R.id.lbl_job_time);
        lblPolicy = view.findViewById(R.id.lbl_policy);
        btnNoAllowed = view.findViewById(R.id.btn_no_allowed);      btnNoAllowed.setOnClickListener(this);
        btnAllowed = view.findViewById(R.id.btn_allowed_schedule);      btnAllowed.setOnClickListener(this);
        allowedLayout = view.findViewById(R.id.allowed_reschedule_layout);
        txtDay = view.findViewById(R.id.txt_reschedule_day);        txtDay.addTextChangedListener(this);
        txtHour = view.findViewById(R.id.txt_txt_reschedule_hours);        txtHour.addTextChangedListener(this);
        txtMinute = view.findViewById(R.id.txt_reschedule_minutes);        txtMinute.addTextChangedListener(this);
        lblTerms = view.findViewById(R.id.lbl_terms);
        txtTerms = view.findViewById(R.id.txt_terms);        txtTerms.addTextChangedListener(this);
        btnNext = view.findViewById(R.id.btn_post_proposal_next);

        if (selectedAppointment != null) {
            mJob = selectedAppointment;
            if (mJob.getBudget() != null)
                lblJobType.setText("Package Job");
            else
                lblJobType.setText("One Time Job");
            lblJobTime.setText(convertJobTimeString(mJob));
        }
        mProposal = selectedProposal;

        if (isRescheduling) {
            if (mProposal.isRescheduleAllowed() == null) {
                allowed = false;
                noAllowed = false;
            } else if (mProposal.isRescheduleAllowed()) {
                allowed = true;
                noAllowed = false;
                onShowAllowedLayout();
            } else {
                noAllowed = true;
                allowed = false;
            }
        } else {
            lblPolicy.setText("Cancellation Policy (Optional)");
            btnNoAllowed.setText("No cancellation allowed.");
            btnAllowed.setText("Allowed, cancel before...");
            lblTerms.setText("If you have any special terms for cancellation, state below (Optional)");
            btnNext.setText("Go To Summary");
            if (mProposal.isCancellationAllowed() == null) {
                allowed = false;
                noAllowed = false;
            } else if (mProposal.isCancellationAllowed()) {
                allowed = true;
                noAllowed = false;
                onShowAllowedLayout();
            } else {
                noAllowed = true;
                allowed = false;
            }
        }
        updateView();
    }

    private void updateView() {
        onCyanButtonColor(btnNoAllowed);
        onCyanButtonColor(btnAllowed);
        btnNext.setVisibility(View.GONE);
        onNextButtonDisable();
        btnAllowed.setVisibility(View.VISIBLE);
        btnNoAllowed.setVisibility(View.VISIBLE);
        allowedLayout.setVisibility(View.GONE);
        if (noAllowed) {
            btnAllowed.setVisibility(View.GONE);
            btnNext.setVisibility(View.VISIBLE);
            onYellowButtonColor(btnNoAllowed);
            onNextButtonEnable();
        } else if (allowed) {
            btnNoAllowed.setVisibility(View.GONE);
            allowedLayout.setVisibility(View.VISIBLE);
            onYellowButtonColor(btnAllowed);
            btnNext.setVisibility(View.VISIBLE);
            if (txtDay.getText().toString().length() > 0
                    && txtHour.getText().length() > 0
                    && txtMinute.getText().length() >0)
                onNextButtonEnable();
        }
    }

    private void onShowAllowedLayout() {
        if (isRescheduling) {
            txtDay.setText(getDays(mProposal.getRescheduleDate()));
            txtHour.setText(getHours(mProposal.getRescheduleDate()));
            txtMinute.setText(getMinutes(mProposal.getRescheduleDate()));
            txtTerms.setText(mProposal.getRescheduleNote());
        } else {
            txtDay.setText(getDays(mProposal.getCancellationDate()));
            txtHour.setText(getHours(mProposal.getCancellationDate()));
            txtMinute.setText(getMinutes(mProposal.getCancellationDate()));
            txtTerms.setText(mProposal.getCancellationNote());
        }
    }

    private void setProposal() {
        if (isRescheduling) {
            if (noAllowed) {
                mProposal.setRescheduleAllowed(false);
                mProposal.setRescheduleDate(0);
            } else {
                mProposal.setRescheduleAllowed(true);
                // Allowed Rescheduling Date
                mProposal.setRescheduleDate(getSeconds(txtDay.getText().toString(), txtHour.getText().toString(), txtMinute.getText().toString()));
            }
            mProposal.setRescheduleNote(txtTerms.getText().toString());
        } else {
            if (noAllowed) {
                mProposal.setCancellationAllowed(false);
                mProposal.setCancellationDate(0);
            } else {
                mProposal.setCancellationAllowed(true);
                // Allowed Cancellation Date
                mProposal.setCancellationDate(getSeconds(txtDay.getText().toString(), txtHour.getText().toString(), txtMinute.getText().toString()));
            }
            mProposal.setCancellationNote(txtTerms.getText().toString());
        }
        mProposal.setCancellationNote(txtTerms.getText().toString());
        selectedProposal = mProposal;
    }

    private void onYellowButtonColor(TextView button) {
        button.setBackgroundResource(R.drawable.yellow_background);
        button.setTextColor(ContextCompat.getColor(mContext, R.color.JGGBlack));
    }

    private void onCyanButtonColor(TextView button) {
        button.setBackgroundResource(R.drawable.cyan_border_background);
        button.setTextColor(ContextCompat.getColor(mContext, R.color.JGGCyan));
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

    public void setRescheduling(boolean rescheduling) {
        this.isRescheduling = rescheduling;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_no_allowed) {
            noAllowed = !noAllowed;
        } else if (view.getId() == R.id.btn_allowed_schedule) {
            allowed = !allowed;
        } else if (view.getId() == R.id.btn_post_proposal_next) {
            setProposal();
            listener.onNextButtonClick();
            return;
        }
        updateView();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (txtDay.getText().toString().length() > 0
                && txtHour.getText().toString().length() > 0
                && txtMinute.getText().toString().length() > 0) {
            onNextButtonEnable();
        } else {
            onNextButtonDisable();
        }
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
