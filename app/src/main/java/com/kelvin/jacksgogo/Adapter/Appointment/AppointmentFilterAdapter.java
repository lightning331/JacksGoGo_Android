package com.kelvin.jacksgogo.Adapter.Appointment;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kelvin.jacksgogo.CustomView.Views.SectionTitleView;
import com.kelvin.jacksgogo.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by PUMA on 11/1/2017.
 */


public class AppointmentFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList dataSet;
    public final Map<String, ArrayList> sections = new LinkedHashMap<>();
    public final ArrayAdapter<String> headers;
    public final static int TYPE_SECTION_HEADER = 0;

    Context mContext;

    public AppointmentFilterAdapter(Context context) {
        this.mContext = context;
        headers = new ArrayAdapter<String>(context, R.layout.view_section_title); // this is the header desing page.
    }

    public void addSection(String section, ArrayList arrayList) {
        this.headers.add(section);
        this.sections.put(section, arrayList);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_SECTION_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_section_title, parent, false);
            return new SectionTitleView(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_app_filter_option, parent, false);
            return new AppointmentFilterListView(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Object itemData = getItem(position);

        if (position == 0) {
            SectionTitleView sectionView = (SectionTitleView) holder;
            sectionView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
            sectionView.txtTitle.setTypeface(Typeface.create("mulibold", Typeface.BOLD));
            sectionView.setTitle((String)itemData);
        } else {
            AppointmentFilterAdapter.AppointmentFilterListView cellView = (AppointmentFilterAdapter.AppointmentFilterListView) holder;
            cellView.title.setText((String) itemData);

            cellView.bind(position, listener);
        }
    }

    @Override
    public int getItemCount() {
        int total = 0;
        for(ArrayList arrayList : this.sections.values())
            total += arrayList.size() + 1;
        return total;
    }

    public Object getItem(int position) {
        for(Object section : this.sections.keySet()) {
            ArrayList arrayList = sections.get(section);
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
            ArrayList arrayList = sections.get(section);
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

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class AppointmentFilterListView extends RecyclerView.ViewHolder {

        TextView title;
        RoundedImageView imageView;
        Boolean isSelected = false;

        public AppointmentFilterListView(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.lblFilterTitle);
            imageView = (RoundedImageView) itemView.findViewById(R.id.view_filter_bg);
        }

        public void bind(final int position, final OnItemClickListener listener) {

            title.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    if (isSelected) {
                        title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGBlack));
                        imageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGYellow));
                        imageView.setBorderColor(ContextCompat.getColor(mContext, R.color.JGGYellow));
                        imageView.setCornerRadius((float) 5);
                        imageView.setOval(false);
                        imageView.mutateBackground(true);
                    } else {
                        title.setTextColor(ContextCompat.getColor(mContext, R.color.JGGOrange));
                        imageView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.JGGWhite));
                        imageView.setBorderColor(ContextCompat.getColor(mContext, R.color.JGGOrange));
                        imageView.setBorderWidth((float) 4);
                        imageView.setCornerRadius((float) 5);
                        imageView.setOval(false);
                        imageView.mutateBackground(true);
                    }
                    isSelected = !isSelected;
                    listener.onItemClick(position);
                }
            });
        }
    }
}
