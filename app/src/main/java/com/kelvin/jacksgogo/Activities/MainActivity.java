package com.kelvin.jacksgogo.Activities;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewBehavior;
import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewHelper;
import com.kelvin.jacksgogo.Fragments.FavouriteFragment;
import com.kelvin.jacksgogo.Fragments.HomeFragment;
import com.kelvin.jacksgogo.Fragments.ProfileFragment;
import com.kelvin.jacksgogo.Fragments.SearchFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Fragments.AppointmentsFragment;

public class MainActivity extends AppCompatActivity implements AppointmentsFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;
    private Toolbar mToolbar;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            selectFragment(item);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_search:
                    return true;
                case R.id.navigation_appointments:
                    return true;
                case R.id.navigation_favourite:
                    return true;
                case R.id.navigation_profile:
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
                frag = SearchFragment.newInstance(null,
                        null);
                mToolbar.setTitle(R.string.title_search);
                break;
            case R.id.navigation_appointments:
                frag = AppointmentsFragment.newInstance();
                break;
            case R.id.navigation_favourite:
                frag = FavouriteFragment.newInstance(null,
                        null);
                mToolbar.setTitle(R.string.title_favourite);
                break;
            case R.id.navigation_profile:
                frag = ProfileFragment.newInstance(null,
                        null);
                mToolbar.setTitle(R.string.title_profile);
                break;
        }

        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, frag, frag.getTag());
            ft.commit();
            if (frag instanceof AppointmentsFragment) {
                this.addTopActionBarForAppointment();
            } else {
                mToolbar.getMenu().clear();
            }
        }
    }

    private void addTopActionBarForAppointment() {

        mToolbar = (Toolbar) findViewById(R.id.myToolbar);
        setSupportActionBar(mToolbar);
        LayoutInflater mInflater = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mCustomMenuView = mInflater.inflate(R.layout.appointment_custom_actionbar, null);
        mToolbar.addView(mCustomMenuView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomNavigationView.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationViewBehavior());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
