package com.kelvin.jacksgogo.Fragments.Jobs;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kelvin.jacksgogo.Activities.Jobs.PostProposalActivity;
import com.kelvin.jacksgogo.CustomView.Views.JGGAlertView;
import com.kelvin.jacksgogo.CustomView.Views.PostProposalTabView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAPIManager;
import com.kelvin.jacksgogo.Utils.API.JGGURLManager;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel;
import com.kelvin.jacksgogo.Utils.Responses.JGGBaseResponse;
import com.kelvin.jacksgogo.Utils.Responses.JGGPostAppResponse;
import com.squareup.picasso.Picasso;

import java.util.Date;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.kelvin.jacksgogo.Utils.Global.POST;
import static com.kelvin.jacksgogo.Utils.Global.createProgressDialog;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.appointmentNewDate;
import static com.kelvin.jacksgogo.Utils.JGGTimeManager.getAppointmentTime;
import static com.kelvin.jacksgogo.Utils.Models.Proposal.JGGProposalModel.getDaysString;

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
    private TextView btnDelete;

    private JGGAppointmentModel selectedAppointment;
    private ProposalStatus proposalStatus;
    private AlertDialog alertDialog;
    private ProgressDialog progressDialog;
    private String postedProposalID;
    private PostProposalMainTabFragment fragment;
    private PostProposalActivity mActivity;
    public enum ProposalStatus {
        POST,
        EDIT,
        INVITE
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
        ButterKnife.bind(mActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_proposal_summary, container, false);

        selectedAppointment = JGGAppManager.getInstance().getSelectedAppointment();

        String postTime = appointmentNewDate(new Date());

        JGGProposalModel proposalModel = JGGAppManager.getInstance().getSelectedProposal();
        proposalModel.setPostOn(postTime);
        JGGAppManager.getInstance().setSelectedProposal(proposalModel);

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
        btnDelete = view.findViewById(R.id.btn_delete_proposal);

        btnDesc.setOnClickListener(this);
        btnBudget.setOnClickListener(this);
        btnRescheduling.setOnClickListener(this);
        btnCancellation.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        if (proposalStatus == ProposalStatus.POST) {
            btnDelete.setVisibility(View.GONE);
            btnSubmit.setVisibility(View.VISIBLE);
        } else if (proposalStatus == ProposalStatus.EDIT) {
            mActivity.setEdit(false);
            btnDelete.setVisibility(View.VISIBLE);
            btnSubmit.setVisibility(View.GONE);
        }
    }

    private void setData() {

        Picasso.with(mContext)
                .load(selectedAppointment.getCategory().getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(selectedAppointment.getCategory().getName());
        lblTime.setText(getAppointmentTime(selectedAppointment));

        JGGProposalModel selectedProposal = JGGAppManager.getInstance().getSelectedProposal();
        if (selectedProposal != null) {
            // Description
            lblDesc.setText(selectedProposal.getDescription());
            // Budget Type
            //lblBudgetType.setText();
            // Budget
            lblBidBudget.setText(String.valueOf(selectedProposal.getBudget()) + "/month");
            // Supplies
            //lblSupplies.setText("Our own supplies - $ ");
            // Rescheduling
            if (selectedProposal.isRescheduleAllowed())
                lblRescheduling.setText(getDaysString(Long.valueOf(selectedProposal.getRescheduleTime())));
            else
                lblRescheduling.setText("No rescheduling allowed.");
            // Cancellation
            if (selectedProposal.isCancellationAllowed())
                lblCancellation.setText(getDaysString(Long.valueOf(selectedProposal.getCancellationTime())));
            else
                lblCancellation.setText("No cancellation allowed.");
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
        progressDialog = createProgressDialog(mContext);
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, mContext);

        JGGProposalModel proposalModel = JGGAppManager.getInstance().getSelectedProposal();
        Call<JGGPostAppResponse> call = manager.postNewProposal(proposalModel);
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        postedProposalID = response.body().getValue();

                        JGGProposalModel model = JGGAppManager.getInstance().getSelectedProposal();
                        model.setID(postedProposalID);
                        JGGAppManager.getInstance().setSelectedProposal(model);

                        showPostProposalAlertDialog();
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGPostAppResponse> call, Throwable t) {
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    // TODO - invite/edit proposal
    public void onEditProposal() {
        //showPostProposalAlertDialog();
        progressDialog = createProgressDialog(mContext);
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        JGGProposalModel proposalModel = JGGAppManager.getInstance().getSelectedProposal();

        Call<JGGPostAppResponse> call = manager.editProposal(proposalModel);
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        postedProposalID = response.body().getValue();

                        JGGProposalModel model = JGGAppManager.getInstance().getSelectedProposal();
                        model.setID(postedProposalID);
                        JGGAppManager.getInstance().setSelectedProposal(model);
                        showPostProposalAlertDialog();
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGPostAppResponse> call, Throwable t) {
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void onAcceptInvite() {
        JGGProposalModel proposalModel = JGGAppManager.getInstance().getSelectedProposal();
        progressDialog = createProgressDialog(mContext);
        JGGAPIManager manager = JGGURLManager.createService(JGGAPIManager.class, mContext);

        Call<JGGPostAppResponse> call = manager.acceptInvite(proposalModel);
        call.enqueue(new Callback<JGGPostAppResponse>() {
            @Override
            public void onResponse(Call<JGGPostAppResponse> call, Response<JGGPostAppResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        postedProposalID = response.body().getValue();

                        JGGProposalModel model = JGGAppManager.getInstance().getSelectedProposal();
                        model.setID(postedProposalID);
                        JGGAppManager.getInstance().setSelectedProposal(model);
                        showPostProposalAlertDialog();
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGPostAppResponse> call, Throwable t) {
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void onDeleteProposal() {
        progressDialog = createProgressDialog(mContext);

        JGGAPIManager apiManager = JGGURLManager.createService(JGGAPIManager.class, mContext);
        String proposalID = JGGAppManager.getInstance().getSelectedProposal().getID();
        Call<JGGBaseResponse> call = apiManager.deleteProposal(proposalID);
        call.enqueue(new Callback<JGGBaseResponse>() {
            @Override
            public void onResponse(Call<JGGBaseResponse> call, Response<JGGBaseResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getSuccess()) {
                        mActivity.finish();
                    } else {
                        Toast.makeText(mContext, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    int statusCode  = response.code();
                    Toast.makeText(mContext, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JGGBaseResponse> call, Throwable t) {
                Log.d("SignUpPhoneActivity", t.getMessage());
                Toast.makeText(mContext, "Request time out!", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void showPostProposalAlertDialog() {
        JGGAlertView builder = new JGGAlertView(mContext,
                mContext.getResources().getString(R.string.alert_proposal_posted_title),
                "Proposal reference no: " + postedProposalID + '\n' +  '\n' + "Good luck!",
                false,
                "",
                0,
                0,
                mContext.getResources().getString(R.string.alert_view_proposal),
                R.color.JGGCyan);
        alertDialog = builder.create();
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else {
                    alertDialog.dismiss();

                    mActivity.setEdit(true);
                    mActivity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.post_proposal_container, PostedProposalFragment.newInstance(POST))
                            .commit();
                }
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void showDeleteAlertDialog() {
        JGGAlertView builder = new JGGAlertView(mContext,
                mContext.getResources().getString(R.string.alert_delete_proposal_title),
                mContext.getResources().getString(R.string.alert_delete_proposal_desc),
                false,
                mContext.getResources().getString(R.string.alert_cancel),
                R.color.JGGCyan,
                R.color.JGGCyan10Percent,
                mContext.getResources().getString(R.string.menu_option_delete),
                R.color.JGGRed);
        alertDialog = builder.create();
        builder.setOnItemClickListener(new JGGAlertView.OnItemClickListener() {
            @Override
            public void onDoneButtonClick(View view) {
                if (view.getId() == R.id.btn_alert_cancel)
                    alertDialog.dismiss();
                else {
                    alertDialog.dismiss();
                    onDeleteProposal();
                }
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_post_proposal) {
            //showPostProposalAlertDialog();
            switch (proposalStatus) {
                case POST:
                    onPostProposal();
                    return;
                case EDIT:
                    onEditProposal();
                    return;
                case INVITE:
                    onAcceptInvite();
                    return;
            }
        } else if (view.getId() == R.id.btn_delete_proposal) {
            showDeleteAlertDialog();
            return;
        } else if (view.getId() == R.id.btn_proposal_describe) {
            fragment = PostProposalMainTabFragment.newInstance(PostProposalTabView.TabName.DESCRIBE, ProposalStatus.POST);
            if (proposalStatus == ProposalStatus.EDIT) {
                fragment = PostProposalMainTabFragment.newInstance(PostProposalTabView.TabName.DESCRIBE, ProposalStatus.EDIT);
            }
        } else if (view.getId() == R.id.btn_proposal_price) {
            fragment = PostProposalMainTabFragment.newInstance(PostProposalTabView.TabName.BID, ProposalStatus.POST);
            if (proposalStatus == ProposalStatus.EDIT) {
                fragment = PostProposalMainTabFragment.newInstance(PostProposalTabView.TabName.BID, ProposalStatus.EDIT);
            }
        } else if (view.getId() == R.id.btn_proposal_rescheduling) {
            fragment = PostProposalMainTabFragment.newInstance(PostProposalTabView.TabName.RESCHEDULING, ProposalStatus.POST);
            if (proposalStatus == ProposalStatus.EDIT) {
                fragment = PostProposalMainTabFragment.newInstance(PostProposalTabView.TabName.RESCHEDULING, ProposalStatus.EDIT);
            }
        }  else if (view.getId() == R.id.btn_proposal_cancellation) {
            fragment = PostProposalMainTabFragment.newInstance(PostProposalTabView.TabName.CANCELLATION, ProposalStatus.POST);
            if (proposalStatus == ProposalStatus.EDIT) {
                fragment = PostProposalMainTabFragment.newInstance(PostProposalTabView.TabName.CANCELLATION, ProposalStatus.EDIT);
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
