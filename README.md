[![](https://jitpack.io/v/XiaogegeChen/Sponge.svg)](https://jitpack.io/#XiaogegeChen/Sponge)
# Sponge（Android端的图文混排框架）
![1](https://github.com/XiaogegeChen/Sponge/blob/master/screenshot/first.gif)
![2](https://github.com/XiaogegeChen/Sponge/blob/master/screenshot/second.gif)
## 主要功能
* 基于html，可解析大部分html标签<br>
* 可添加自定义标签及其解析<br>
* 可自定义图片和链接的点击事件<br>
* 全部接口都有默认实现，采用链式调用，简介清晰（使用过Glide和RxJava的都懂）<br>
## 快速使用
1.在工程根目录的build.gradle中添加依赖
```
allprojects {
    repositories {
        google()
        jcenter()
        
        maven { url 'https://jitpack.io' } 
    }
}
```
2.在工程目录的build.gradle中添加依赖(查看最上面的版本号进行替换)
```
implementation 'com.github.xiaogegechen:sponge:1.0.0'
```
3. 在布局文件中添加一个```ScrollView```并包裹```TextView```，如下：
```
    <ScrollView
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        <TextView
            android:layout_marginLeft="15dp"
            android:layout_marginRight="15dp"
            android:id="@+id/text_view"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Hello World!" />
    </ScrollView>
```
4.在```Activity```中拿到控件，并配置。可参考[MainActivity.java](https://github.com/XiaogegeChen/Sponge/blob/master/app/src/main/java/com/github/xiaogegechen/sponge/MainActivity.java)
```
    private static final String HTML = "";   // 具体的内容可以参看上述MainActivity.java
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        mTextView = findViewById (R.id.text_view);
        
        Sponge.with (MainActivity.this)   // 传入context，并拿到Sponge的单例
                .content (HTML)     // 待解析的html格式的文本
                .drawableGetter (new DefaultDrawableGetter ())   // 图片获取器，规定如何获取图片
                .specialTagHandler (new SizeAndColorTagHandler ())    // 特殊标签的解析器
                .imageClickHandler (new DefaultImageClickHandler (MainActivity.this))   // 图片点击事件处理器
                .urlClickHandler (new DefaultURLClickHandler (MainActivity.this))   // 链接点击事件处理器
                .into (mTextView);   // 将上述配置传递给TextView
```
至此一个简单的图文混排demo就完成了。<br/>
## 自定义扩展
Sponge是一个扩展性很强、很灵活的框架，提供了大量的接口进行功能的扩展，包括自定义图片获取器、自定义标签解析、自定义图片点击、自定义链接点击等等。详细的使用方法可参考博客[Android端的图文混排](https://blog.csdn.net/qq_40909351/article/details/96000262)
## 更新日志
v1.0.0
