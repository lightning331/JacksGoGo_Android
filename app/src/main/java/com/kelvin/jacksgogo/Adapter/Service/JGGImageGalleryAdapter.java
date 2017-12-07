package com.kelvin.jacksgogo.Adapter.Service;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kelvin.jacksgogo.R;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.impl.OnItemClickListener;

import java.util.List;

public class JGGImageGalleryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private int itemSize;
    private OnItemClickListener mItemClickListener;

    private List<AlbumFile> mAlbumFiles;

    public JGGImageGalleryAdapter(Context context, int itemSize, OnItemClickListener itemClickListener) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.itemSize = itemSize;
        this.mItemClickListener = itemClickListener;
    }

    public void notifyDataSetChanged(List<AlbumFile> imagePathList) {
        this.mAlbumFiles = imagePathList;
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position < mAlbumFiles.size()) {
            return AlbumFile.TYPE_IMAGE;
        } else {
            return AlbumFile.TYPE_INVALID;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(mInflater.inflate(R.layout.item_content_image, parent, false), itemSize, mItemClickListener);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < mAlbumFiles.size()) {
            ImageViewHolder viewHolder = ((ImageViewHolder) holder);
            viewHolder.setData(mAlbumFiles.get(position));
        } else {
            ImageViewHolder imageViewHolder = ((ImageViewHolder) holder);
            imageViewHolder.mIvImage.setImageResource(R.mipmap.icon_add_photo_green);
        }
    }

    @Override
    public int getItemCount() {
        return mAlbumFiles == null ? 0 : mAlbumFiles.size() + 1;
    }

    private static class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final int itemSize;
        private final OnItemClickListener mItemClickListener;

        private ImageView mIvImage;

        ImageViewHolder(View itemView, int itemSize, OnItemClickListener itemClickListener) {
            super(itemView);
            itemView.getLayoutParams().height = itemSize;

            this.itemSize = itemSize;
            this.mItemClickListener = itemClickListener;

            mIvImage = (ImageView) itemView.findViewById(R.id.iv_album_content_image);
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) mIvImage.getLayoutParams();
            marginParams.setMargins(10, 10, 10, 10);

            itemView.setOnClickListener(this);
        }

        public void setData(AlbumFile albumFile) {
            Album.getAlbumConfig().
                    getAlbumLoader().
                    loadAlbumFile(mIvImage, albumFile, itemSize, itemSize);
        }

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }
}
