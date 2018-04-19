package com.kelvin.jacksgogo.Fragments.GoClub_Event;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.jacksgogo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GcLimitFragment extends Fragment {


    public GcLimitFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gc_limit, container, false);
    }

    // TODO : Next Click Listener
    private GcTimeFragment.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onNextButtonClick();
    }

    public void setOnItemClickListener(GcTimeFragment.OnItemClickListener listener) {
        this.listener = listener;
    }

}
