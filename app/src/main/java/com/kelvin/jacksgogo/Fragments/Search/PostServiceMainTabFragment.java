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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.selectedCategory;
import com.kelvin.jacksgogo.CustomView.Views.PostServiceTabbarView;
import com.kelvin.jacksgogo.R;
import com.squareup.picasso.Picasso;

public class PostServiceMainTabFragment extends Fragment {

    private static final String TAB_MENU1 = "Describe";
    private static final String TAB_MENU2 = "Price";
    private static final String TAB_MENU3 = "TimeSlot";
    private static final String TAB_MENU4 = "Address";

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private PostServiceTabbarView tabbarView;
    private LinearLayout tabbarLayout;
    private ImageView imgCategory;
    private TextView lblCategory;

    private AlertDialog alertDialog;
    private String tabName;

    public PostServiceMainTabFragment() {
        // Required empty public constructor
    }

    public static PostServiceMainTabFragment newInstance(PostServiceTabbarView.TabName name) {
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
                .load(selectedCategory.getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(selectedCategory.getName());

        tabbarLayout = (LinearLayout)view.findViewById(R.id.post_service_tabbar_view);
        tabbarView = new PostServiceTabbarView(getContext());
        tabbarView.mDescribeText.setText(TAB_MENU1);
        tabbarView.mTimeText.setText(TAB_MENU2);
        tabbarView.mAddressText.setText(TAB_MENU3);
        tabbarView.mReportText.setText(TAB_MENU4);
        tabbarLayout.addView(tabbarView);

        initTabbarView();

        tabbarView.setTabItemClickLietener(new PostServiceTabbarView.OnTabItemClickListener() {
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
            tabbarView.setTabName(PostServiceTabbarView.TabName.DESCRIBE, true);
        } else if (tabName == "TIME") {
            tabbarView.setTabName(PostServiceTabbarView.TabName.TIME, true);
        } else if (tabName == "ADDRESS") {
            tabbarView.setTabName(PostServiceTabbarView.TabName.ADDRESS, true);
        } else if (tabName == "REPORT") {
            tabbarView.setTabName(PostServiceTabbarView.TabName.REPORT, true);
        }
        refreshFragment();
    }

    private void onTabbarViewClick(View view) {

        if (view.getId() == R.id.btn_describe) {
            tabbarView.setTabName(PostServiceTabbarView.TabName.DESCRIBE, true);
        } else if (view.getId() == R.id.btn_time) {
            tabbarView.setTabName(PostServiceTabbarView.TabName.TIME, true);
        } else if (view.getId() == R.id.btn_address) {
            tabbarView.setTabName(PostServiceTabbarView.TabName.ADDRESS, true);
        } else if (view.getId() == R.id.btn_report) {
            tabbarView.setTabName(PostServiceTabbarView.TabName.REPORT, true);
        }
        refreshFragment();
    }

    private void refreshFragment() {

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        if (tabbarView.getTabName() == PostServiceTabbarView.TabName.DESCRIBE) {
            PostServiceDescribeFragment frag = PostServiceDescribeFragment.newInstance("SERVICE");
            frag.setOnItemClickListener(new PostServiceDescribeFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    tabbarView.setTabName(PostServiceTabbarView.TabName.TIME, true);
                    refreshFragment();
                }
            });
            ft.replace(R.id.post_service_detail_container, frag, frag.getTag());
        } else if (tabbarView.getTabName() == PostServiceTabbarView.TabName.TIME) {
            PostServicePriceFragment frag = new PostServicePriceFragment();
            frag.setOnItemClickListener(new PostServicePriceFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    tabbarView.setTabName(PostServiceTabbarView.TabName.ADDRESS, true);
                    refreshFragment();
                }
            });
            ft.replace(R.id.post_service_detail_container, frag, frag.getTag());
        } else if (tabbarView.getTabName() == PostServiceTabbarView.TabName.ADDRESS) {
            PostServiceTimeSlotFragment frag = new PostServiceTimeSlotFragment();
            frag.setOnItemClickListener(new PostServiceTimeSlotFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    tabbarView.setTabName(PostServiceTabbarView.TabName.REPORT, true);
                    refreshFragment();
                }
            });
            ft.replace(R.id.post_service_detail_container, frag, frag.getTag());
        } else if (tabbarView.getTabName() == PostServiceTabbarView.TabName.REPORT) {
            PostServiceAddressFragment frag = PostServiceAddressFragment.newInstance("SERVICE");
            frag.setOnItemClickListener(new PostServiceAddressFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    PostServiceSummaryFragment fragment = new PostServiceSummaryFragment();
                    fragment.setEditStatus(PostServiceSummaryFragment.PostEditStatus.NONE);

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
