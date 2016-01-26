package com.likeios.fuhao.tang.imagepickercontrollerviewlib.utils;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import com.likeios.fuhao.tang.imagepickercontrollerviewlib.model.PhotoAlbum;
import com.likeios.fuhao.tang.imagepickercontrollerviewlib.model.PhotoInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tang on 16/1/25.
 */
public class PhotoListScanner {
    public static ArrayList<PhotoAlbum> getAllPhotoFolder(Context context) {
        final String[] projectionPhotos = {
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.ORIENTATION,
                MediaStore.Images.Thumbnails.DATA
        };
        ArrayList<PhotoAlbum> photoAlbumList = new ArrayList<>();
        HashMap<Integer, PhotoAlbum> bucketMap = new HashMap<>();
        Cursor cursor = null;

        PhotoAlbum mPhotoAlbum = new PhotoAlbum(0,"所有照片",new ArrayList<PhotoInfo>());
        photoAlbumList.add(0, mPhotoAlbum);

        try {
            cursor = MediaStore.Images.Media.query(context.getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    , projectionPhotos, "", null, MediaStore.Images.Media.DATE_TAKEN + " DESC");
            if (cursor != null) {
                int bucketNameColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME);
                final int bucketIdColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID);

                while (cursor.moveToNext()) {
                    int bucketId = cursor.getInt(bucketIdColumn);
                    String bucketName = cursor.getString(bucketNameColumn);
                    final int dataColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                    final int imageIdColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                    final int imageId = cursor.getInt(imageIdColumn);
                    final String path = cursor.getString(dataColumn);

                    File file = new File(path);
                    if (file.exists() && file.length() > 0 ) {
                        PhotoInfo photoInfo = new PhotoInfo();
                        photoInfo.setPhotoId(imageId);
                        photoInfo.setPhotoPath(path);
                        if (mPhotoAlbum.getCoverPhoto() == null) {
                            mPhotoAlbum.setCoverPhoto(photoInfo);
                        }
                        mPhotoAlbum.getPhotoInfos().add(photoInfo);
                        PhotoAlbum photoAlbum = bucketMap.get(bucketId);
                        if (photoAlbum == null) {
                            photoAlbum = new PhotoAlbum(bucketId,bucketName,photoInfo,new ArrayList<PhotoInfo>());
                            bucketMap.put(bucketId, photoAlbum);
                            photoAlbumList.add(photoAlbum);
                        }
                        photoAlbum.getPhotoInfos().add(photoInfo);
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("Error!",ex.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return photoAlbumList;
    }
}
