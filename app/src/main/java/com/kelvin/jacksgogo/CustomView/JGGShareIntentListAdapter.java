package com.kelvin.jacksgogo.CustomView;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.kelvin.jacksgogo.CustomView.Views.JGGShareIntentDialog;
import com.kelvin.jacksgogo.R;

import java.util.List;

public class JGGShareIntentListAdapter extends BaseAdapter {
    private Context context;
    private String shareLink;
    private List<ResolveInfo> listResolve;
    private PackageManager pm;
    private JGGShareIntentDialog dialog;
    private Typeface typeface = null;
    private int     currentPosition = -1;

    public JGGShareIntentListAdapter(Context context, JGGShareIntentDialog dialog, String shareLink) {
        this.context = context;
        this.dialog = dialog;
        this.shareLink = shareLink;
        pm = context.getPackageManager();
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareLink);
        listResolve = pm.queryIntentActivities(sendIntent, 0);

        for (int i = 0; i < listResolve.size(); i++) {
            if (listResolve.get(i).loadLabel(pm).toString().equals(context.getResources().getString(R.string.app_name))) {
                listResolve.remove(i);
            }
        }

    }

    public void setTypeface(Typeface tf) {
        typeface = tf;
    }

    @Override
    public int getCount() {
        if (listResolve == null)
            return 0;
        return listResolve.size();
    }

    @Override
    public Object getItem(int position) {
        return listResolve.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setCurrentPosition(int position) {
        currentPosition = position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.jgg_item_share_intent, parent,
                    false);
            holder.image = (ImageView) convertView
                    .findViewById(R.id.share_intent_item_icon);
            holder.text = (TextView) convertView
                    .findViewById(R.id.share_intent_item_text);
            if (typeface != null) {
                holder.text.setTypeface(typeface);
            }

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ResolveInfo info = listResolve.get(position);
        Drawable icon = info.loadIcon(pm);
        holder.image.setImageDrawable(icon);
        holder.text.setText(info.loadLabel(pm));
        final ActivityInfo activityInfo = info.activityInfo;
        final ComponentName componentName = new ComponentName(
                activityInfo.applicationInfo.packageName, activityInfo.name);

        if (position == currentPosition) {
            convertView.setBackgroundColor(context.getResources().getColor(R.color.JGGYellow));
        }else{
            convertView.setBackgroundColor(context.getResources().getColor(R.color.JGGWhite));
        }

        return convertView;
    }

    public void toggleSend(int position) {
        final ResolveInfo info = listResolve.get(position);
        final ActivityInfo activityInfo = info.activityInfo;
        final ComponentName componentName = new ComponentName(
                activityInfo.applicationInfo.packageName, activityInfo.name);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.setClassName(activityInfo.packageName, activityInfo.name);
        intent.putExtra(Intent.EXTRA_TEXT, shareLink);
        intent.setComponent(componentName);
        context.startActivity(intent);
    }

    private class ViewHolder {
        public ImageView image;
        public TextView text;
    }

}
