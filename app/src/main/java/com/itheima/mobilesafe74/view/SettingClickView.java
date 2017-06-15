package com.itheima.mobilesafe74.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itheima.mobilesafe74.R;

/**
 * Created by Administrator on 2017/6/14.
 */

public class SettingClickView extends RelativeLayout {

    private TextView tv_title, tv_dec;

    private String title,content;

    private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.itheima.mobilesafe74";

    public SettingClickView(Context context) {
        this(context,null);
    }

    public SettingClickView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public SettingClickView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View.inflate(context, R.layout.setting_click_view, this);

        tv_title = (TextView)findViewById(R.id.tv_titleContent);
        tv_dec = (TextView)findViewById(R.id.tv_des);

        initattrs(attrs);

        tv_title.setText(title);
        tv_dec.setText(content);
    }

    private void initattrs(AttributeSet attrs) {
/*
* 	mDestitle = attrs.getAttributeValue(NAMESPACE, "destitle");
		mDesoff = attrs.getAttributeValue(NAMESPACE, "desoff");
		mDeson = attrs.getAttributeValue(NAMESPACE, "deson");
* */
        title=attrs.getAttributeValue(NAMESPACE,"mDesTitle");
        content=attrs.getAttributeValue(NAMESPACE,"mDesContent");
    }


    public void setTv_dec(String tv_deccontent){
        tv_dec.setText(tv_deccontent);
    }

}
