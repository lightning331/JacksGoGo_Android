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
import com.kelvin.jacksgogo.CustomView.Views.PostJobTabView;
import com.kelvin.jacksgogo.Fragments.Search.PostServiceDescribeFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Global.PostStatus;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.squareup.picasso.Picasso;

import static com.kelvin.jacksgogo.Utils.Global.EVENTS;
import static com.kelvin.jacksgogo.Utils.Global.GOCLUB;

public class CreateEventMainFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Context mContext;

    private JGGCategoryModel selectedCategory;
    private PostEventTabView tabView;
    private ImageView imgCategory;
    private TextView lblCategory;

    private String tabName;
    private String postStatus;
    private int tabIndex = 0;

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

        selectedCategory = JGGAppManager.getInstance().getSelectedCategory();

        Picasso.with(mContext)
                .load(selectedCategory.getImage())
                .placeholder(null)
                .into(imgCategory);
        lblCategory.setText(selectedCategory.getName());

        tabView = new PostEventTabView(mContext);
        LinearLayout tabViewLayout = view.findViewById(R.id.post_go_club_tab_view);
        tabViewLayout.addView(tabView);
        tabView.setTabItemClickListener(new PostEventTabView.OnTabItemClickListener() {
            @Override
            public void onTabItemClick(View view) {
                onTabViewClick(view);
            }
        });

        initTabView(view);

        return view;
    }

    public int getTabIndex() {
        return this.tabIndex;
    }

    public void setTabIndex(int index) {
        this.tabIndex = index;
        switch (index) {
            case 0:
                tabView.setTabName(EventTabName.DESCRIBE);
                break;
            case 1:
                tabView.setTabName(EventTabName.TIME);
                break;
            case 2:
                tabView.setTabName(EventTabName.ADDRESS);
                break;
            case 3:
                tabView.setTabName(EventTabName.LIMIT);
                break;
            case 4:
                tabView.setTabName(EventTabName.COST);
                break;
        }

        refreshFragment();
    }

    private void initTabView(View view) {

        if (tabName.equals("DESCRIBE")) {
            setTabIndex(0);
        } else if (tabName.equals("TIME")) {
            setTabIndex(1);
        } else if (tabName.equals("ADDRESS")) {
            setTabIndex(2);
        } else if (tabName.equals("LIMIT")) {
            setTabIndex(3);
        } else if (tabName.equals("COST")) {
            setTabIndex(4);
        }
    }

    private void onTabViewClick(View view) {
        if (view.getId() == R.id.btn_describe) {
            setTabIndex(0);
        } else if (view.getId() == R.id.btn_time) {
            setTabIndex(1);
        } else if (view.getId() == R.id.btn_address) {
            setTabIndex(2);
        } else if (view.getId() == R.id.btn_budget) {
            setTabIndex(3);
        } else if (view.getId() == R.id.btn_report) {
            setTabIndex(4);
        }
    }

    private void refreshFragment() {

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        if (tabView.getTabName() == EventTabName.DESCRIBE) {
            PostServiceDescribeFragment frag = PostServiceDescribeFragment.newInstance(EVENTS);
            frag.setOnItemClickListener(new PostServiceDescribeFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    setTabIndex(1);
                }
            });
            ft.replace(R.id.go_club_main_tab_container, frag, frag.getTag());
        } else if (tabView.getTabName() == EventTabName.TIME) {
            GcTimeFragment frag = new GcTimeFragment();
            frag.setOnItemClickListener(new GcTimeFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    setTabIndex(2);
                }
            });
            ft.replace(R.id.go_club_main_tab_container, frag, frag.getTag());
        } else if (tabView.getTabName() == EventTabName.ADDRESS) {
            GcAddressFragment frag = GcAddressFragment.newInstance(GOCLUB);;
            frag.setOnItemClickListener(new GcTimeFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    setTabIndex(3);
                }
            });
            ft.replace(R.id.go_club_main_tab_container, frag, frag.getTag());
        } else if (tabView.getTabName() == EventTabName.LIMIT) {
            GcEventLimitFragment frag = new GcEventLimitFragment();
            frag.setOnItemClickListener(new GcTimeFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    setTabIndex(4);
                }
            });
            ft.replace(R.id.go_club_main_tab_container, frag, frag.getTag());
        } else if (tabView.getTabName() == EventTabName.COST) {
            GcCostFragment frag = new GcCostFragment();
            frag.setOnItemClickListener(new GcCostFragment.OnItemClickListener() {
                @Override
                public void onNextButtonClick() {
                    GcEventSummaryFragment fragment = new GcEventSummaryFragment();
//                    fragment.setEditStatus(editStatus);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.post_go_club_container, fragment, fragment.getTag())
                            .addToBackStack("post_goclub_summary")
                            .commit();
                }
            });
            ft.replace(R.id.go_club_main_tab_container, frag, frag.getTag());
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
