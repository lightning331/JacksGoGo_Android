package com.kelvin.jacksgogo.Fragments.GoClub_Event;

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

import com.kelvin.jacksgogo.Activities.GoClub_Event.CreateGoClubActivity;
import com.kelvin.jacksgogo.CustomView.Views.PostGoClubTabView;
import com.kelvin.jacksgogo.CustomView.Views.PostGoClubTabView.GoClubTabName;
import com.kelvin.jacksgogo.Fragments.Search.PostServiceDescribeFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.PostStatus;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.squareup.picasso.Picasso;

import static com.kelvin.jacksgogo.Utils.Global.GOCLUB;
import static com.kelvin.jacksgogo.Utils.Global.POST;

public class CreateGoClubMainFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;
    private CreateGoClubActivity mActivity;

    private JGGCategoryModel selectedCategory;
    private PostGoClubTabView tabView;
    private ImageView imgCategory;
    private TextView lblCategory;

    private String tabName;
    private String postStatus;

    public CreateGoClubMainFragment() {
        // Required empty public constructor
    }

    public static CreateGoClubMainFragment newInstance(GoClubTabName name, PostStatus status) {
        CreateGoClubMainFragment fragment = new CreateGoClubMainFragment();
        Bundle args = new Bundle();
        args.putString("tabName", name.toString());
        args.putString("postStatus", status.toString());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tabName = getArguments().getString("tabName");
            postStatus = getArguments().getString("postStatus");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_go_club_main, container, false);

        imgCategory = (ImageView) view.findViewById(R.id.img_category);
        lblCategory = (TextView) view.findViewById(R.id.lbl_category_name);

        selectedCategory = JGGAppManager.getInstance().getSelectedClub().getCategory();

        Picasso.with(mContext)
                .load(selectedCategory.getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(selectedCategory.getName());

        initTabView(view);

        return view;
    }

    private void initTabView(View view) {

        tabView = new PostGoClubTabView(mContext);
        LinearLayout tabViewLayout = view.findViewById(R.id.post_go_club_tab_view);
        tabViewLayout.addView(tabView);
        if (tabName.equals("DESCRIBE")) {
            tabView.setTabName(GoClubTabName.DESCRIBE, true);
        } else if (tabName.equals("LIMIT")) {
            tabView.setTabName(GoClubTabName.LIMIT, true);
        } else if (tabName.equals("ADMIN")) {
            tabView.setTabName(GoClubTabName.ADMIN, true);
        }
        tabView.setTabItemClickListener(new PostGoClubTabView.OnTabItemClickListener() {
            @Override
            public void onTabItemClick(View view) {
                onTabViewClick(view);
            }
        });
        refreshFragment();
    }

    private void onTabViewClick(View view) {

        if (view.getId() == R.id.btn_describe) {
            tabView.setTabName(GoClubTabName.DESCRIBE, true);
        } else if (view.getId() == R.id.btn_time) {
            tabView.setTabName(GoClubTabName.LIMIT, true);
        } else if (view.getId() == R.id.btn_address) {
            tabView.setTabName(GoClubTabName.ADMIN, true);
        }
        refreshFragment();
    }

    private void refreshFragment() {

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        if (tabView.getTabName() == GoClubTabName.DESCRIBE) {
            PostServiceDescribeFragment frag = PostServiceDescribeFragment.newInstance(GOCLUB);
            frag.setOnItemClickListener(new PostServiceDescribeFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    tabView.setTabName(GoClubTabName.LIMIT, true);
                    refreshFragment();
                }
            });
            ft.replace(R.id.go_club_main_tab_container, frag, frag.getTag());
        } else if (tabView.getTabName() == GoClubTabName.LIMIT) {
            GcLimitFragment frag = new GcLimitFragment();
            frag.setOnItemClickListener(new GcLimitFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    tabView.setTabName(GoClubTabName.ADMIN, true);
                    refreshFragment();
                }
            });
            ft.replace(R.id.go_club_main_tab_container, frag, frag.getTag());
        } else if (tabView.getTabName() == GoClubTabName.ADMIN) {
            GcAdminFragment frag = new GcAdminFragment();
            frag.setOnItemClickListener(new GcAdminFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    onClickGoToSummary();
                }
            });
            ft.replace(R.id.go_club_main_tab_container, frag, frag.getTag());
        }
        ft.commit();
    }

    private void onClickGoToSummary() {
        GcSummaryFragment fragment = new GcSummaryFragment();
        if (postStatus.equals(POST)) fragment.setEditStatus(PostStatus.POST);
        else fragment.setEditStatus(PostStatus.EDIT);
        mActivity.getSupportFragmentManager().beginTransaction()
                .replace(R.id.post_go_club_container, fragment)
                .addToBackStack("create_club_summary")
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
        mActivity = ((CreateGoClubActivity) context);
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
