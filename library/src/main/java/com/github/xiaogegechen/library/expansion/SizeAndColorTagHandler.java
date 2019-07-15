package com.github.xiaogegechen.library.expansion;

import android.graphics.Color;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.github.xiaogegechen.library.api.SpecialTagHandler;

import java.util.Map;

/**
 * 解析"size_and_color"标签的处理器，"size_and_color"有"size"和"color"属性
 * 分别表示字体大小和字体颜色。
 * 如<size_and_color size="30px" color="#00ff00">hello Android</size_and_color>
 */
public class SizeAndColorTagHandler implements SpecialTagHandler {

    private static final String TAG = "SizeAndColorTagHandler";

    @Override
    public void handleSpecialTag(Map<String, String> attributeMap, Editable output, int start, int end) {
        // 拿到各个属性
        String size = attributeMap.get ("size");
        String color = attributeMap.get ("color");
        //各个属性赋值
        if(size != null){
            size = size.split ("px")[0];
            int px = Integer.parseInt (size);
            output.setSpan (new AbsoluteSizeSpan (px), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if(color != null){
            int colorInt = Color.parseColor (color);
            Log.d (TAG, "handleSpecialTag: " + color + " -> " + colorInt);
            output.setSpan (new ForegroundColorSpan (colorInt), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    @Override
    public String getSpecialTag() {
        return "size_and_color";
    }
}
