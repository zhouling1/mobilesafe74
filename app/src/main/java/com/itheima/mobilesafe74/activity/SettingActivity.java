package com.itheima.mobilesafe74.activity;

import com.itheima.mobilesafe74.R;
import com.itheima.mobilesafe74.service.AddressService;
import com.itheima.mobilesafe74.utils.ConstantValue;
import com.itheima.mobilesafe74.utils.ServiceUtils;
import com.itheima.mobilesafe74.utils.SpUtil;
import com.itheima.mobilesafe74.view.SettingItemView;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initUpdate();
        initAddress();
    }

    /**
     * 版本更新开关
     */
    private void initUpdate() {
        final SettingItemView siv_update = (SettingItemView) findViewById(R.id.siv_update);

        //获取已有的开关状态,用作显示
        boolean open_update = SpUtil.getBoolean(this, ConstantValue.OPEN_UPDATE, false);
        //是否选中,根据上一次存储的结果去做决定
        siv_update.setCheck(open_update);

        siv_update.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果之前是选中的,点击过后,变成未选中
                //如果之前是未选中的,点击过后,变成选中

                //获取之前的选中状态
                boolean isCheck = siv_update.isCheck();
                //将原有状态取反,等同上诉的两部操作
                siv_update.setCheck(!isCheck);
                //将取反后的状态存储到相应sp中
                SpUtil.putBoolean(getApplicationContext(), ConstantValue.OPEN_UPDATE, !isCheck);
            }
        });
    }

    //是否显示电话归属地显示
    private void initAddress() {
        final SettingItemView siv_Calllocation = (SettingItemView) findViewById(R.id.siv_Calllocation);


        final boolean open_on = ServiceUtils.isRunning(SettingActivity.this,"com.itheima.mobilesafe74.service.AddressService");//获取服务是否开启
        siv_Calllocation.setCheck(open_on);
        siv_Calllocation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                 boolean checking=siv_Calllocation.isCheck();
                  if(!checking){
                    startService(new Intent(getApplicationContext(), AddressService.class));
                  }else{
                       stopService(new Intent(getApplicationContext(),AddressService.class));
                  }
                  siv_Calllocation.setCheck(!checking);
            }
        });
    }

}
