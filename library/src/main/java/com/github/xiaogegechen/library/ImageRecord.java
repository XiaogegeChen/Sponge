package com.github.xiaogegechen.library;

/**
 * 图片信息体，包含图片的url和当前的状态
 */
public class ImageRecord {

    /**
     * 加载失败
     */
    public static final int TYPE_LOAD_FAIL = 1;

    /**
     * 加载成功
     */
    public static final int TYPE_OK = 2;

    /**
     * 加载中
     */
    public static final int TYPE_LOADING = 3;

    /**
     * 图片url
     */
    private String url;

    /**
     * 当前状态
     */
    private int status;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
