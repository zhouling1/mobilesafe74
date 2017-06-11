package com.itheima.mobilesafe74.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsManager;
import android.telephony.gsm.SmsMessage;

import com.itheima.mobilesafe74.R;
import com.itheima.mobilesafe74.service.LocationService;
import com.itheima.mobilesafe74.utils.ConstantValue;
import com.itheima.mobilesafe74.utils.SpUtil;

public class MSMReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
       boolean open_security= SpUtil.getBoolean(context, ConstantValue.OPEN_SECURITY,false);


       if(open_security){
             Object[] objects= (Object[]) intent.getExtras().get("pdus");
              for(Object o:objects){
                  //获取短信对象
                  SmsMessage sms=SmsMessage.createFromPdu((byte[]) o);
                  //获取短信基本信息
                  String originatingAddress=sms.getOriginatingAddress();
                  //获取短信内容
                  String msmBody=sms.getMessageBody();
                  if(msmBody.contains("#*alar*#")){
                      MediaPlayer mediaPlayer=MediaPlayer.create(context, R.raw.ylzs);
                      mediaPlayer.setLooping(true);
                      mediaPlayer.start();
                  }
                  if(msmBody.contains("#*location*#")){
                      context.startService(new Intent(context,LocationService.class));

                  }

              }

       }
    }
}
