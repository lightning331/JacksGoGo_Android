package com.kelvin.jacksgogo.CustomView.Views;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class JGGAlertView extends android.app.AlertDialog.Builder implements OnClickListener {

    private Context mContext;

    private TextView lblTitle;
    private TextView description;
    public EditText txtReason;
    private TextView cancelButton;
    private TextView okButton;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onDoneButtonClick(View view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public JGGAlertView(Context context,
                        String title,
                        String desc,
                        boolean reason,
                        String cancel,
                        int cancelTextColor,
                        int cancelButtonColor,
                        String ok,
                        int okButtonColor) {
        super(context);
        this.mContext = context;

        initView();

        lblTitle.setText(title);
        if (desc.equals(""))
            description.setVisibility(View.GONE);
        else
            description.setText(desc);
        if (reason)
            txtReason.setVisibility(View.VISIBLE);
        if (cancel.equals(""))
            cancelButton.setVisibility(View.GONE);
        else {
            cancelButton.setText(cancel);
            cancelButton.setTextColor(ContextCompat.getColor(mContext, cancelTextColor));
            cancelButton.setBackgroundColor(ContextCompat.getColor(mContext, cancelButtonColor));
        }
        okButton.setText(ok);
        okButton.setBackgroundColor(ContextCompat.getColor(mContext, okButtonColor));
    }

    public void initView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View alertView = inflater.inflate(R.layout.jgg_alert_view, null);

        lblTitle = alertView.findViewById(R.id.lbl_alert_titile);
        description = alertView.findViewById(R.id.lbl_alert_description);
        txtReason = alertView.findViewById(R.id.txt_alert_reason);
        cancelButton = alertView.findViewById(R.id.btn_alert_cancel);
        okButton = alertView.findViewById(R.id.btn_alert_ok);

        cancelButton.setOnClickListener(this);
        okButton.setOnClickListener(this);

        txtReason.setVisibility(View.GONE);
        setView(alertView);
    }

    public void setDialog(int title, int desc, boolean reason, int cancel, int cancelTextColor, int cancelButtonColor, int ok, int okButtonColor) {

    }

    @Override
    public void onClick(View v) {
        listener.onDoneButtonClick(v);
    }
}
