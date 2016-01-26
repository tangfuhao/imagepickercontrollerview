package com.likeios.fuhao.tang.imagepickercontrollerviewlib.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.likeios.fuhao.tang.imagepickercontrollerviewlib.R;
import com.likeios.fuhao.tang.imagepickercontrollerviewlib.model.PhotoAlbum;
import com.likeios.fuhao.tang.imagepickercontrollerviewlib.model.PhotoInfo;
import com.likeios.fuhao.tang.imagepickercontrollerviewlib.view.ImageDisplayerInterface;

import java.util.List;

/**
 * Created by tang on 16/1/26.
 */
public class AlbumListAdapter extends ViewHolderAdapter<AlbumListAdapter.AlbumViewHolder, PhotoAlbum>{
    private PhotoAlbum mSelectAlbum;
    private Context mContext;
    private ImageDisplayerInterface mImageDisplayerInterface;

    public void setmImageDisplayerInterface(ImageDisplayerInterface mImageDisplayerInterface) {
        this.mImageDisplayerInterface = mImageDisplayerInterface;
    }

    public AlbumListAdapter(Context context, List<PhotoAlbum> list) {
        super(context, list);
        this.mContext = context;
    }

    @Override
    public AlbumViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflate(R.layout.adapter_album_list_item, parent);
        return new AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AlbumViewHolder holder, int position) {
        PhotoAlbum mPhotoAlbum = getDatas().get(position);

        String path = null;
        PhotoInfo photoInfo = mPhotoAlbum.getCoverPhoto();
        if (photoInfo != null) {
            path = photoInfo.getPhotoPath();
        }
        holder.mAlbumCover.setImageResource(R.drawable.default_cover_photo);
        Drawable defaultDrawable = mContext.getResources().getDrawable(R.drawable.default_cover_photo);
        if(mImageDisplayerInterface!=null)
            mImageDisplayerInterface.displayImage(mContext, path, holder.mAlbumCover, defaultDrawable, 200, 200);

        holder.mAlbumName.setText(mPhotoAlbum.getName());
        int size = 0;
        if ( mPhotoAlbum.getPhotoInfos() != null ) {
            size = mPhotoAlbum.getPhotoInfos().size();
        }
        holder.mPhotoCount.setText(String.valueOf(size));
        holder.mAlbumState.setVisibility(mSelectAlbum == mPhotoAlbum || (mSelectAlbum == null && position == 0) ? View.VISIBLE : View.GONE);
    }

    public void setSelectFolder(PhotoAlbum photoAlbum) {
        this.mSelectAlbum = photoAlbum;
    }

    public PhotoAlbum getSelectFolder() {
        return mSelectAlbum;
    }

    static class AlbumViewHolder extends ViewHolderAdapter.ViewHolder {
        ImageView mAlbumCover;
        ImageView mAlbumState;
        TextView mAlbumName;
        TextView mPhotoCount;
        View mView;
        public AlbumViewHolder(View view) {
            super(view);
            this.mView = view;
            mAlbumCover = (ImageView) view.findViewById(R.id.album_cover);
            mAlbumName = (TextView) view.findViewById(R.id.album_name);
            mPhotoCount = (TextView) view.findViewById(R.id.album_phpto_count);
            mAlbumState = (ImageView) view.findViewById(R.id.album_state);
        }
    }
}
