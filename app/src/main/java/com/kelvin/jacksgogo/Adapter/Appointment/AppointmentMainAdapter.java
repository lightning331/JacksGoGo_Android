package com.kelvin.jacksgogo.Adapter.Appointment;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Appointment.AppointmentMainCell;
import com.kelvin.jacksgogo.CustomView.Views.HeaderTitleView;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGAppointmentModel;
import com.kelvin.jacksgogo.Utils.Models.User.JGGUserProfileModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by PUMA on 10/31/2017.
 * https://rajeshandroiddeveloper.blogspot.jp/2013/05/sectioned-list-view-list-with-headers.html
 */

public class AppointmentMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private JGGUserProfileModel currentUser;
    private ArrayList<JGGAppointmentModel> dataSet;
    public final Map<String, ArrayList<JGGAppointmentModel>> sections = new LinkedHashMap<>();
    public final ArrayAdapter<String> headers;
    public final static int TYPE_SECTION_HEADER = 0;
    public final static int TYPE_INCOMING = 1;
    public final static int TYPE_OUTGOING = 2;

    Context mContext;

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(JGGAppointmentModel appointment);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public AppointmentMainAdapter(Context context) {
        this.mContext = context;
        currentUser = JGGAppManager.getInstance().getCurrentUser();
        headers = new ArrayAdapter<String>(context, R.layout.view_section_title); // this is the header desing page.
    }

    public void addSection(String section, ArrayList<JGGAppointmentModel> arrayList) {
        this.headers.add(section);
        this.sections.put(section, arrayList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_SECTION_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            return new HeaderTitleView(view);
        } else if (viewType == TYPE_OUTGOING) {
            View jobView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_outgoing_job, parent, false);
            return new AppointmentMainCell(mContext, jobView);
        } else if (viewType == TYPE_INCOMING) {
            View serviceView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_incoming_job, parent, false);
            return new AppointmentMainCell(mContext, serviceView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final Object itemData = getItem(position);

        if (itemData instanceof String) {
            // Section title
            HeaderTitleView sectionView = (HeaderTitleView) holder;
            sectionView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            sectionView.setTitle((String) itemData);
        } else if (itemData instanceof JGGAppointmentModel) {
            // Appointment main cell
            AppointmentMainCell cellView = (AppointmentMainCell) holder;

            final JGGAppointmentModel appointment = (JGGAppointmentModel) itemData;

            cellView.setAppointment(appointment);

            cellView.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(appointment);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        int total = 0;
        for (ArrayList<JGGAppointmentModel> arrayList : this.sections.values())
            total += arrayList.size() + 1;
        return total;
    }

    @Override
    public int getItemViewType(int position) {
        for (Object section : this.sections.keySet()) {
            ArrayList<JGGAppointmentModel> arrayList = sections.get(section);
            int size = arrayList.size() + 1;

            // check if position inside this section
            if (position == 0)
                return TYPE_SECTION_HEADER;
            if (position < size) {
                JGGAppointmentModel jobModel = arrayList.get(position - 1);
                if (jobModel.getUserProfileID().equals(currentUser.getID()))
                    return TYPE_OUTGOING;
                else
                    return TYPE_INCOMING;
            }

            // otherwise jump into next section
            position -= size;
        }
        return -1;
    }

    public Object getItem(int position) {
        for (Object section : this.sections.keySet()) {
            ArrayList<JGGAppointmentModel> arrayList = sections.get(section);
            int size = arrayList.size() + 1;

            // check if position inside this section
            if (position == 0) return section;
            if (position < size) return arrayList.get(position - 1);

            // otherwise jump into next section
            position -= size;
        }
        return null;
    }

    // Search Filter
    public void setFilter(ArrayList<JGGAppointmentModel> filteredArray) {
        dataSet = new ArrayList<>();
        dataSet.addAll(filteredArray);
        notifyDataSetChanged();
    }
}
