package com.itheima.mobilesafe74.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.itheima.mobilesafe74.R;

/**
 * Created by zhouling on 2017/6/10.
 */

public class ToolActivity extends Activity{
    private TextView tv_qur;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atool);
        tv_qur= (TextView) findViewById(R.id.tv_query_phone_address);
        tv_qur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ToolActivity.this,QuryAddressActivity.class));
            }
        });

    }
}
