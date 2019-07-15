package com.github.xiaogegechen.library;

import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Html;

import com.github.xiaogegechen.library.api.SpecialTagHandler;

import org.xml.sax.XMLReader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TagHandler的实现类，解析自定义的或者framework不支持的标签
 * 通过对外提供的{@link SpecialTagHandler}接口增加课解析的标签
 */
public class MyTagHandler implements Html.TagHandler {

    /**
     * 特殊标签处理器集合
     */
    private List<SpecialTagHandler> mSpecialTagHandlerList;

    public MyTagHandler(){
        mSpecialTagHandlerList = new ArrayList<> ();
    }

    /**
     * 批量添加新的标签处理器
     * @param handlerList 标签处理器集合
     */
    public synchronized void addTagHandler(@Nullable List<SpecialTagHandler> handlerList){
        if (handlerList != null && handlerList.size () > 0) {
            mSpecialTagHandlerList.addAll (handlerList);
        }
    }

    // 解析标签用到的变量
    private Map<String, String> attributeMap = new HashMap<> ();
    private int start = 0;

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if(opening){
            // 拿到属性和文本开始位置下标
            attributeMap = getAttrs (xmlReader);
            start = output.length ();
        }else{
            // 拿到文本结束位置下标
            int end = output.length ();
            // 寻找合适的处理器处理
            for (SpecialTagHandler handler : mSpecialTagHandlerList) {
                if(tag.equals (handler.getSpecialTag ())){
                    handler.handleSpecialTag (attributeMap, output, start ,end);
                    break;
                }
            }
        }
    }

    /**
     * 拿到属性，并存储在map中
     * @param xmlReader xmlReader
     * @return 存储了属性的map。
     */
    private Map<String, String> getAttrs(XMLReader xmlReader){
        Map<String, String> map = new HashMap<> ();
        try {
            Field elementField = xmlReader.getClass ().getDeclaredField ("theNewElement");
            elementField.setAccessible (true);
            Object theNewElement = elementField.get (xmlReader);

            Field attrField = theNewElement.getClass ().getDeclaredField ("theAtts");
            attrField.setAccessible (true);
            Object theAtts = attrField.get (theNewElement);

            Field lengthField = theAtts.getClass ().getDeclaredField ("length");
            lengthField.setAccessible (true);
            Object length = lengthField.get (theAtts);

            Field dataField = theAtts.getClass ().getDeclaredField ("data");
            dataField.setAccessible (true);
            Object data = dataField.get (theAtts);

            int len = (Integer)length;
            String[] dt = (String[]) data;

            for(int i = 0; i < len; i++){
                map.put (dt[i *5 + 1], dt[i *5 + 4]);
            }

        } catch (NoSuchFieldException e) {
            e.printStackTrace ();
        } catch (IllegalAccessException e) {
            e.printStackTrace ();
        }
        return map;
    }
}
