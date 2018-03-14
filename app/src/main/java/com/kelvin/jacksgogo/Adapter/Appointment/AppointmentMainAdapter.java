package com.kelvin.jacksgogo.Adapter.Appointment;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment.ApptHistoryListCell;
import com.kelvin.jacksgogo.CustomView.Views.SectionTitleView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGJobModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.kelvin.jacksgogo.Utils.API.JGGAppManager.currentUser;
import static com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentBaseModel.appointmentDay;
import static com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentBaseModel.appointmentMonth;
import static com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentBaseModel.appointmentMonthDate;

/**
 * Created by PUMA on 10/31/2017.
 * https://rajeshandroiddeveloper.blogspot.jp/2013/05/sectioned-list-view-list-with-headers.html
 */

public class AppointmentMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<JGGJobModel> dataSet;
    public final Map<String, ArrayList<JGGJobModel>> sections = new LinkedHashMap<>();
    public final ArrayAdapter<String> headers;
    public final static int TYPE_SECTION_HEADER = 0;
    public final static int TYPE_SERVICE = 1;
    public final static int TYPE_JOB = 2;

    Context mContext;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position, Object object);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public AppointmentMainAdapter(Context context) {
        this.mContext = context;
        headers = new ArrayAdapter<String>(context, R.layout.view_section_title); // this is the header desing page.
    }

    public void addSection(String section, ArrayList<JGGJobModel> arrayList) {
        this.headers.add(section);
        this.sections.put(section, arrayList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_SECTION_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            return new SectionTitleView(view);
        } else if (viewType == TYPE_JOB) {
            View jobView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_app_job_history, parent, false);
            return new ApptHistoryListCell(jobView);
        } else if (viewType == TYPE_SERVICE) {
            View serviceView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_app_service_history, parent, false);
            return new ApptHistoryListCell(serviceView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final Object itemData = getItem(position);

        if (itemData instanceof String) {
            // RecyclerView Header
            SectionTitleView sectionView = (SectionTitleView) holder;
            sectionView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            sectionView.setTitle((String) itemData);
        } else if (itemData instanceof JGGJobModel) {
            // RecyclerView Cell
            ApptHistoryListCell cellView = (ApptHistoryListCell) holder;
            JGGJobModel appointment = (JGGJobModel) itemData;

            cellView.lbl_Title.setText(appointment.getTitle());
            cellView.lbl_Comment.setText(appointment.getDescription());
            String dateString = appointment.getPostOn();
            Date appDay = appointmentMonthDate(dateString);
            cellView.lbl_Day.setText(appointmentDay(appDay));
            cellView.lbl_Month.setText(appointmentMonth(appDay));
            Picasso.with(mContext)
                    .load(appointment.getUserProfile().getUser().getPhotoURL())
                    .placeholder(R.mipmap.icon_profile)
                    .into(cellView.img_Profile);

            if (appointment.getStatus() == 1) {
                cellView.lbl_Status.setText("Cancelled");
            } else if (appointment.getStatus() == 2) {
                cellView.lbl_Status.setText("Withdrawn");
            } else {
                cellView.lbl_Status.setVisibility(View.GONE);
            }

            if (appointment.isRequest()) {
                cellView.lbl_Day.setTextColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));
                cellView.lbl_Month.setTextColor(ContextCompat.getColor(getContext(), R.color.JGGCyan));
            } else {
                cellView.lbl_Day.setTextColor(ContextCompat.getColor(getContext(), R.color.JGGGreen));
                cellView.lbl_Month.setTextColor(ContextCompat.getColor(getContext(), R.color.JGGGreen));
            }
//            else if (appointment instanceof JGGEventModel) {
//                cellView.lbl_Day.setTextColor(ContextCompat.getColor(getContext(), R.color.JGGPurple));
//                cellView.lbl_Month.setTextColor(ContextCompat.getColor(getContext(), R.color.JGGPurple));
//            }

//            if (appointment.getBadgeNumber() < 1) {
//                // Badge view hide when count is less than 1
//                cellView.lbl_BadgeNumber.setVisibility(View.INVISIBLE);
//                cellView.mViewStatusBar.setVisibility(View.INVISIBLE);
//            } else {
//                cellView.lbl_BadgeNumber.setVisibility(View.VISIBLE);
//                cellView.mViewStatusBar.setVisibility(View.VISIBLE);
//                // Show Badge Count
//                Integer badgeCount = appointment.getBadgeNumber();
//                cellView.lbl_BadgeNumber.setText(String.valueOf(badgeCount));
//            }

            //if (appointment.getUserProfileID().equals(currentUser.getID())) {
                cellView.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClick(position, itemData);
                    }
                });
            //}
        }
    }

    @Override
    public int getItemCount() {
        int total = 0;
        for (ArrayList<JGGJobModel> arrayList : this.sections.values())
            total += arrayList.size() + 1;
        return total;
    }

    @Override
    public int getItemViewType(int position) {
        for (Object section : this.sections.keySet()) {
            ArrayList<JGGJobModel> arrayList = sections.get(section);
            int size = arrayList.size() + 1;

            // check if position inside this section
            if (position == 0)
                return TYPE_SECTION_HEADER;
            if (position < size) {
                JGGJobModel jobModel = arrayList.get(position - 1);
                if (jobModel.isRequest())
                    return TYPE_JOB;
                else
                    return TYPE_SERVICE;
            }

            // otherwise jump into next section
            position -= size;
        }
        return -1;
    }

    public Object getItem(int position) {
        for (Object section : this.sections.keySet()) {
            ArrayList<JGGJobModel> arrayList = sections.get(section);
            int size = arrayList.size() + 1;

            // check if position inside this section
            if (position == 0) return section;
            if (position < size) return arrayList.get(position - 1);

            // otherwise jump into next section
            position -= size;
        }
        return null;
    }

    private Context getContext() {
        return this.mContext;
    }

    // Search Filter
    public void setFilter(ArrayList<JGGJobModel> filteredArray) {
        dataSet = new ArrayList<>();
        dataSet.addAll(filteredArray);
        notifyDataSetChanged();
    }
}
