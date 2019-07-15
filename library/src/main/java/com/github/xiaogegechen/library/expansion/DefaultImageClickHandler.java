package com.github.xiaogegechen.library.expansion;

import android.content.Context;
import android.content.Intent;

import com.github.xiaogegechen.library.Sponge;
import com.github.xiaogegechen.library.api.ImageClickHandler;

/**
 * 默认的图片点击处理
 */
public class DefaultImageClickHandler implements ImageClickHandler {

    private Context mContext;

    public DefaultImageClickHandler(Context context){
        mContext = context;
    }

    @Override
    public void clickLoadingImage(String source) {
        // 正在加载不响应
    }

    @Override
    public void clickLoadFailedImage(String source) {
        // 加载失败，重新加载
        Sponge.with (mContext).reload (source);
    }

    @Override
    public void clickLoadOKImage(String source) {
        // 跳转到PictureActivity
        Intent intent = new Intent (mContext, DefaultPictureActivity.class);
        intent.putExtra (Constants.PICTURE_ACTIVITY_PARAM_NAME, source);
        mContext.startActivity (intent);
    }
}
