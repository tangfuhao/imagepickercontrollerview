package com.likeios.fuhao.tang.imagepickercontrollerviewlib.model;

import java.util.ArrayList;

/**
 * Created by tang on 16/1/25.
 *
 */
public class PhotoAlbum {
    private int id;
    private String name;
    private ArrayList<PhotoInfo> photoInfos;
    private PhotoInfo coverPhoto;

    public PhotoAlbum() {
    }

    public PhotoAlbum(int id, String name, ArrayList<PhotoInfo> photoInfos) {
        this.id = id;
        this.name = name;
        this.photoInfos = photoInfos;
    }

    public PhotoAlbum(int id, String name, PhotoInfo coverPhoto, ArrayList<PhotoInfo> photoInfos) {
        this.id = id;
        this.name = name;
        this.photoInfos = photoInfos;
        this.coverPhoto = coverPhoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<PhotoInfo> getPhotoInfos() {
        return photoInfos;
    }

    public void setPhotoInfos(ArrayList<PhotoInfo> photoInfos) {
        this.photoInfos = photoInfos;
    }

    public PhotoInfo getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(PhotoInfo coverPhoto) {
        this.coverPhoto = coverPhoto;
    }
}
