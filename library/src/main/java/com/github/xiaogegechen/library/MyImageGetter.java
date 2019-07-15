package com.github.xiaogegechen.library;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.Html;

import com.github.xiaogegechen.library.api.DrawableGetter;

import java.util.HashMap;
import java.util.Map;

/**
 * ImageGetter的实现类，通过对外提供的{@link DrawableGetter}
 * 来拿到图片加载各个阶段的Drawable
 */
public abstract class MyImageGetter implements Html.ImageGetter {

    private static final String TAG = "MyImageGetter";

    /**
     * 图片最大宽度，一般和他的TextView宽度相等
     */
    private int mMaxWidth;

    /**
     * 图片url和图片详细信息的映射，管理这个textView中所有的图片。
     */
    private Map<String, ImageRecord> mImageRecordMap;

    /**
     * Drawable 处理器
     */
    private DrawableGetter mDrawableGetter;

    private Context mContext;

    public MyImageGetter(int maxWidth, Context context, DrawableGetter drawableGetter){
        mMaxWidth = maxWidth;
        mContext = context;
        mDrawableGetter = drawableGetter;
        mImageRecordMap = new HashMap<> ();
    }

    public Map<String, ImageRecord> getImageRecordMap() {
        return mImageRecordMap;
    }

    public int getMaxWidth() {
        return mMaxWidth;
    }

    @Override
    public Drawable getDrawable(String source) {
        // 检查是否已经加入map中
        ImageRecord record = mImageRecordMap.get (source);
        if(record == null){
            // 没有加入，加载，返回加载中
            loadFromNet (source);
            return mDrawableGetter.getLoadingDrawable (mMaxWidth, mContext);
        }else{
            // 根据状态决定返回值
            switch (record.getStatus ()){
                case ImageRecord.TYPE_LOADING:
                    // 返回加载中的图片
                    return mDrawableGetter.getLoadingDrawable (mMaxWidth, mContext);
                case ImageRecord.TYPE_OK:
                    // 返回文件中的图片
                    String fileName = Utils.hashKeyForDisk (source);
                    Bitmap bitmap;
                    if ((bitmap = Utils.loadBitmapFromFile (mContext, fileName))!= null) {
                        return mDrawableGetter.getLoadOKDrawable (mMaxWidth, mContext, bitmap);
                    }
                    break;
                case ImageRecord.TYPE_LOAD_FAIL:
                    // 返回失败的图片
                    return mDrawableGetter.getLoadFailedDrawable(mMaxWidth, mContext);
                default:
                    break;
            }
        }
        return null;
    }

    /**
     * 从网络加载一张图片，并写入到当前的信息体容器中
     * @param source 图片url
     */
    public void loadFromNet(final String source){
        // 检查是否在map中
        ImageRecord record = mImageRecordMap.get (source);
        if(record == null){
            // 不在map中，加入map
            record = new ImageRecord ();
            record.setStatus (ImageRecord.TYPE_LOADING);
            record.setUrl (source);
            mImageRecordMap.put (source, record);
        }
        // 设置为加载中
        record.setStatus (ImageRecord.TYPE_LOADING);
        // 开启异步任务进行加载
        new LoadImageAsyncTask (mContext, new LoadImageListener () {
            @Override
            public void onSuccess() {
                // 改变状态，通知更新
                mImageRecordMap.get (source).setStatus (ImageRecord.TYPE_OK);
                set ();
            }

            @Override
            public void onFailure() {
                // 改变状态，通知更新
                mImageRecordMap.get (source).setStatus (ImageRecord.TYPE_LOAD_FAIL);
                set ();
            }
        }).execute (source);
    }

    // 为textView设置内容和点击处理
    public abstract void set();
}
