package com.kelvin.jacksgogo.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

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
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
