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
import android.widget.FrameLayout;
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
import com.kelvin.jacksgogo.Fragments.Profile.ProfileHomeFragment;
import com.kelvin.jacksgogo.Fragments.Profile.SignInFragment;
import com.kelvin.jacksgogo.Fragments.Search.SearchFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.API.JGGAppManager;

import io.fabric.sdk.android.Fabric;

import static com.kelvin.jacksgogo.Utils.Global.SIGNUP_FINISHED;

public class MainActivity extends AppCompatActivity implements AppMainFragment.OnFragmentInteractionListener {

    private Toolbar mToolbar;
    private AppMainTabView appMainTabView;
    private SearchMainTabView searchTabView;
    private FavouriteMainTabView favouriteTabView;
    private FrameLayout mContainer;
    private CoordinatorLayout.LayoutParams topNavLayoutParams;
    private CoordinatorLayout.LayoutParams bottomNavLayoutParams;
    private BottomNavigationView mbtmView;
    private MenuItem mItem;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            mItem = item;
            selectFragment();
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

    public void selectFragment() {
        Fragment frag = null;
        // Show Top Navigation Bar
        topNavLayoutParams.setBehavior(new AppBarLayout.ScrollingViewBehavior());
        mContainer.requestLayout();
        // Hide Bottom NavigationView and ToolBar in Scroll
        bottomNavLayoutParams.setBehavior(new BottomNavigationViewBehavior());

        switch (mItem.getItemId()) {
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
                // Remove Top Navigation Bar
                topNavLayoutParams.setBehavior(null);
                // Disable Bottom Navigation Bar hidden
                bottomNavLayoutParams.setBehavior(null);
                mContainer.requestLayout();

                String username = JGGAppManager.getInstance(this).getUsernamePassword()[0];
                if (!username.equals("")) {
                    frag = ProfileHomeFragment.newInstance();
                } else {
                    frag = SignInFragment.newInstance();
                }
                break;
        }
        initTopNavigation(frag);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.myToolbar);
        mContainer = (FrameLayout) findViewById(R.id.container);
        mbtmView = (BottomNavigationView) findViewById(R.id.navigation);

        topNavLayoutParams = (CoordinatorLayout.LayoutParams) mContainer.getLayoutParams();
        BottomNavigationViewHelper.disableShiftMode(mbtmView);
        bottomNavLayoutParams = (CoordinatorLayout.LayoutParams) mbtmView.getLayoutParams();

        mbtmView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        boolean bSmsVeryfyKey = getIntent().getBooleanExtra(SIGNUP_FINISHED, false);
        if (bSmsVeryfyKey) {
            mbtmView.setSelectedItemId(R.id.navigation_profile);
        } else {
            mbtmView.setSelectedItemId(R.id.navigation_home);
        }

        appMainTabView = new AppMainTabView(this);
        searchTabView = new SearchMainTabView(this);
        favouriteTabView = new FavouriteMainTabView(this);

        setSupportActionBar(mToolbar);
    }

    private void initTopNavigation(Fragment frag) {
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
        }
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
