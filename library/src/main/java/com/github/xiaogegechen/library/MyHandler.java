package com.github.xiaogegechen.library;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.style.ImageSpan;
import android.text.style.URLSpan;

import com.github.xiaogegechen.library.api.ImageClickHandler;
import com.github.xiaogegechen.library.api.URLClickHandler;
import com.github.xiaogegechen.library.expansion.DefaultImageClickHandler;
import com.github.xiaogegechen.library.expansion.DefaultURLClickHandler;

import java.util.Map;

/**
 * 处理点击事件的Handler，所有的点击事件都需要通过这个Handler实现
 */
public class MyHandler extends Handler {

    private static final String TAG = "MyHandler";

    // 图片点击处理器
    private ImageClickHandler mImageClickHandler;

    // 链接点击处理器
    private URLClickHandler mURLClickHandler;

    // ImageGetter,主要是为了获取包含当前图片信息的map
    private MyImageGetter mImageGetter;

    public MyHandler(ImageClickHandler imageClickHandler, URLClickHandler urlClickHandler, MyImageGetter imageGetter, Context context){
        // 如果是空的，就用默认的处理器
        if(imageClickHandler == null){
            mImageClickHandler = new DefaultImageClickHandler (context);
        }else{
            mImageClickHandler = imageClickHandler;
        }

        if(urlClickHandler == null){
            mURLClickHandler = new DefaultURLClickHandler (context);
        }else{
            mURLClickHandler = urlClickHandler;
        }

        mImageGetter = imageGetter;
    }

    /**
     * 设置图片点击处理器
     * @param imageClickHandler 图片点击处理器
     */
    public void setImageClickHandler(ImageClickHandler imageClickHandler) {
        mImageClickHandler = imageClickHandler;
    }

    /**
     * 设置链接点击处理器
     * @param URLClickHandler 链接点击处理器
     */
    public void setURLClickHandler(URLClickHandler URLClickHandler) {
        mURLClickHandler = URLClickHandler;
    }

    @Override
    public void handleMessage(Message msg) {
        if(msg.what == Constants.HANDLER_CODE){
            // 拿到所点击位置的链接
            Object[] links = (Object[]) msg.obj;
            // 遍历链接，调用处理器响应的处理方法
            for (Object link : links) {
                if(link instanceof ImageSpan){
                    // 图片点击的处理
                    String source = ((ImageSpan) link).getSource ();
                    Map<String, ImageRecord> map = mImageGetter.getImageRecordMap ();
                    ImageRecord record = map.get (source);
                    if(record != null){
                        switch (record.getStatus ()){
                            case ImageRecord.TYPE_OK:
                                // 加载正确
                                mImageClickHandler.clickLoadOKImage (source);
                                break;
                            case ImageRecord.TYPE_LOADING:
                                // 正在加载
                                mImageClickHandler.clickLoadingImage (source);
                                break;
                            case ImageRecord.TYPE_LOAD_FAIL:
                                // 加载失败
                                mImageClickHandler.clickLoadFailedImage (source);
                                break;
                            default:
                                break;
                        }
                    }
                }else if(link instanceof URLSpan){
                    // 链接点击处理
                    String url = ((URLSpan) link).getURL ();
                    mURLClickHandler.clickURL (url);
                }
            }
        }
    }
}
