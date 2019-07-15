package com.github.xiaogegechen.library.api;

/**
 * 图片点击处理器，处理不同状态下的图片的点击事件
 */
public interface ImageClickHandler {

    /**
     * 正在加载中的图片的点击事件
     * @param source 图片的url
     */
    void clickLoadingImage(String source);

    /**
     * 加载失败的图片的点击事件
     * @param source 图片的url
     */
    void clickLoadFailedImage(String source);

    /**
     * 加载完成的图片的点击事件
     * @param source 图片的url
     */
    void clickLoadOKImage(String source);
}
