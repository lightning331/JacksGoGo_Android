package com.kelvin.jacksgogo.CustomView.Views;

import android.content.Context;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.kelvin.jacksgogo.R;

/**
 * Created by storm on 5/15/2018.
 */

public class JGGBookAlertView extends BottomSheetDialog implements View.OnClickListener {

    private Context mContext;
    private Button btnBook;
    private Button btnCancel;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDoneButtonClick(View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public JGGBookAlertView(Context context) {
        super(context);
        mContext = context;

        initView();
    }

    public JGGBookAlertView(Context context, int themeResId) {
        super(context, themeResId);

        mContext = context;

        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View alertView = inflater.inflate(R.layout.jgg_book_alert_view, null);
        btnBook = alertView.findViewById(R.id.btn_book);
        btnCancel = alertView.findViewById(R.id.btn_cancel);
        btnBook.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        setContentView(alertView);
    }

    @Override
    public void onClick(View v) {
        listener.onDoneButtonClick(v);
    }
}
