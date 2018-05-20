package com.kelvin.jacksgogo.Adapter.GoClub_Event;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kelvin.jacksgogo.Activities.GoClub_Event.GoClubDetailActivity;
import com.kelvin.jacksgogo.R;
import com.kelvin.jacksgogo.Utils.JGGAppManager;
import com.kelvin.jacksgogo.Utils.Models.GoClub_Event.JGGGoClubModel;
import com.kelvin.jacksgogo.Utils.Models.Jobs_Services_Events.JGGCategoryModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JoinedGoClubAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<JGGGoClubModel> mGoClubs;
    private ArrayList<JGGGoClubModel> filteredClubs = new ArrayList<>();
    private Map<String, ArrayList<JGGGoClubModel>> filteredCategoryClubs;

    public JoinedGoClubAdapter(Context context, ArrayList<JGGGoClubModel> goClubs) {
        mContext = context;
        mGoClubs = goClubs;

        sortCategory();
        notifyDataSetChanged();
    }

    public void refresh(ArrayList<JGGGoClubModel> goClubs) {
        mGoClubs = goClubs;

        sortCategory();
        notifyDataSetChanged();
    }

    private void sortCategory() {
        if (mGoClubs.size() > 0) {
            Map<String, ArrayList<JGGGoClubModel>> categories = new HashMap<>();

            for (JGGGoClubModel club : mGoClubs) {
                String categoryId = club.getCategoryID();
                ArrayList<JGGGoClubModel> clubs = categories.get(categoryId);
                if (clubs == null) {
                    clubs = new ArrayList<>();
                    categories.put(categoryId, clubs);
                }
                clubs.add(club);
            }
            filteredCategoryClubs = categories;
        } else {
            filteredCategoryClubs = null;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cell_joined_go_clubs, parent, false);

        JoinedGoClubViewHolder joined = new JoinedGoClubViewHolder(view);
        return joined;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        List keys = new ArrayList(filteredCategoryClubs.keySet());
        String categoryId = keys.get(position).toString();
        ArrayList<JGGCategoryModel> allCategories = JGGAppManager.getInstance().getCategories();
        JGGCategoryModel selectedCategory = null;
        for (JGGCategoryModel cat : allCategories) {
            if (cat.getID().equals(categoryId)) {
                selectedCategory = cat;
                filteredClubs = filteredCategoryClubs.get(categoryId);
                break;
            }
        }
        if (selectedCategory != null) {
            JoinedGoClubViewHolder viewHolder = (JoinedGoClubViewHolder) holder;
            viewHolder.setCategory(selectedCategory);
            viewHolder.setClubs(filteredClubs);
        }
    }

    @Override
    public int getItemCount() {
        if (filteredCategoryClubs != null) {
            int count = filteredCategoryClubs.size();
            return count;
        } else {
            return 0;
        }
    }

    public class JoinedGoClubViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView mRecyclerView;
        private ImageView imgCategory;
        private TextView lblCategory;

        public JoinedGoClubViewHolder(View itemView) {
            super(itemView);

            mRecyclerView = itemView.findViewById(R.id.joined_recycler_view);
            imgCategory = itemView.findViewById(R.id.img_category);
            lblCategory = itemView.findViewById(R.id.lbl_category_name);

            if (mRecyclerView != null) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false));
            }
        }

        public void setClubs(final ArrayList<JGGGoClubModel> clubs) {
            GoClubHorizontalAdapter adapter = new GoClubHorizontalAdapter(mContext, clubs);
            adapter.setOnItemClickListener(new GoClubHorizontalAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    JGGAppManager.getInstance().setSelectedClub(clubs.get(position));
                    Intent intent = new Intent(mContext, GoClubDetailActivity.class);
                    mContext.startActivity(intent);
                }
            });
            mRecyclerView.setAdapter(adapter);
        }

        public void setCategory(JGGCategoryModel category) {
            // Category
            Picasso.with(mContext)
                    .load(category.getImage())
                    .placeholder(null)
                    .into(imgCategory);
            lblCategory.setText(category.getName());
        }
    }
}
