package com.itheima.mobilesafe74.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.itheima.mobilesafe74.R;

/**
 * Created by zhouling on 2017/6/10.
 */

public class QuryAddressActivity extends Activity{
    private EditText ed_Phone;
    private Button btn_query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_address);
        initView();
    }

    private void initView() {
        ed_Phone= (EditText) findViewById(R.id.et_phone);
        btn_query= (Button) findViewById(R.id.bt_query);
        btn_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(ed_Phone.getText())){
                    //query();
                }else {
                    Animation shake= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);

                    ed_Phone.startAnimation(shake);
                }
            }
        });

    }
}
