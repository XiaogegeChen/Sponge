package com.github.xiaogegechen.library;

/**
 * 下载结果回调监听
 */
public interface LoadImageListener {

    /**
     * 下载成功
     */
    void onSuccess();

    /**
     * 下载失败
     */
    void onFailure();
}
