package com.kelvin.jacksgogo.Adapter.GoClub_Event;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.R;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

/**
 * Created by storm on 4/21/2018.
 */

public class GcAdminAdapter extends RecyclerView.Adapter {

    private Context mContext;

    public GcAdminAdapter(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.cell_gc_admin, null, false);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new GcAttendeesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        GcAttendeesViewHolder viewHolder = (GcAttendeesViewHolder) holder;
        viewHolder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onPlusItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onPlusItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class GcAttendeesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_avatar) RoundedImageView img_avatar;
        @BindView(R.id.txt_name) TextView txt_name;
        @BindView(R.id.ratingbar) MaterialRatingBar ratingBar;
        @BindView(R.id.ll_plus)        LinearLayout ll_plus;
        @BindView(R.id.btn_plus)        Button btnPlus;

        public GcAttendeesViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
