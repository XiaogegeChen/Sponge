package com.github.xiaogegechen.library;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 工具类
 */
public class Utils {

    /**
     * 从文件中读取bitmap
     * @param context context
     * @param fileName 文件名
     * @return 读到的bitmap,如果文件不存在返回null
     */
    public static Bitmap loadBitmapFromFile(Context context, String fileName){
        Bitmap bitmap = null;
        File file = new File (context.getExternalCacheDir (), fileName);
        if(file.exists ()){
            bitmap = BitmapFactory.decodeFile (file.getPath ());
        }
        return bitmap;
    }

    /**
     * MD5加密字符串用作文件名
     * @param key 图片url
     * @return MD5加密得到的文件名
     */
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            String hex = Integer.toHexString (0xFF & aByte);
            if (hex.length () == 1) {
                sb.append ('0');
            }
            sb.append (hex);
        }
        return sb.toString();
    }

    /**
     * px转化为sp
     * @param px 像素大小
     * @return 文字大小
     */
    public static int px2sp(int px){
        float density = Resources.getSystem ().getDisplayMetrics ().density;
        return (int) ((px - 0.5) / density);
    }
}
