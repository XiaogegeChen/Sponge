package com.github.xiaogegechen.library.api;

import android.text.Editable;

import java.util.Map;

/**
 * 特殊标签处理器，interface
 */
public interface SpecialTagHandler {
    /**
     * 处理特殊标签
     * @param attributeMap 标签的属性map
     * @param output 标签内的文本
     * @param start 标签内文本的起始下标
     * @param end 标签内文本的终止下标
     */
    void handleSpecialTag(Map<String, String> attributeMap, Editable output, int start, int end);

    /**
     * 返回标签名
     * @return 标签名
     */
    String getSpecialTag();
}
