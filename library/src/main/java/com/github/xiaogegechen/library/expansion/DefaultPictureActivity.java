package com.github.xiaogegechen.library.expansion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.github.xiaogegechen.library.R;
import com.github.xiaogegechen.library.Utils;

/**
 * 默认的图片显示Activity
 */
public class DefaultPictureActivity extends AppCompatActivity {

    private ImageView mPicture;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_picture_default);

        mPicture = findViewById (R.id.picture);
        String url = getIntent ().getStringExtra (Constants.PICTURE_ACTIVITY_PARAM_NAME);
        String fileName = Utils.hashKeyForDisk (url);
        mPicture.setImageBitmap (Utils.loadBitmapFromFile (this, fileName));
    }
}
