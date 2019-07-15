package com.github.xiaogegechen.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 下载图片的异步任务
 */
public class LoadImageAsyncTask extends AsyncTask<String, Void, Boolean> {

    @SuppressLint("StaticFieldLeak")
    private Context mContext;

    /**
     * 下载结果回调监听
     */
    private LoadImageListener mLoadImageListener;

    public LoadImageAsyncTask(Context context, LoadImageListener loadImageListener) {
        mContext = context;
        mLoadImageListener = loadImageListener;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        HttpURLConnection connection  = null;
        InputStream inputStream = null;
        FileOutputStream outputStream = null;
        try {
            URL url = new URL (params[0]);
            connection = (HttpURLConnection) url.openConnection ();
            inputStream = connection.getInputStream ();
            Bitmap bitmap = BitmapFactory.decodeStream (inputStream);
            // 新建文件
            String fileName = Utils.hashKeyForDisk (params[0]);
            File file = new File (mContext.getExternalCacheDir (), fileName);
            if(file.exists ()){
                file.delete ();
            }
            file.createNewFile ();
            // 存储bitmap
            outputStream = new FileOutputStream (file);
            boolean success = bitmap.compress (Bitmap.CompressFormat.JPEG, 100, outputStream);
            if(success){
                return true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace ();
        } catch (IOException e) {
            e.printStackTrace ();
        }finally {
            if (connection != null) {
                connection.disconnect ();
            }
            if (inputStream != null) {
                try {
                    inputStream.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close ();
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if(aBoolean){
            mLoadImageListener.onSuccess ();
        }else{
            mLoadImageListener.onFailure ();
        }
    }
}
