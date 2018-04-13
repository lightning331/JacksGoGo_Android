package com.kelvin.jacksgogo.Fragments.Search;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.CustomView.Views.PostServiceTabView;
import com.kelvin.jacksgogo.R;
import com.squareup.picasso.Picasso;

import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedAppointment;
import static com.kelvin.jacksgogo.Utils.Global.SERVICES;

public class PostServiceMainTabFragment extends Fragment {

    private static final String TAB_MENU1 = "Describe";
    private static final String TAB_MENU2 = "Price";
    private static final String TAB_MENU3 = "TimeSlot";
    private static final String TAB_MENU4 = "Address";

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private PostServiceTabView tabbarView;
    private LinearLayout tabbarLayout;
    private ImageView imgCategory;
    private TextView lblCategory;

    private PostServiceSummaryFragment.PostEditStatus editStatus;
    private String tabName;

    public void setEditStatus(PostServiceSummaryFragment.PostEditStatus editStatus) {
        this.editStatus = editStatus;
    }

    public PostServiceMainTabFragment() {
        // Required empty public constructor
    }

    public static PostServiceMainTabFragment newInstance(PostServiceTabView.PostServiceTabName name) {
        PostServiceMainTabFragment fragment = new PostServiceMainTabFragment();
        Bundle args = new Bundle();
        args.putString("tabName", name.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tabName = getArguments().getString("tabName");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post_service_main_tab, container, false);

        imgCategory = (ImageView) view.findViewById(R.id.img_post_service_tab_category);
        lblCategory = (TextView) view.findViewById(R.id.lbl_post_service_tab_category_name);
        Picasso.with(mContext)
                .load(selectedAppointment.getCategory().getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(selectedAppointment.getCategory().getName());

        tabbarLayout = (LinearLayout)view.findViewById(R.id.post_service_tabbar_view);
        tabbarView = new PostServiceTabView(getContext());
        tabbarView.mDescribeText.setText(TAB_MENU1);
        tabbarView.mTimeText.setText(TAB_MENU2);
        tabbarView.mAddressText.setText(TAB_MENU3);
        tabbarView.mReportText.setText(TAB_MENU4);
        tabbarLayout.addView(tabbarView);

        initTabbarView();

        tabbarView.setTabItemClickListener(new PostServiceTabView.OnTabItemClickListener() {
            @Override
            public void onTabItemClick(View view) {
                onTabbarViewClick(view);
            }
        });
        refreshFragment();

        return view;
    }

    private void initTabbarView() {

        if (tabName == "DESCRIBE") {
            tabbarView.setTabName(PostServiceTabView.PostServiceTabName.DESCRIBE, true);
        } else if (tabName == "TIME") {
            tabbarView.setTabName(PostServiceTabView.PostServiceTabName.TIME, true);
        } else if (tabName == "ADDRESS") {
            tabbarView.setTabName(PostServiceTabView.PostServiceTabName.ADDRESS, true);
        } else if (tabName == "REPORT") {
            tabbarView.setTabName(PostServiceTabView.PostServiceTabName.REPORT, true);
        }
        refreshFragment();
    }

    private void onTabbarViewClick(View view) {

        if (view.getId() == R.id.btn_describe) {
            tabbarView.setTabName(PostServiceTabView.PostServiceTabName.DESCRIBE, true);
        } else if (view.getId() == R.id.btn_time) {
            tabbarView.setTabName(PostServiceTabView.PostServiceTabName.TIME, true);
        } else if (view.getId() == R.id.btn_address) {
            tabbarView.setTabName(PostServiceTabView.PostServiceTabName.ADDRESS, true);
        } else if (view.getId() == R.id.btn_report) {
            tabbarView.setTabName(PostServiceTabView.PostServiceTabName.REPORT, true);
        }
        refreshFragment();
    }

    private void refreshFragment() {

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        if (tabbarView.getPostServiceTabName() == PostServiceTabView.PostServiceTabName.DESCRIBE) {
            PostServiceDescribeFragment frag = PostServiceDescribeFragment.newInstance(SERVICES);
            frag.setOnItemClickListener(new PostServiceDescribeFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    tabbarView.setTabName(PostServiceTabView.PostServiceTabName.TIME, true);
                    refreshFragment();
                }
            });
            ft.replace(R.id.post_service_detail_container, frag, frag.getTag());
        } else if (tabbarView.getPostServiceTabName() == PostServiceTabView.PostServiceTabName.TIME) {
            PostServicePriceFragment frag = new PostServicePriceFragment();
            frag.setOnItemClickListener(new PostServicePriceFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    tabbarView.setTabName(PostServiceTabView.PostServiceTabName.ADDRESS, true);
                    refreshFragment();
                }
            });
            ft.replace(R.id.post_service_detail_container, frag, frag.getTag());
        } else if (tabbarView.getPostServiceTabName() == PostServiceTabView.PostServiceTabName.ADDRESS) {
            PostServiceTimeSlotFragment frag = new PostServiceTimeSlotFragment();
            frag.setOnItemClickListener(new PostServiceTimeSlotFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    tabbarView.setTabName(PostServiceTabView.PostServiceTabName.REPORT, true);
                    refreshFragment();
                }
            });
            ft.replace(R.id.post_service_detail_container, frag, frag.getTag());
        } else if (tabbarView.getPostServiceTabName() == PostServiceTabView.PostServiceTabName.REPORT) {
            PostServiceAddressFragment frag = PostServiceAddressFragment.newInstance(SERVICES);
            frag.setOnItemClickListener(new PostServiceAddressFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    PostServiceSummaryFragment fragment = new PostServiceSummaryFragment();
                    fragment.setEditStatus(editStatus);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.post_service_container, fragment, fragment.getTag())
                            .addToBackStack("post_service_summary")
                            .commit();
                }
            });
            ft.replace(R.id.post_service_detail_container, frag, frag.getTag());
        }
        ft.commit();
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
