package com.kelvin.jacksgogo.Fragments.Jobs;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.Jobs.PostProposalActivity;
import com.kelvin.jacksgogo.Activities.Jobs.PostedJobActivity;
import com.kelvin.jacksgogo.CustomView.Views.PostJobTabbarView;
import com.kelvin.jacksgogo.CustomView.Views.PostProposalTabbarView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.Global;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGPostJobResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGPostProposalResponse;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedCategory;
import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedProposal;
import static com.kelvin.jacksgogo.Utils.Global.convertJobTimeString;

public class PostProposalSummaryFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private ImageView imgCategory;
    private TextView lblCategory;
    private TextView lblTime;
    private TextView lblDesc;
    private TextView lblBudgetType;
    private TextView lblBidBudget;
    private TextView lblSupplies;
    private TextView lblRescheduling;
    private TextView lblCancellation;
    private LinearLayout btnDesc;
    private LinearLayout btnBudget;
    private LinearLayout btnRescheduling;
    private LinearLayout btnCancellation;
    private TextView btnSubmit;

    private JGGProposalModel mProposal;
    private ProposalStatus proposalStatus;
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    private String postedProposalID;

    private PostProposalMainTabFragment fragment;
    private PostProposalActivity mActivity;

    public enum ProposalStatus {
        POST,
        EDIT
    }

    public void setEditStatus(ProposalStatus status) {
        this.proposalStatus = status;
    }

    public PostProposalSummaryFragment() {
        // Required empty public constructor
    }

    public static PostProposalSummaryFragment newInstance() {
        PostProposalSummaryFragment fragment = new PostProposalSummaryFragment();
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
        View view = inflater.inflate(R.layout.fragment_post_proposal_summary, container, false);
        mProposal = selectedProposal;

        initView(view);
        setData();
        return view;
    }

    private void initView(View view) {
        imgCategory = (ImageView) view.findViewById(R.id.img_category);
        lblCategory = (TextView) view.findViewById(R.id.lbl_category_name);
        lblTime = (TextView) view.findViewById(R.id.lbl_date);

        lblDesc = view.findViewById(R.id.lbl_proposal_desc);
        lblBudgetType = view.findViewById(R.id.lbl_budget_type);
        lblBidBudget = view.findViewById(R.id.lbl_bid_budget);
        lblSupplies = view.findViewById(R.id.lbl_supplies);
        lblRescheduling = view.findViewById(R.id.lbl_proposal_rescheduling);
        lblCancellation = view.findViewById(R.id.lbl_proposal_cancellation);
        btnDesc = view.findViewById(R.id.btn_proposal_describe);
        btnBudget = view.findViewById(R.id.btn_proposal_price);
        btnRescheduling = view.findViewById(R.id.btn_proposal_rescheduling);
        btnCancellation = view.findViewById(R.id.btn_proposal_cancellation);
        btnSubmit = view.findViewById(R.id.btn_post_proposal);

        btnDesc.setOnClickListener(this);
        btnBudget.setOnClickListener(this);
        btnRescheduling.setOnClickListener(this);
        btnCancellation.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    private void setData() {

        Picasso.with(mContext)
                .load(selectedCategory.getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(selectedCategory.getName());
        lblTime.setText(convertJobTimeString(selectedAppointment));

        if (mProposal != null) {
            // Description
            lblDesc.setText(mProposal.getDescription());
            // Budget Type
            //lblBudgetType.setText();
            // Budget
            lblBidBudget.setText(String.valueOf(mProposal.getBudget()) + "/month");
            // Supplies
            lblSupplies.setText("Our own supplies - $ ");
            // Rescheduling
            //lblRescheduling.setText();
            // Cancellation
            //lblCancellation.setText();
        } else {
            lblDesc.setText("");
            lblBudgetType.setText("No set");
            lblBidBudget.setText("No set");
            lblSupplies.setText("No set");
            lblRescheduling.setText("No set");
            lblCancellation.setText("No set");
        }
    }

    private void onPostProposal() {
        progressDialog = Global.createProgressDialog(mContext);
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGPostProposalResponse> call = manager.postNewProposal(mProposal);
        call.enqueue(new Callback<JGGPostProposalResponse>() {
            @Override
            public void onResponse(Call<JGGPostProposalResponse> call, Response<JGGPostProposalResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        postedProposalID = response.body().getValue();
                        showPostProposalAlertDialog(false);
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGPostProposalResponse> call, Throwable t) {
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void onEditProposal() {
        progressDialog = Global.createProgressDialog(mContext);
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        Call<JGGPostProposalResponse> call = manager.editProposal(mProposal);
        call.enqueue(new Callback<JGGPostProposalResponse>() {
            @Override
            public void onResponse(Call<JGGPostProposalResponse> call, Response<JGGPostProposalResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    postedProposalID = response.body().getValue();
                    showPostProposalAlertDialog(true);
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGPostProposalResponse> call, Throwable t) {
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void showPostProposalAlertDialog(final boolean isEdit) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        LayoutInflater inflater = this.getLayoutInflater();

        View alertView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();

        TextView cancelButton = (TextView) alertView.findViewById(R.id.btn_alert_cancel);
        TextView okButton = (TextView) alertView.findViewById(R.id.btn_alert_ok);
        TextView title = (TextView) alertView.findViewById(R.id.lbl_alert_titile);
        TextView desc = (TextView) alertView.findViewById(R.id.lbl_alert_description);

        title.setText(R.string.alert_proposal_posted_title);
        desc.setText("Proposal reference no: " + postedProposalID + '\n' +  '\n' + "Good luck!");
        okButton.setText(R.string.alert_view_proposal);
        okButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGCyan));
        cancelButton.setVisibility(View.GONE);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                if (isEdit) {
                    getActivity().onBackPressed();
                } else {
                    //getActivity().finish();
                    Intent intent = new Intent(mContext, PostedJobActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });

        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_post_proposal) {
            switch (proposalStatus) {
                case POST:
                    //showPostProposalAlertDialog(false);
                    onPostProposal();
                    break;
                case EDIT:
                    onEditProposal();
                    break;
                default:
                    break;
            }
            return;
        } else if (view.getId() == R.id.btn_proposal_describe) {
            fragment = PostProposalMainTabFragment.newInstance(PostProposalTabbarView.TabName.DESCRIBE, ProposalStatus.POST);
            if (proposalStatus == ProposalStatus.EDIT) {
                fragment = PostProposalMainTabFragment.newInstance(PostProposalTabbarView.TabName.DESCRIBE, ProposalStatus.EDIT);
            }
        } else if (view.getId() == R.id.btn_proposal_price) {
            fragment = PostProposalMainTabFragment.newInstance(PostProposalTabbarView.TabName.BID, ProposalStatus.POST);
            if (proposalStatus == ProposalStatus.EDIT) {
                fragment = PostProposalMainTabFragment.newInstance(PostProposalTabbarView.TabName.BID, ProposalStatus.EDIT);
            }
        } else if (view.getId() == R.id.btn_proposal_rescheduling) {
            fragment = PostProposalMainTabFragment.newInstance(PostProposalTabbarView.TabName.RESCHEDULING, ProposalStatus.POST);
            if (proposalStatus == ProposalStatus.EDIT) {
                fragment = PostProposalMainTabFragment.newInstance(PostProposalTabbarView.TabName.RESCHEDULING, ProposalStatus.EDIT);
            }
        }  else if (view.getId() == R.id.btn_proposal_cancellation) {
            fragment = PostProposalMainTabFragment.newInstance(PostProposalTabbarView.TabName.CANCELLATION, ProposalStatus.POST);
            if (proposalStatus == ProposalStatus.EDIT) {
                fragment = PostProposalMainTabFragment.newInstance(PostProposalTabbarView.TabName.CANCELLATION, ProposalStatus.EDIT);
            }
        }
        mActivity.getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.post_proposal_container, fragment)
                .commit();
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
        mActivity = ((PostProposalActivity) mContext);
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
