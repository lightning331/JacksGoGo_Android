package com.kelvin.jacksgogo.Fragments.Jobs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Jobs.InviteProviderActivity;
import com.kelvin.jacksgogo.Activities.Jobs.JobDetailActivity;
import com.kelvin.jacksgogo.Adapter.Jobs.QuotationAdapter;
import com.kelvin.jacksgogo.CustomView.JGGActionbarView;
import com.kelvin.jacksgogo.Models.Base.Global;
import com.kelvin.jacksgogo.Models.JGGBiddingProviderModel;
import com.kelvin.jacksgogo.Models.User.JGGUserBaseModel;
import com.kelvin.jacksgogo.R;

import java.util.ArrayList;

public class QuotationFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private TextView btnInvite;

    private ArrayList<JGGBiddingProviderModel> quotationArray = new ArrayList<>();

    public QuotationFragment() {
        // Required empty public constructor
    }

    public static QuotationFragment newInstance(String param1, String param2) {
        QuotationFragment fragment = new QuotationFragment();
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
        View view = inflater.inflate(R.layout.fragment_quotation, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.quotation_recycler_view);
        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        }
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        QuotationAdapter adapter = new QuotationAdapter(mContext, addDummyData());
        mRecyclerView.setAdapter(adapter);

        btnInvite = (TextView) view.findViewById(R.id.btn_invite_service_provider);
        btnInvite.setOnClickListener(this);

        return view;
    }

    private ArrayList<JGGBiddingProviderModel> addDummyData() {

        JGGBiddingProviderModel p1 = new JGGBiddingProviderModel();
        JGGUserBaseModel user1 = new JGGUserBaseModel();
        user1.setFullname("CYYong");
        user1.setAvatarUrl(R.drawable.nurse);
        user1.setRate(4.5f);
        p1.setUser(user1);
        p1.setStatus(Global.BiddingStatus.NEWPROPOSAL);
        p1.setPrice(100.00f);
        quotationArray.add(p1);

        JGGBiddingProviderModel p2 = new JGGBiddingProviderModel();
        JGGUserBaseModel user2 = new JGGUserBaseModel();
        user2.setFullname("Christina.P");
        user2.setAvatarUrl(R.drawable.nurse4);
        user2.setRate(5.0f);
        p2.setUser(user2);
        p2.setStatus(Global.BiddingStatus.PENDING);
        p2.setPrice(105.00f);
        quotationArray.add(p2);

        JGGBiddingProviderModel p3 = new JGGBiddingProviderModel();
        JGGUserBaseModel user3 = new JGGUserBaseModel();
        user3.setFullname("RenYW");
        user3.setAvatarUrl(R.drawable.carousel01);
        user3.setRate(4.0f);
        p3.setUser(user3);
        p3.setStatus(Global.BiddingStatus.PENDING);
        p3.setPrice(110.00f);
        quotationArray.add(p3);

        JGGBiddingProviderModel p4 = new JGGBiddingProviderModel();
        JGGUserBaseModel user4 = new JGGUserBaseModel();
        user4.setFullname("RositaV");
        user4.setAvatarUrl(R.drawable.carousel02);
        user4.setRate(5.0f);
        p4.setUser(user4);
        p4.setStatus(Global.BiddingStatus.NOTRESPONDED);
        quotationArray.add(p4);

        JGGBiddingProviderModel p5 = new JGGBiddingProviderModel();
        JGGUserBaseModel user5 = new JGGUserBaseModel();
        user5.setFullname("Alicaia.Leong");
        user5.setAvatarUrl(R.drawable.nurse);
        user5.setRate(3.5f);
        p5.setUser(user5);
        p5.setStatus(Global.BiddingStatus.DECLINED);
        quotationArray.add(p5);

        JGGBiddingProviderModel p6 = new JGGBiddingProviderModel();
        JGGUserBaseModel user6 = new JGGUserBaseModel();
        user6.setFullname("CYYong");
        user6.setAvatarUrl(R.drawable.nurse4);
        user6.setRate(4.75f);
        p6.setUser(user6);
        p6.setStatus(Global.BiddingStatus.REJECTED);
        p6.setPrice(90.00f);
        quotationArray.add(p6);

        return quotationArray;
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
        ((JobDetailActivity) context).setStatus(JGGActionbarView.EditStatus.NONE);
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
        if (view.getId() == R.id.btn_invite_service_provider) {
            Intent intent = new Intent(mContext, InviteProviderActivity.class);
            startActivity(intent);
        }
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
