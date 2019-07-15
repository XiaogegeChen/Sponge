package com.github.xiaogegechen.library.expansion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.github.xiaogegechen.library.R;

/**
 * 默认的链接Activity
 */
public class DefaultLinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_link_default);
        TextView textView = findViewById (R.id.link_text);
        textView.setText (getIntent ().getStringExtra (Constants.LINK_ACTIVITY_PARAM_NAME));
    }
}
