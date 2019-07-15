package com.github.xiaogegechen.library;

import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * 文本点击事件的分发与处理
 */
public class MyLinkMovementMethod extends ScrollingMovementMethod {

    private static final String TAG = "MyLinkMovementMethod";

    /**
     * 负责处理点击事件
     */
    private Handler mHandler;

    /**
     * 标记是否是点击，用以区别触摸事件
     */
    private boolean isClick = false;

    public MyLinkMovementMethod(Handler handler){
        mHandler = handler;
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        int action = event.getAction();
        Log.d (TAG, "onTouchEvent: " + event.getAction ());

        if(action == MotionEvent.ACTION_MOVE){
            isClick = false;
        }

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x = x - widget.getTotalPaddingLeft ();
            y = y - widget.getTotalPaddingTop ();

            x = x + widget.getScrollX ();
            y = y + widget.getScrollY ();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            Object[] imageLinks = buffer.getSpans(off, off, ImageSpan.class);
            Object[] textLinks = buffer.getSpans (off, off, ClickableSpan.class);
            int length = imageLinks.length + textLinks.length;
            Object[] links = new Object[length];
            for(int i = 0; i < imageLinks.length; i++){
                links[i] = imageLinks[i];
            }
            for(int i = 0; i < textLinks.length; i++){
                links[imageLinks.length + i] = textLinks[i];
            }

            if (links.length != 0) {
                if(action == MotionEvent.ACTION_DOWN){
                    // TODO 在这里设置触摸效果

                    isClick = true;
                    // 不能直接返回true，因为后续滑动会受限制
                    // 应该直接交给父类处理，视为不可点击页面处理
                    return super.onTouchEvent (widget, buffer, event);
                }else {
                    // 这里是action == MotionEvent.ACTION_UP情况
                    if(isClick){
                        Message message = mHandler.obtainMessage ();
                        message.obj = links;
                        message.what = Constants.HANDLER_CODE;
                        mHandler.sendMessage (message);
                    }
                }
                return true;
            }else{
                Selection.removeSelection(buffer);
            }
        }
        return super.onTouchEvent (widget, buffer, event);
    }
}
