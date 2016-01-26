package com.likeios.fuhao.tang.imagepickercontrollerviewlib.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.likeios.fuhao.tang.imagepickercontrollerviewlib.R;
import com.likeios.fuhao.tang.imagepickercontrollerviewlib.adapter.AlbumListAdapter;
import com.likeios.fuhao.tang.imagepickercontrollerviewlib.adapter.PhotoListAdapter;
import com.likeios.fuhao.tang.imagepickercontrollerviewlib.model.PhotoAlbum;
import com.likeios.fuhao.tang.imagepickercontrollerviewlib.model.PhotoInfo;
import com.likeios.fuhao.tang.imagepickercontrollerviewlib.utils.PhotoListScanner;

import java.util.ArrayList;

/**
 * Created by tang on 16/1/25.
 */
public class ImagePickerView extends RelativeLayout implements AdapterView.OnItemClickListener,View.OnClickListener{
    private ArrayList<PhotoAlbum> mAllPhotoAlbumList;
    private AlbumListAdapter mAlbumListAdapter;

    private ArrayList<PhotoInfo> mPhotoList;
    private PhotoListAdapter mPhotoListAdapter;

    private GridView mPhotoGridView;
    private ListView mPhotoAlbumListView;
    private TextView mEmptyView;

    private TextView mBackButton;
    private TextView mCancleButton;

    private ImagePickerStateInterface mImagePickerStateInterface;

    private static final int NotifyUpdateData_Message = 1;

    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if ( msg.what == NotifyUpdateData_Message ){
                mEmptyView.setVisibility(View.INVISIBLE);
                mPhotoListAdapter.notifyDataSetChanged();
                mAlbumListAdapter.notifyDataSetChanged();
                if (mAllPhotoAlbumList.get(0).getPhotoInfos() == null ||
                        mAllPhotoAlbumList.get(0).getPhotoInfos().size() == 0) {
                    mEmptyView.setVisibility(View.VISIBLE);
                    mEmptyView.setText(R.string.no_photos);
                }
            }
        }
    };

    public ImagePickerView(Context context) {
        super(context);
    }

    public ImagePickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImagePickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ImagePickerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void init(int width,ImageDisplayerInterface mImageDisplayerInterface,ImagePickerStateInterface mImagePickerStateInterface){
        this.mImagePickerStateInterface = mImagePickerStateInterface;

        LayoutInflater mLayoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rootView = mLayoutInflater.inflate(R.layout.image_picker_layout,this);

        mPhotoAlbumListView = (ListView) rootView.findViewById(R.id.photo_catalogue_listview);
        mPhotoAlbumListView.setOnItemClickListener(this);
        mAllPhotoAlbumList = new ArrayList<PhotoAlbum>();
        mAlbumListAdapter = new AlbumListAdapter(getContext(), mAllPhotoAlbumList);
        mAlbumListAdapter.setmImageDisplayerInterface(mImageDisplayerInterface);
        mPhotoAlbumListView.setAdapter(mAlbumListAdapter);

        mPhotoGridView = (GridView) rootView.findViewById(R.id.photo_gridview);
        mPhotoGridView.setOnItemClickListener(this);
        mPhotoList = new ArrayList<PhotoInfo>();
        mPhotoListAdapter = new PhotoListAdapter(getContext(), mPhotoList, width);
        mPhotoListAdapter.setmImageDisplayerInterface(mImageDisplayerInterface);
        mPhotoGridView.setAdapter(mPhotoListAdapter);

        mEmptyView = (TextView) rootView.findViewById(R.id.empty_textview);
        mBackButton = (TextView) rootView.findViewById(R.id.textView1);
        mBackButton.setOnClickListener(this);
        mCancleButton = (TextView) rootView.findViewById(R.id.textView2);
        mCancleButton.setOnClickListener(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if(mAllPhotoAlbumList ==null) return;

        //加载照片
        new Thread() {
            @Override
            public void run() {
                super.run();
                ArrayList<PhotoAlbum> photoAlbumList = PhotoListScanner.getAllPhotoFolder(ImagePickerView.this.getContext());
                mAllPhotoAlbumList.clear();
                mAllPhotoAlbumList.addAll(photoAlbumList);

                mPhotoList.clear();
                if ( photoAlbumList.size() > 0 ) {
                    if ( photoAlbumList.get(0).getPhotoInfos() != null ) {
                        mPhotoList.addAll(photoAlbumList.get(0).getPhotoInfos());
                    }
                }

                refreshAdapter();
            }
        }.start();
    }

    private void refreshAdapter() {
        mHanlder.sendEmptyMessage(1);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if ( parent.getId() == R.id.photo_catalogue_listview ) {
            folderItemClick(position);
        } else {
            photoItemClick(view, position);
        }
    }

    private void SwitchFolderAndFileMode(boolean showFolder){
        mPhotoGridView.setVisibility(showFolder ? View.INVISIBLE : View.VISIBLE);
        mBackButton.setVisibility(showFolder ? View.INVISIBLE : View.VISIBLE);
        mPhotoAlbumListView.setVisibility(showFolder ? View.VISIBLE : View.INVISIBLE);
    }

    private void folderItemClick(int position) {
        SwitchFolderAndFileMode(false);

        mPhotoList.clear();
        PhotoAlbum mPhotoAlbum = mAllPhotoAlbumList.get(position);
        if ( mPhotoAlbum.getPhotoInfos() != null ) {
            mPhotoList.addAll(mPhotoAlbum.getPhotoInfos());
        }
        mPhotoListAdapter.notifyDataSetChanged();

        if (mPhotoList.size() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
            mEmptyView.setText(R.string.no_photos);
        }
    }


    private void photoItemClick(View view, int position) {
        PhotoInfo info = mPhotoList.get(position);

        if(mImagePickerStateInterface!=null)
            mImagePickerStateInterface.onPickImage(info);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.textView1){
            SwitchFolderAndFileMode(true);
        }else if(v.getId() == R.id.textView2){
            if(mImagePickerStateInterface!=null)
                mImagePickerStateInterface.onCanclePicke();
        }
    }


}
