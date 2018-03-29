package com.kelvin.jacksgogo.Fragments.Jobs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.Activities.Jobs.ProgressJobSummaryActivity;
import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.ReviewCell;
import com.kelvin.jacksgogo.CustomView.Views.JGGActionbarView;
import com.kelvin.jacksgogo.R;

public class JobReviewFragment extends Fragment {

    private Context mContext;
    private OnFragmentInteractionListener mListener;

    public JobReviewFragment() {
        // Required empty public constructor
    }

    public static JobReviewFragment newInstance(String param1, String param2) {
        JobReviewFragment fragment = new JobReviewFragment();
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
        View view = inflater.inflate(R.layout.fragment_job_review, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {

        LinearLayout givenReviewLayout = (LinearLayout)view.findViewById(R.id.job_given_review_layout);
        ReviewCell givenReviewView = new ReviewCell(mContext);
        givenReviewView.ratingBar.setRating((float)5.0);
        givenReviewView.lblDate.setText("By you on 25 Dec, 2017");
        givenReviewView.avatar.setImageResource(R.mipmap.nurse);
        givenReviewLayout.addView(givenReviewView);

        LinearLayout getReviewLayout = (LinearLayout)view.findViewById(R.id.job_get_review_layout);
        ReviewCell getReviewView = new ReviewCell(mContext);
        getReviewView.ratingBar.setRating((float)4.6);
        getReviewView.lblDate.setText("By catherinedesilva on 25 Dec, 2017");
        getReviewView.lblDescription.setText("Client is a wonder to work with. She gives clear instructions and makes decisions fast.");
        getReviewLayout.addView(getReviewView);

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
