package com.github.xiaogegechen.sponge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.github.xiaogegechen.library.Sponge;
import com.github.xiaogegechen.library.expansion.DefaultDrawableGetter;
import com.github.xiaogegechen.library.expansion.DefaultImageClickHandler;
import com.github.xiaogegechen.library.expansion.DefaultURLClickHandler;
import com.github.xiaogegechen.library.expansion.SizeAndColorTagHandler;

public class MainActivity extends AppCompatActivity {

    private static final String HTML = "<img src=\"https://cn.bing.com/th?id=OHR.LeatherbackTT_ROW0614606094_1920x1080.jpg&rf=LaDigue_1920x1081920x1080.jpg\"><br>\n" +
            "<p>\n" +
            "  最近，朋友圈被一部悬疑古装剧《长安十二时辰》刷屏了，不知道各位同学看了吗？随着剧的热播，“十二时辰”这个梗已经被玩坏了，各大APP的十二时辰、周末十二时辰、上班十二时辰、北上广十二时辰......\n" +
            "</p>\n" +
            "<p>\n" +
            "  <size_and_color size=\"30px\" color=\"#00ffff\">\n" +
            "    最近，朋友圈被一部悬疑古装剧《长安十二时辰》刷屏了，不知道各位同学看了吗？随着剧的热播，“十二时辰”这个梗已经被玩坏了，各大APP的十二时辰、周末十二时辰、上班十二时辰、北上广十二时辰......\n" +
            "  </size_and_color>\n" +
            "</p>\n" +
            "<a href=\"https://www.zhihu.com/\">\n" +
            "  <u>\n" +
            "    <size_and_color size=\"50px\" color=\"#ff0000\">\n" +
            "    知乎,发现更大的世界\n" +
            "    </size_and_color>\n" +
            "  </u>\n" +
            "</a><br/>\n" +
            "<p>\n" +
            "  <size_and_color size=\"30px\" color=\"#00ff00\">\n" +
            "    最近，朋友圈被一部悬疑古装剧《长安十二时辰》刷屏了，不知道各位同学看了吗？随着剧的热播，“十二时辰”这个梗已经被玩坏了，各大APP的十二时辰、周末十二时辰、上班十二时辰、北上广十二时辰......\n" +
            "  </size_and_color>\n" +
            "</p>\n" +
            "<p>\n" +
            "  <size_and_color size=\"30px\" color=\"#0000ff\">\n" +
            "    最近，朋友圈被一部悬疑古装剧《长安十二时辰》刷屏了，不知道各位同学看了吗？随着剧的热播，“十二时辰”这个梗已经被玩坏了，各大APP的十二时辰、周末十二时辰、上班十二时辰、北上广十二时辰......\n" +
            "  </size_and_color>\n" +
            "</p>\n" +
            "\n" +
            "<p>\n" +
            "  <size_and_color size=\"50px\" color=\"#E98B2A\">\n" +
            "    最近，朋友圈被一部悬疑古装剧《长安十二时辰》刷屏了，不知道各位同学看了吗？随着剧的热播，“十二时辰”这个梗已经被玩坏了，各大APP的十二时辰、周末十二时辰、上班十二时辰、北上广十二时辰......\n" +
            "  </size_and_color>\n" +
            "</p>";

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        mTextView = findViewById (R.id.text_view);
        Sponge.with (MainActivity.this)
                .content (HTML)
                .drawableGetter (new DefaultDrawableGetter ())
                .specialTagHandler (new SizeAndColorTagHandler ())
                .imageClickHandler (new DefaultImageClickHandler (MainActivity.this))
                .urlClickHandler (new DefaultURLClickHandler (MainActivity.this))
                .into (mTextView);
    }
}
