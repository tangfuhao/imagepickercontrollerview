package com.likeios.fuhao.tang.imagepickercontrollerview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.likeios.fuhao.tang.imagepickercontrollerviewlib.model.PhotoInfo;
import com.likeios.fuhao.tang.imagepickercontrollerviewlib.view.ImageDisplayerInterface;
import com.likeios.fuhao.tang.imagepickercontrollerviewlib.view.ImagePickerStateInterface;
import com.likeios.fuhao.tang.imagepickercontrollerviewlib.view.ImagePickerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout mlayout = (RelativeLayout) findViewById(R.id.layout);
        ImagePickerView mImagePickerView = new ImagePickerView(this);
        mImagePickerView.init(1280,
                new ImageDisplayerInterface() {
                    @Override
                    public void displayImage(Context mContext, String path, ImageView mThumb, Drawable defaultDrawable, int width, int height) {

                    }
                },
                new ImagePickerStateInterface() {
                    @Override
                    public void onPickImage(PhotoInfo info) {

                    }

                    @Override
                    public void onCanclePicke() {

                    }
                });
        mlayout.addView(mImagePickerView,1280,RelativeLayout.LayoutParams.MATCH_PARENT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
