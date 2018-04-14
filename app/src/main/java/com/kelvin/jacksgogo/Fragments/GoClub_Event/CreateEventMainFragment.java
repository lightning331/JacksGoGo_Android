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

import com.kelvin.jacksgogo.CustomView.Views.PostEventTabView;
import com.kelvin.jacksgogo.CustomView.Views.PostEventTabView.EventTabName;
import com.kelvin.jacksgogo.Fragments.Search.PostServiceDescribeFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.PostStatus;
import com.squareup.picasso.Picasso;

import static com.kelvin.jacksgogo.Utils.Global.EVENTS;
import static com.kelvin.jacksgogo.Utils.JGGAppManager.selectedCategory;

public class CreateEventMainFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private PostEventTabView tabView;
    private ImageView imgCategory;
    private TextView lblCategory;

    private String tabName;
    private String postStatus;

    public CreateEventMainFragment() {
        // Required empty public constructor
    }

    public static CreateEventMainFragment newInstance(EventTabName name, PostStatus status) {
        CreateEventMainFragment fragment = new CreateEventMainFragment();
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
        View view = inflater.inflate(R.layout.fragment_create_event_main, container, false);

        imgCategory = (ImageView) view.findViewById(R.id.img_category);
        lblCategory = (TextView) view.findViewById(R.id.lbl_category_name);
        Picasso.with(mContext)
                .load(selectedCategory.getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(selectedCategory.getName());

        initTabView(view);

        return view;
    }

    private void initTabView(View view) {

        tabView = new PostEventTabView(mContext);
        LinearLayout tabViewLayout = view.findViewById(R.id.post_go_club_tab_view);
        tabViewLayout.addView(tabView);
        if (tabName.equals("DESCRIBE")) {
            tabView.setTabName(EventTabName.DESCRIBE);
        } else if (tabName.equals("TIME")) {
            tabView.setTabName(EventTabName.TIME);
        } else if (tabName.equals("ADDRESS")) {
            tabView.setTabName(EventTabName.ADDRESS);
        } else if (tabName.equals("LIMIT")) {
            tabView.setTabName(EventTabName.LIMIT);
        } else if (tabName.equals("COST")) {
            tabView.setTabName(EventTabName.COST);
        }
        tabView.setTabItemClickListener(new PostEventTabView.OnTabItemClickListener() {
            @Override
            public void onTabItemClick(View view) {
                onTabViewClick(view);
            }
        });
        refreshFragment();
    }

    private void onTabViewClick(View view) {

        if (view.getId() == R.id.btn_describe) {
            tabView.setTabName(EventTabName.DESCRIBE);
        } else if (view.getId() == R.id.btn_time) {
            tabView.setTabName(EventTabName.TIME);
        } else if (view.getId() == R.id.btn_address) {
            tabView.setTabName(EventTabName.ADDRESS);
        } else if (view.getId() == R.id.btn_budget) {
            tabView.setTabName(EventTabName.LIMIT);
        } else if (view.getId() == R.id.btn_report) {
            tabView.setTabName(EventTabName.COST);
        }
        refreshFragment();
    }

    private void refreshFragment() {

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        if (tabView.getTabName() == EventTabName.DESCRIBE) {
            PostServiceDescribeFragment frag = PostServiceDescribeFragment.newInstance(EVENTS);
            frag.setOnItemClickListener(new PostServiceDescribeFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    tabView.setTabName(EventTabName.TIME);
                    refreshFragment();
                }
            });
            ft.replace(R.id.go_club_main_tab_container, frag, frag.getTag());
        } else if (tabView.getTabName() == EventTabName.TIME) {

        } else if (tabView.getTabName() == EventTabName.ADDRESS) {

        } else if (tabView.getTabName() == EventTabName.LIMIT) {

        } else if (tabView.getTabName() == EventTabName.COST) {

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
