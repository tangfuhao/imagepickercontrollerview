package com.likeios.fuhao.tang.imagepickercontrollerviewlib.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.likeios.fuhao.tang.imagepickercontrollerviewlib.R;
import com.likeios.fuhao.tang.imagepickercontrollerviewlib.model.PhotoInfo;
import com.likeios.fuhao.tang.imagepickercontrollerviewlib.view.ImageDisplayerInterface;

import java.util.List;


/**
 * Created by tang on 16/1/26.
 */
public class PhotoListAdapter extends ViewHolderAdapter<PhotoListAdapter.PhotoViewHolder, PhotoInfo>{
    private int mWidth;
    private int mRowWidth;
    private Context mContext;
    private ImageDisplayerInterface mImageDisplayerInterface;

    public void setmImageDisplayerInterface(ImageDisplayerInterface mImageDisplayerInterface) {
        this.mImageDisplayerInterface = mImageDisplayerInterface;
    }

    public PhotoListAdapter(Context Context, List<PhotoInfo> list, int screenWidth) {
        super(Context, list);
        this.mWidth = screenWidth;
        this.mRowWidth = mWidth /3;
        this.mContext = Context;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = inflate(R.layout.adapter_photo_list_item, parent);
        setHeight(view);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        PhotoInfo photoInfo = getDatas().get(position);

        String path = null;
        if (photoInfo != null) {
            path = photoInfo.getPhotoPath();
        }

        holder.mIvThumb.setImageResource(R.drawable.default_cover_photo);
        Drawable defaultDrawable = mContext.getResources().getDrawable(R.drawable.default_cover_photo);
        if(mImageDisplayerInterface!=null)
            mImageDisplayerInterface.displayImage(mContext, path, holder.mIvThumb, defaultDrawable, mRowWidth, mRowWidth);
        holder.mIvCheck.setVisibility(View.GONE);
    }

    private void setHeight(final View convertView) {
        int height = mWidth / 3 - 8;
        convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
    }

    public static class PhotoViewHolder extends ViewHolderAdapter.ViewHolder {

        public ImageView mIvThumb;
        public ImageView mIvCheck;
        View mView;
        public PhotoViewHolder(View view) {
            super(view);
            mView = view;
            mIvThumb = (ImageView) view.findViewById(R.id.photo_thumb);
            mIvCheck = (ImageView) view.findViewById(R.id.photo_state);
        }
    }
}
