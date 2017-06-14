package com.itheima.mobilesafe74.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;


/**
 * Created by Administrator on 2017/6/14.
 */

public class ServiceUtils {

    /**
     * @param context     上下文环境
     * @param classname 服务的类名
     * @return
     */
    public static boolean isRunning(Context context, String classname) {
        //使用ActivityManager获取service的集合
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = manager.getRunningServices(1000);

        //遍历集合
        for(ActivityManager.RunningServiceInfo info:runningServiceInfos){
           if(classname.equals(info.service.getClassName())){
               return true;
           }

        }

        return false;
    }
}
