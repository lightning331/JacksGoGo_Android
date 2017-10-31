package com.kelvin.jacksgogo.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.CustomView.ListSectionHeaderView;
import com.kelvin.jacksgogo.Models.JGGAppointmentBaseModel;
import com.kelvin.jacksgogo.Models.JGGEventModel;
import com.kelvin.jacksgogo.Models.JGGServiceModel;
import com.kelvin.jacksgogo.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by PUMA on 10/31/2017.
 * https://rajeshandroiddeveloper.blogspot.jp/2013/05/sectioned-list-view-list-with-headers.html
 */

public class AppointmentRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<JGGAppointmentBaseModel> dataSet;
    public final Map<String, ArrayList<JGGAppointmentBaseModel>> sections = new LinkedHashMap<>();
    public final ArrayAdapter<String> headers;
    public final static int TYPE_SECTION_HEADER = 0;

    Context mContext;

    public AppointmentRecyclerViewAdapter(Context context) {
        this.mContext = context;
        headers = new ArrayAdapter<String>(context, R.layout.appointments_header); // this is the header desing page.
    }

    public void addSection(String section, ArrayList<JGGAppointmentBaseModel> arrayList) {
        this.headers.add(section);
        this.sections.put(section, arrayList);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_SECTION_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointments_header, parent, false);
            return new ListSectionHeaderView(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointments_list_cell, parent, false);
            return new AppointmentListView(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object itemData = getItem(position);
        if (itemData instanceof String) {
            ListSectionHeaderView sectionView = (ListSectionHeaderView)holder;
            sectionView.setTitle((String)itemData);
        } else if (itemData instanceof JGGAppointmentBaseModel) {
            AppointmentListView cellView = (AppointmentListView)holder;
            JGGAppointmentBaseModel appointment = (JGGAppointmentBaseModel)itemData;

            cellView.lbl_Title.setText(appointment.getTitle());
            cellView.lbl_Comment.setText(appointment.getComment());
            cellView.lbl_Day.setText(appointment.getAppointmentDay());
            cellView.lbl_Month.setText(appointment.getAppointmentMonth());

            if (appointment.getStatus() == JGGAppointmentBaseModel.AppointmentStatus.CANCELLED) {
                cellView.lbl_Status.setText("Cancelled");
            } else if (appointment.getStatus() == JGGAppointmentBaseModel.AppointmentStatus.WITHDRAWN) {
                cellView.lbl_Status.setText("Withdrawn");
            } else {
                cellView.lbl_Status.setText("");
            }
            if (appointment.getBadgeNumber() < 1) {
                // Badge view hide when count is less than 1
                cellView.lbl_BadgeNumber.setVisibility(View.INVISIBLE);
                cellView.mViewStatusBar.setVisibility(View.INVISIBLE);
            } else {
                cellView.lbl_BadgeNumber.setVisibility(View.VISIBLE);
                cellView.mViewStatusBar.setVisibility(View.VISIBLE);
                // Show Badge Count
                Integer badgeCount = appointment.getBadgeNumber();
                cellView.lbl_BadgeNumber.setText(String.valueOf(badgeCount));
            }

            cellView.lbl_Day.setTextColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));
            cellView.lbl_Month.setTextColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));
            if (appointment instanceof JGGServiceModel) {
                cellView.lbl_Day.setTextColor(ContextCompat.getColor(getContext(), R.color.JGGGreen));
                cellView.lbl_Month.setTextColor(ContextCompat.getColor(getContext(), R.color.JGGGreen));
            } else if (appointment instanceof JGGEventModel){
                cellView.lbl_Day.setTextColor(ContextCompat.getColor(getContext(), R.color.JGGPurple));
                cellView.lbl_Month.setTextColor(ContextCompat.getColor(getContext(), R.color.JGGPurple));
            }
        }
    }

    @Override
    public int getItemCount() {
        int total = 0;
        for(ArrayList<JGGAppointmentBaseModel> arrayList : this.sections.values())
            total += arrayList.size() + 1;
        return total;
    }

    public Object getItem(int position) {
        for(Object section : this.sections.keySet()) {
            ArrayList<JGGAppointmentBaseModel> arrayList = sections.get(section);
            int size = arrayList.size() + 1;

            // check if position inside this section
            if(position == 0) return section;
            if(position < size) return arrayList.get(position - 1);

            // otherwise jump into next section
            position -= size;
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        int type = 1;
        for(Object section : this.sections.keySet()) {
            ArrayList<JGGAppointmentBaseModel> arrayList = sections.get(section);
            int size = arrayList.size() + 1;

            // check if position inside this section
            if(position == 0) return TYPE_SECTION_HEADER;
            if(position < size) return 1;

            // otherwise jump into next section
            position -= size;
            type += arrayList.size();
        }
        return -1;

    }

    private Context getContext() {
        return this.mContext;
    }

    public class AppointmentListView extends RecyclerView.ViewHolder {

        TextView lbl_Day;
        TextView lbl_Month;
        TextView lbl_Title;
        TextView lbl_Comment;
        TextView lbl_Status;
        TextView lbl_BadgeNumber;
        ImageView img_Profile;
        RelativeLayout mViewStatusBar;

        public AppointmentListView(View itemView) {
            super(itemView);

            lbl_Day = (TextView) itemView.findViewById(R.id.lblDay);
            lbl_Month = (TextView) itemView.findViewById(R.id.lblMonth);
            lbl_Title = (TextView) itemView.findViewById(R.id.lblTitle);
            lbl_Comment = (TextView) itemView.findViewById(R.id.lblComment);
            lbl_Status = (TextView) itemView.findViewById(R.id.lblStatus);
            lbl_BadgeNumber = (TextView) itemView.findViewById(R.id.lblBadgeCount);
            mViewStatusBar = (RelativeLayout) itemView.findViewById(R.id.appointment_statusLayout);
        }
    }


}
