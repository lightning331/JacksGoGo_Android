package com.kelvin.jacksgogo.Fragments.Search;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kelvin.jacksgogo.CustomView.Views.EditJobTabbarView;
import com.kelvin.jacksgogo.R;

public class PostServiceDetailFragment extends Fragment {

    private static final String TAB_MENU1 = "Describe";
    private static final String TAB_MENU2 = "Price";
    private static final String TAB_MENU3 = "TimeSlot";
    private static final String TAB_MENU4 = "Address";

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private EditJobTabbarView tabbarView;
    private LinearLayout tabbarLayout;
    private AlertDialog alertDialog;

    private String status;

    public PostServiceDetailFragment() {
        // Required empty public constructor
    }

    public static PostServiceDetailFragment newInstance(EditJobTabbarView.EditTabStatus status) {
        PostServiceDetailFragment fragment = new PostServiceDetailFragment();
        Bundle args = new Bundle();
        args.putString("status", status.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            status = getArguments().getString("status");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_service_detail, container, false);

        tabbarLayout = (LinearLayout)view.findViewById(R.id.post_service_tabbar_view);
        tabbarView = new EditJobTabbarView(getContext());
        tabbarView.mDescribeText.setText(TAB_MENU1);
        tabbarView.mTimeText.setText(TAB_MENU2);
        tabbarView.mAddressText.setText(TAB_MENU3);
        tabbarView.mReportText.setText(TAB_MENU4);
        tabbarLayout.addView(tabbarView);

        initTabbarView();

        tabbarView.setTabItemClickLietener(new EditJobTabbarView.OnTabItemClickListener() {
            @Override
            public void onTabItemClick(View view) {
                onTabbarViewClick(view);
            }
        });
        refreshFragment();

        return view;
    }

    private void initTabbarView() {

        if (status == "DESCRIBE") {
            tabbarView.setEditTabStatus(EditJobTabbarView.EditTabStatus.DESCRIBE, true);
        } else if (status == "TIME") {
            tabbarView.setEditTabStatus(EditJobTabbarView.EditTabStatus.TIME, true);
        } else if (status == "ADDRESS") {
            tabbarView.setEditTabStatus(EditJobTabbarView.EditTabStatus.ADDRESS, true);
        } else if (status == "REPORT") {
            tabbarView.setEditTabStatus(EditJobTabbarView.EditTabStatus.REPORT, true);
        }
        refreshFragment();
    }

    private void onTabbarViewClick(View view) {

        if (view.getId() == R.id.btn_describe) {
            tabbarView.setEditTabStatus(EditJobTabbarView.EditTabStatus.DESCRIBE, true);
        } else if (view.getId() == R.id.btn_time) {
            tabbarView.setEditTabStatus(EditJobTabbarView.EditTabStatus.TIME, true);
        } else if (view.getId() == R.id.btn_address) {
            tabbarView.setEditTabStatus(EditJobTabbarView.EditTabStatus.ADDRESS, true);
        } else if (view.getId() == R.id.btn_report) {
            tabbarView.setEditTabStatus(EditJobTabbarView.EditTabStatus.REPORT, true);
        }
        refreshFragment();
    }

    private void refreshFragment() {

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        if (tabbarView.getEditTabStatus() == EditJobTabbarView.EditTabStatus.DESCRIBE) {
            PostServiceDescribeFragment frag = new PostServiceDescribeFragment();
            frag.setOnItemClickListener(new PostServiceDescribeFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick(String title, String comment, String tags) {
                    tabbarView.setEditTabStatus(EditJobTabbarView.EditTabStatus.TIME, true);
                    refreshFragment();
                }
            });
            ft.replace(R.id.post_service_detail_container, frag, frag.getTag());
        } else if (tabbarView.getEditTabStatus() == EditJobTabbarView.EditTabStatus.TIME) {
            PostServicePriceFragment frag = new PostServicePriceFragment();
            frag.setOnItemClickListener(new PostServicePriceFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    tabbarView.setEditTabStatus(EditJobTabbarView.EditTabStatus.ADDRESS, true);
                    refreshFragment();
                }
            });
            ft.replace(R.id.post_service_detail_container, frag, frag.getTag());
        } else if (tabbarView.getEditTabStatus() == EditJobTabbarView.EditTabStatus.ADDRESS) {
            PostServiceTimeSlotFragment frag = new PostServiceTimeSlotFragment();
            frag.setOnItemClickListener(new PostServiceTimeSlotFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    tabbarView.setEditTabStatus(EditJobTabbarView.EditTabStatus.REPORT, true);
                    refreshFragment();
                }
            });
            ft.replace(R.id.post_service_detail_container, frag, frag.getTag());
        } else if (tabbarView.getEditTabStatus() == EditJobTabbarView.EditTabStatus.REPORT) {
            PostServiceAddressFragment frag = new PostServiceAddressFragment();
            frag.setOnItemClickListener(new PostServiceAddressFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    PostServiceMainFragment fragment = new PostServiceMainFragment();
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.post_service_container, fragment, fragment.getTag());
                    fragment.setEditStatus(PostServiceMainFragment.PostEditStatus.NONE);
                    ft.addToBackStack("post_service_main");
                    ft.commit();
                }
            });
            ft.replace(R.id.post_service_detail_container, frag, frag.getTag());
        }
        ft.commit();
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
