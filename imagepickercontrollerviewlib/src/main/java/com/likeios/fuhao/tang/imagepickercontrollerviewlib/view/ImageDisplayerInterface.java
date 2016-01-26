package com.likeios.fuhao.tang.imagepickercontrollerviewlib.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by tang on 16/1/26.
 */
public interface ImageDisplayerInterface {
    void displayImage(Context mContext, String path, ImageView mThumb, Drawable defaultDrawable, int width, int height);
}
