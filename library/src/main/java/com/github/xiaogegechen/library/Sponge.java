package com.github.xiaogegechen.library;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.text.Spanned;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.github.xiaogegechen.library.api.DrawableGetter;
import com.github.xiaogegechen.library.api.ImageClickHandler;
import com.github.xiaogegechen.library.api.SpecialTagHandler;
import com.github.xiaogegechen.library.api.URLClickHandler;
import com.github.xiaogegechen.library.expansion.DefaultDrawableGetter;
import com.github.xiaogegechen.library.expansion.DefaultImageClickHandler;
import com.github.xiaogegechen.library.expansion.DefaultURLClickHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 对外提供的主类，使用方法如下：
 * Sponge.with (MainActivity.this)    // 传入Context
 *                 .content (HTML)    // 传入文本内容
 *                 .drawableGetter (new DefaultDrawableGetter ())   // 传入drawableGetter
 *                 .specialTagHandler (new SizeAndColorTagHandler ())   // 传入specialTagHandler，null将使用默认值
 *                 .imageClickHandler (new DefaultImageClickHandler (MainActivity.this))   // 传入imageClickHandler，null将使用默认值
 *                 .urlClickHandler (new DefaultURLClickHandler (MainActivity.this))  // urlClickHandler，null将使用默认值
 *                 .into (mTextView);   // 将配置作用在相应的textView上
 */
public class Sponge {

    private static volatile Sponge sInstance;

    /**
     * 传递context，并实例化单例
     * @param context context
     */
    public static Sponge with(Context context){
        if (sInstance == null) {
            synchronized (Sponge.class){
                if (sInstance == null) {
                    sInstance = new Sponge (context);
                }
            }
        }
        return sInstance;
    }

    private Context mContext;
    private String mContent;
    private DrawableGetter mDrawableGetter;
    private List<SpecialTagHandler> mSpecialTagHandlerList;
    private ImageClickHandler mImageClickHandler;
    private URLClickHandler mURLClickHandler;
    private MyImageGetter mImageGetter;
    private TextView mTarget;

    private Sponge(Context context){
        mContext = context;
    }

    /**
     * 传入待解析的文本
     * @param content 待解析的文本，不能为null
     * @return this
     */
    public Sponge content(@NonNull String content){
        mContent = content;
        return this;
    }

    /**
     * 传入drawableGetter
     * @param drawableGetter drawableGetter，可以为null，为null就使用默认值
     * @return this
     */
    public Sponge drawableGetter(@Nullable DrawableGetter drawableGetter){
        if (drawableGetter == null) {
            mDrawableGetter = new DefaultDrawableGetter ();
        }else{
            mDrawableGetter = drawableGetter;
        }
        return this;
    }

    /**
     * 传入自定义的标签处理器
     * @param specialTagHandlerList 自定义的标签处理器集合，可以为null，表示不传入任何自定义标签处理器
     * @return this
     */
    public Sponge specialTagHandler(@Nullable List<SpecialTagHandler> specialTagHandlerList){
        mSpecialTagHandlerList = specialTagHandlerList;
        return this;
    }

    /**
     * 传入自定义的标签处理器
     * @param specialTagHandler 自定义的标签处理器，可以为null，表示不传入任何自定义标签处理器
     * @return this
     */
    public Sponge specialTagHandler(@Nullable SpecialTagHandler specialTagHandler){
        if (specialTagHandler != null) {
            List<SpecialTagHandler> specialTagHandlerList = new ArrayList<> ();
            specialTagHandlerList.add (specialTagHandler);
            mSpecialTagHandlerList = specialTagHandlerList;
        }
        return this;
    }

    /**
     * 传入图片点击事件处理器
     * @param imageClickHandler 图片点击事件处理器，可以为null，表示使用默认图片点击事件处理器
     * @return this
     */
    public Sponge imageClickHandler(@Nullable ImageClickHandler imageClickHandler){
        if (imageClickHandler == null) {
            mImageClickHandler = new DefaultImageClickHandler (mContext);
        }else {
            mImageClickHandler = imageClickHandler;
        }
        return this;
    }

    /**
     * 传入链接点击事件处理器
     * @param urlClickHandler 链接点击事件处理器，可以为null，表四使用默认的链接点击事件处理器
     * @return this
     */
    public Sponge urlClickHandler(@Nullable URLClickHandler urlClickHandler){
        if (urlClickHandler == null) {
            mURLClickHandler = new DefaultURLClickHandler (mContext);
        }else{
            mURLClickHandler = urlClickHandler;
        }
        return this;
    }

    /**
     * 设置内容和处理器到textView中
     * @param target textView
     */
    public void into(@NonNull TextView target){
        mTarget = target;
        // 拿到target宽度
        if(mTarget.getWidth () != 0){
            mImageGetter = deployImageGetter (mTarget.getWidth ());
            mImageGetter.set ();
        }else{
            ViewTreeObserver observer = target.getViewTreeObserver ();
            observer.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener () {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onGlobalLayout() {
                    mTarget.getViewTreeObserver ().removeOnGlobalLayoutListener (this);
                    mImageGetter = deployImageGetter (mTarget.getWidth ());
                    mImageGetter.set ();
                }
            });
        }
    }

    private MyImageGetter deployImageGetter(int maxWidth){
        return new MyImageGetter (maxWidth, mContext, mDrawableGetter) {
            @Override
            public void set() {
                // 特殊的标签处理器
                MyTagHandler tagHandler = new MyTagHandler ();
                tagHandler.addTagHandler (mSpecialTagHandlerList);
                Spanned spanned = Html.fromHtml (mContent, mImageGetter, tagHandler);
                mTarget.setText (spanned);
                // 设置为可点击
                MyHandler handler = new MyHandler (mImageClickHandler, mURLClickHandler, mImageGetter, mContext);
                MyLinkMovementMethod linkMovementMethod = new MyLinkMovementMethod (handler);
                mTarget.setMovementMethod(linkMovementMethod);
            }
        };
    }

    /**
     * 重新加载一张图片
     * @param url 图片url
     */
    public void reload(String url){
        mImageGetter.loadFromNet (url);
    }
}
