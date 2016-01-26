package com.likeios.fuhao.tang.imagepickercontrollerviewlib.view;

import com.likeios.fuhao.tang.imagepickercontrollerviewlib.model.PhotoInfo;

/**
 * Created by tang on 16/1/26.
 */
public interface ImagePickerStateInterface {

    void onPickImage(PhotoInfo info);
    void onCanclePicke();
}
