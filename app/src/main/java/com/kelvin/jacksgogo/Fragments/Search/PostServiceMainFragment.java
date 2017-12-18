package com.kelvin.jacksgogo.Fragments.Search;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.Search.PostServiceActivity;
import com.kelvin.jacksgogo.Activities.Search.PostedServiceActivity;
import com.kelvin.jacksgogo.CustomView.Views.EditJobTabbarView;
import com.kelvin.jacksgogo.R;

import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;

public class PostServiceMainFragment extends Fragment implements View.OnClickListener {

    private Context mContext;
    private OnFragmentInteractionListener mListener;

    private LinearLayout btnDescribe;
    private LinearLayout btnPrice;
    private LinearLayout btnTimeSlot;
    private LinearLayout btnAddress;
    private LinearLayout btnPostService;
    private TextView lblDescribeTitle;
    private TextView lblDescribeDesc;
    private TextView lblPrice;
    private TextView lblAddress;
    private TagContainerLayout describeTagView;
    private AlertDialog alertDialog;

    private PostServiceDetailFragment detailFragment;

    private PostEditStatus editStatus;

    public enum PostEditStatus {
        NONE,
        EDIT,
        DUPLICATE
    }

    public PostEditStatus getEditStatus() {
        return editStatus;
    }

    public void setEditStatus(PostEditStatus editStatus) {
        this.editStatus = editStatus;
    }

    public PostServiceMainFragment() {
        // Required empty public constructor
    }

    public static PostServiceMainFragment newInstance(String param1, String param2) {
        PostServiceMainFragment fragment = new PostServiceMainFragment();
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
        View view = inflater.inflate(R.layout.fragment_post_service_main, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        btnDescribe = view.findViewById(R.id.btn_post_main_describe);
        btnPrice = view.findViewById(R.id.btn_post_main_price);
        btnTimeSlot = view.findViewById(R.id.btn_post_main_time_slot);
        btnAddress = view.findViewById(R.id.btn_post_main_address);
        describeTagView = view.findViewById(R.id.post_service_main_tag_view);
        lblDescribeTitle = view.findViewById(R.id.lbl_post_describe_title);
        lblDescribeDesc = view.findViewById(R.id.lbl_post_describe_description);
        lblPrice = view.findViewById(R.id.lbl_post_main_price);
        lblAddress = view.findViewById(R.id.lbl_post_main_address);
        btnPostService = view.findViewById(R.id.btn_post_service);
        describeTagView = view.findViewById(R.id.post_service_main_tag_view);
        setTagList();

        btnDescribe.setOnClickListener(this);
        btnPrice.setOnClickListener(this);
        btnTimeSlot.setOnClickListener(this);
        btnAddress.setOnClickListener(this);
        btnPostService.setOnClickListener(this);
    }

    private void setTagList() {
        Typeface typeface = Typeface.create("muliregular", Typeface.NORMAL);
        describeTagView.setTagTypeface(typeface);
        List<String> tags = new ArrayList<String>();
        tags.add("dog walk");
        tags.add("professional");
        describeTagView.setTags(tags);
    }

    private void showAlertDialog(View view) {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        LayoutInflater inflater = this.getLayoutInflater();

        View alertView = inflater.inflate(R.layout.jgg_alert_view, null);
        builder.setView(alertView);
        alertDialog = builder.create();
        TextView cancelButton = (TextView) alertView.findViewById(R.id.btn_alert_cancel);
        TextView okButton = (TextView) alertView.findViewById(R.id.btn_alert_ok);
        TextView title = (TextView) alertView.findViewById(R.id.lbl_alert_titile);
        TextView desc = (TextView) alertView.findViewById(R.id.lbl_alert_description);

        title.setText(R.string.alert_service_posted_title);
        desc.setText(R.string.alert_service_posted_desc);
        okButton.setText(R.string.alert_view_service_button);
        okButton.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGGreen));
        cancelButton.setVisibility(View.GONE);

        okButton.setOnClickListener(this);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
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

    @Override
    public void onClick(View view) {

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

        if (view.getId() == R.id.btn_post_service) {
            switch (editStatus) {
                case NONE:
                    showAlertDialog(view);
                    break;
                case EDIT:
                    showAlertDialog(view);
                    break;
                case DUPLICATE:
                    Intent intent = new Intent(mContext, PostServiceActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        } else if (view.getId() == R.id.btn_alert_ok) {
            alertDialog.dismiss();
            Intent intent = new Intent(mContext, PostedServiceActivity.class);
            intent.putExtra("is_post", true);
            mContext.startActivity(intent);
        } else if (view.getId() == R.id.btn_post_main_describe) {
            detailFragment = PostServiceDetailFragment.newInstance(EditJobTabbarView.EditTabStatus.DESCRIBE);
            ft.replace(R.id.post_service_container, detailFragment, detailFragment.getTag());
            //detailFragment.setmListener(PostServiceMainFragment.this);
            ft.addToBackStack("post_service");
            ft.commit();
        } else if (view.getId() == R.id.btn_post_main_price) {
            detailFragment = PostServiceDetailFragment.newInstance(EditJobTabbarView.EditTabStatus.TIME);
            ft.replace(R.id.post_service_container, detailFragment, detailFragment.getTag());
            //detailFragment.setmListener(PostServiceMainFragment.this);
            ft.addToBackStack("post_service");
            ft.commit();
        } else if (view.getId() == R.id.btn_post_main_time_slot) {
            detailFragment = PostServiceDetailFragment.newInstance(EditJobTabbarView.EditTabStatus.ADDRESS);
            ft.replace(R.id.post_service_container, detailFragment, detailFragment.getTag());
            //detailFragment.setmListener(PostServiceMainFragment.this);
            ft.addToBackStack("post_service");
            ft.commit();
        } else if (view.getId() == R.id.btn_post_main_address) {
            detailFragment = PostServiceDetailFragment.newInstance(EditJobTabbarView.EditTabStatus.REPORT);
            ft.replace(R.id.post_service_container, detailFragment, detailFragment.getTag());
            //detailFragment.setmListener(PostServiceMainFragment.this);
            ft.addToBackStack("post_service");
            ft.commit();
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
