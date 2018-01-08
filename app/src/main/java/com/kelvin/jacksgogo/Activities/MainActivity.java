package com.kelvin.jacksgogo.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewBehavior;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewHelper;
import com.kelvin.jacksgogo.CustomView.Views.AppMainTabView;
import com.kelvin.jacksgogo.CustomView.Views.FavouriteMainTabView;
import com.kelvin.jacksgogo.CustomView.Views.SearchMainTabView;
import com.kelvin.jacksgogo.Fragments.Appointments.AppMainFragment;
import com.kelvin.jacksgogo.Fragments.Favourite.FavouriteFragment;
import com.kelvin.jacksgogo.Fragments.Home.HomeFragment;
import com.kelvin.jacksgogo.Fragments.Profile.ProfileFragment;
import com.kelvin.jacksgogo.Fragments.Profile.SignInFragment;
import com.kelvin.jacksgogo.Fragments.Search.SearchFragment;
import com.kelvin.jacksgogo.R;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements AppMainFragment.OnFragmentInteractionListener {

    private Toolbar mToolbar;
    private AppMainTabView appMainTabView;
    private SearchMainTabView searchTabView;
    private FavouriteMainTabView favouriteTabView;
    private FrameLayout mContainer;
    private CoordinatorLayout.LayoutParams params;

    private boolean alreadyLoged;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            selectFragment(item);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //item.setIcon(R.mipmap.tab_home_active);
                    return true;
                case R.id.navigation_search:
                    //item.setIcon(R.mipmap.tab_search_active);
                    return true;
                case R.id.navigation_appointments:
                    //item.setIcon(R.mipmap.tab_appointment_active);
                    return true;
                case R.id.navigation_favourite:
                    //item.setIcon(R.mipmap.tab_favourite_active);
                    return true;
                case R.id.navigation_profile:
                    //item.setIcon(R.mipmap.tab_profile_active);
                    return true;
            }
            return false;
        }
    };

    private void selectFragment(MenuItem item) {
        Fragment frag = null;
        mToolbar = (Toolbar) findViewById(R.id.myToolbar);
        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.navigation_home:
                frag = HomeFragment.newInstance(null,
                        null);
                mToolbar.setTitle(R.string.title_home);
                break;
            case R.id.navigation_search:
                frag = SearchFragment.newInstance();
                break;
            case R.id.navigation_appointments:
                frag = AppMainFragment.newInstance();
                break;
            case R.id.navigation_favourite:
                frag = FavouriteFragment.newInstance();
                break;
            case R.id.navigation_profile:

                if (alreadyLoged) {
                    frag = ProfileFragment.newInstance();
                    mToolbar.setTitle(R.string.title_profile);
                } else {
                    frag = SignInFragment.newInstance();
                }
                break;
        }

        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, frag, frag.getTag());
            ft.commit();

            if (frag instanceof AppMainFragment) {
                this.addTopActionBarForAppointment(frag);
            } else {
                this.removeTopActionBarForAppointment();
            }
            if (frag instanceof SearchFragment) {
                this.addTopActionBarForSearch(frag);
            } else {
                this.removeToActionBarForSearch();
            }
            if (frag instanceof FavouriteFragment) {
                this.addTopActionBarForFavourite(frag);
            } else {
                this.removeToActionBarForFavourite();
            }
            if (frag instanceof SignInFragment) {
                params.setBehavior(null);
                mContainer.requestLayout();
            } else {
                params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
                mContainer.requestLayout();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

//        Bundle bundle = getIntent().getExtras();
//        if (bundle != null) alreadyLoged = bundle.getBoolean("loged_in");

        initView();
    }

    private void initView() {
        mContainer = (FrameLayout) findViewById(R.id.container);
        params = (CoordinatorLayout.LayoutParams) mContainer.getLayoutParams();

        BottomNavigationView mbtmView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(mbtmView);
        mbtmView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mbtmView.setSelectedItemId(R.id.navigation_home);

        // Hide Bottom NavigationView and ToolBar
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) mbtmView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());

        appMainTabView = new AppMainTabView(this);
        searchTabView = new SearchMainTabView(this);
        favouriteTabView = new FavouriteMainTabView(this);

        mToolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(mToolbar);
    }

    private void addTopActionBarForAppointment(final Fragment frag) {
        mToolbar.removeView(appMainTabView);
        if (appMainTabView == null) {
            appMainTabView = new AppMainTabView(this);
        }
        mToolbar.addView(appMainTabView);

        appMainTabView.setTabbarItemClickListener(new AppMainTabView.OnTabbarItemClickListener() {
            @Override
            public void onTabbarItemClick(TextView item) {
                if (frag instanceof AppMainFragment) {
                    ((AppMainFragment)frag).refreshFragment(item.getTag());
                }
            }
        });
    }

    private void removeTopActionBarForAppointment() {
        mToolbar.removeView(appMainTabView);
    }

    private void addTopActionBarForSearch(final Fragment frag) {
        mToolbar.removeView(searchTabView);
        if (searchTabView == null) searchTabView = new SearchMainTabView(this);
        mToolbar.addView(searchTabView);

        searchTabView.setTabbarItemClickListener(new SearchMainTabView.OnTabbarItemClickListener() {
            @Override
            public void onTabbarItemClick(TextView item) {
                if (frag instanceof SearchFragment) {
                    ((SearchFragment)frag).refreshFragment(item.getTag().toString());
                }
            }
        });
    }

    private void removeToActionBarForSearch() {
        mToolbar.removeView(searchTabView);
    }

    private void addTopActionBarForFavourite(final Fragment frag) {
        mToolbar.removeView(favouriteTabView);
        if (favouriteTabView == null) favouriteTabView = new FavouriteMainTabView(this);
        mToolbar.addView(favouriteTabView);

        favouriteTabView.setTabbarItemClickListener(new FavouriteMainTabView.OnTabbarItemClickListener() {
            @Override
            public void onTabbarItemClick(TextView item) {
                if (frag instanceof FavouriteFragment) {
                    ((FavouriteFragment)frag).refreshFragment(item.getTag().toString());
                }
            }
        });
    }

    private void removeToActionBarForFavourite() {
        mToolbar.removeView(favouriteTabView);
    }

    public void setLoginStatus(boolean status) {
        alreadyLoged = status;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
