package com.kelvin.jacksgogo.Activities;

import android.app.ActionBar;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.kelvin.jacksgogo.Activities.BottomNavigation.BottomNavigationViewHelper;
import com.kelvin.jacksgogo.Fragments.FavouriteFragment;
import com.kelvin.jacksgogo.Fragments.HomeFragment;
import com.kelvin.jacksgogo.Fragments.ProfileFragment;
import com.kelvin.jacksgogo.Fragments.SearchFragment;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Fragments.AppointmentsFragment;

public class MainActivity extends AppCompatActivity implements AppointmentsFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            selectFragment(item);
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_search:
                    mTextMessage.setText(R.string.title_search);
                    return true;
                case R.id.navigation_appointments:
                    mTextMessage.setText("");
                    return true;
                case R.id.navigation_favourite:
                    mTextMessage.setText(R.string.title_favourite);
                    return true;
                case R.id.navigation_profile:
                    mTextMessage.setText(R.string.title_profile);
                    return true;
            }
            return false;
        }
    };

    private void selectFragment(MenuItem item) {
        Fragment frag = null;
        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.navigation_home:
                frag = HomeFragment.newInstance(null,
                        null);
                break;
            case R.id.navigation_search:
                frag = SearchFragment.newInstance(null,
                        null);
                break;
            case R.id.navigation_appointments:
                frag = AppointmentsFragment.newInstance(null,
                        null);
                break;
            case R.id.navigation_favourite:
                frag = FavouriteFragment.newInstance(null,
                        null);
                break;
            case R.id.navigation_profile:
                frag = ProfileFragment.newInstance(null,
                        null);
                break;
        }

        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, frag, frag.getTag());
            ft.commit();
            if (frag instanceof AppointmentsFragment) {
                this.addTopActionBarForAppointment();
            }
        }
    }

    private void addTopActionBarForAppointment() {

        /*
        ActionBar mActionBar = getActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        //LayoutInflater mInflater = LayoutInflater.from(this);
        LayoutInflater mInflater = (LayoutInflater) this .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View mCustomView = mInflater.inflate(R.layout.appointment_custom_actionbar, null);

        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setCustomView(mCustomView);
*/
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        getSupportActionBar().hide();

        mTextMessage = (TextView) findViewById(R.id.message);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
