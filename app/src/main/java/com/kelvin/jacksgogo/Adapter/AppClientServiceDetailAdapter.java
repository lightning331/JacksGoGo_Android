package com.kelvin.jacksgogo.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.Fragments.Appointments.AppClientServiceDetailFragment;
import com.kelvin.jacksgogo.R;
import com.makeramen.roundedimageview.RoundedImageView;


import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by PUMA on 11/3/2017.
 */

public class AppClientServiceDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    AppClientServiceDetailFragment mContext;
    Boolean expandState = false; // 1: expandable, 0: not expandable

    final int ITEM_COUNT = 5;

    public AppClientServiceDetailAdapter(AppClientServiceDetailFragment context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case 1:
                View nextStepTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_info_next_step_title_cell, parent, false);
                NextStepTitleViewHolder nextStepTitleViewHolder = new NextStepTitleViewHolder(nextStepTitleView);
                nextStepTitleViewHolder.title.setText("Waiting for service provider...");
                return nextStepTitleViewHolder;
            case 2:
                View footerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_info_footer_cell, parent, false);
                DetailInfoHooterViewHolder footerViewHolder = new DetailInfoHooterViewHolder(footerView);
                footerViewHolder.title.setText("Job posted on 7 Jul, 2017 8:15PM");
                return footerViewHolder;
            case 3:
                View expandableView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_info_expandable_header_view, parent, false);
                DetailInfoExpandableViewHolder expandableViewHolder = new DetailInfoExpandableViewHolder(expandableView);

                expandableViewHolder.bind(viewType, listener);

                return expandableViewHolder;
            case 4:
                View sectionTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_info_user_name_rating_cell, parent, false);
                UserAvatarNameRateViewHolder userAvatarNameRateViewHolder = new UserAvatarNameRateViewHolder(sectionTitleView);
                return userAvatarNameRateViewHolder;
            case 5:
                View typeTitleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_type_header_view, parent, false);
                SectionTitleViewHolder sectionTitleViewHolder = new SectionTitleViewHolder(typeTitleView);
                sectionTitleViewHolder.title.setText("Invited service provider:");
                return sectionTitleViewHolder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return ITEM_COUNT;
    }

    @Override
    public int getItemViewType(int position) {

        if (position %5 == 0) return 1;
        else if (position % 4 == 0) return 2;
        else if (position % 3 == 0) return 3;
        else if (position %2 == 0 ) return 4;
        else return 5;
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

class NextStepTitleViewHolder extends RecyclerView.ViewHolder {

    TextView title;
    RoundedImageView avatar;
    LinearLayout markLine;

    public NextStepTitleViewHolder(View itemView) {
        super(itemView);

        this.title = itemView.findViewById(R.id.lbl_next_step_title);
        this.avatar = itemView.findViewById(R.id.next_step_img_avatar);
        this.markLine = itemView.findViewById(R.id.next_step_mark_line);
    }
}

class SectionTitleViewHolder extends RecyclerView.ViewHolder {

    public void setTitle(TextView title) {
        this.title = title;
    }

    TextView title;

    public SectionTitleViewHolder(View itemView) {
        super(itemView);

        this.title = itemView.findViewById(R.id.lbl_detail_type_header);
    }
}

class UserAvatarNameRateViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    MaterialRatingBar ratingBar;
    RoundedImageView avatar;

    public UserAvatarNameRateViewHolder(View itemView) {
        super(itemView);

        this.name = itemView.findViewById(R.id.lbl_username);
        this.ratingBar = itemView.findViewById(R.id.user_ratingbar);
        this.avatar = itemView.findViewById(R.id.img_avatar);
    }
}

class DetailInfoExpandableViewHolder extends RecyclerView.ViewHolder {

    LinearLayout btnExpand;

    public DetailInfoExpandableViewHolder(View itemView) {
        super(itemView);

        this.btnExpand = itemView.findViewById(R.id.btn_expand);
    }

    public void bind(final int position, final AppClientServiceDetailAdapter.OnItemClickListener listener) {

        btnExpand.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                listener.onItemClick(position);
            }
        });
    }
}

class DetailInfoHooterViewHolder extends RecyclerView.ViewHolder {

    TextView title;

    public DetailInfoHooterViewHolder(View itemView) {
        super(itemView);

        this.title = itemView.findViewById(R.id.detail_info_footer_title);
    }
}
