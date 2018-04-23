package com.kelvin.jacksgogo.Adapter.GoClub_Event;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by storm on 4/21/2018.
 */

public class GcAttendeesAdapter extends RecyclerView.Adapter implements View.OnClickListener {

    private Context mContext;

    public GcAttendeesAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cell_gc_attendee, null, false);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new GcAttendeesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GcAttendeesViewHolder viewHolder = (GcAttendeesViewHolder) holder;
        viewHolder.itemView.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public void onClick(View view) {
        listener.onItemClick();
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class GcAttendeesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_avatar) RoundedImageView img_avatar;
        @BindView(R.id.txt_title) TextView txt_title;
        @BindView(R.id.ratingbar) MaterialRatingBar ratingBar;
        @BindView(R.id.txt_role) TextView txt_role;
        @BindView(R.id.txt_demote) TextView txt_demote;

        public GcAttendeesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
