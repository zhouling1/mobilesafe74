package com.itheima.mobilesafe74.activity;

import com.itheima.mobilesafe74.R;
import com.itheima.mobilesafe74.service.AddressService;
import com.itheima.mobilesafe74.utils.ConstantValue;
import com.itheima.mobilesafe74.utils.ServiceUtils;
import com.itheima.mobilesafe74.utils.SpUtil;
import com.itheima.mobilesafe74.view.SettingClickView;
import com.itheima.mobilesafe74.view.SettingItemView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity {
    private String[] myToastStyleSelect;

    private int myToastStyle;
    private  SettingClickView scv_toast_style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initUpdate();
        initAddress();
        initToastLocation();
        initToastStyle();
    }

    private void initToastStyle() {
        scv_toast_style = (SettingClickView) findViewById(R.id.scv_toast_style);
        myToastStyleSelect = new String[]{"透明", "橙色", "蓝色", "灰色", "绿色"};
        myToastStyle = SpUtil.getInt(SettingActivity.this, ConstantValue.TOASTSTYLE, 0);
        scv_toast_style.setTv_dec(myToastStyleSelect[myToastStyle]);
        scv_toast_style.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showToastStyleDialog();
            }
        });

    }

    private void showToastStyleDialog() {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.home_safe);
        builder.setTitle("请选择归属地样式");

        builder.setSingleChoiceItems(myToastStyleSelect, myToastStyle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SpUtil.putInt(SettingActivity.this,ConstantValue.TOASTSTYLE,which);
                dialog.dismiss();
                scv_toast_style.setTv_dec(myToastStyleSelect[which]);
            }
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void initToastLocation() {
        final SettingClickView scv_location = (SettingClickView) findViewById(R.id.scv_location);
        scv_location.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, LocationActivity.class));
            }
        });

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


        final boolean open_on = ServiceUtils.isRunning(SettingActivity.this, "com.itheima.mobilesafe74.service.AddressService");//获取服务是否开启
        siv_Calllocation.setCheck(open_on);
        siv_Calllocation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checking = siv_Calllocation.isCheck();
                if (!checking) {
                    startService(new Intent(getApplicationContext(), AddressService.class));
                } else {
                    stopService(new Intent(getApplicationContext(), AddressService.class));
                }
                siv_Calllocation.setCheck(!checking);
            }
        });
    }

}
