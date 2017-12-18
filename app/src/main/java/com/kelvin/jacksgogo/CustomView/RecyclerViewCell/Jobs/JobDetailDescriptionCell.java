package com.kelvin.jacksgogo.CustomView.RecyclerViewCell.Jobs;

import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kelvin.jacksgogo.CustomView.CustomTypefaceSpan;
import com.kelvin.jacksgogo.R;

/**
 * Created by PUMA on 11/13/2017.
 */

public class JobDetailDescriptionCell extends RecyclerView.ViewHolder {

    public ImageView descriptionImage;
    public TextView description;
    public TextView title;
    public LinearLayout titleLayout;

    public JobDetailDescriptionCell(View itemView) {
        super(itemView);

        descriptionImage = itemView.findViewById(R.id.img_description);
        description = itemView.findViewById(R.id.lbl_description);
        title = itemView.findViewById(R.id.lbl_job_detail_title);
        titleLayout = itemView.findViewById(R.id.job_detail_title_layout);
    }

    public TextView getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description.setText(description);
    }

    public TextView getTitle() {
        return title;
    }

    public void setTitle(String title, boolean bold) {
        if (bold) {
            this.title.setVisibility(View.VISIBLE);
            Typeface muliBold = Typeface.create("mulibold", Typeface.BOLD);
            SpannableString spannableString = new SpannableString(title);
            spannableString.setSpan(new CustomTypefaceSpan("", muliBold), 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            this.title.append(spannableString);
        } else {
            this.title.setText(title);
        }
    }
}
