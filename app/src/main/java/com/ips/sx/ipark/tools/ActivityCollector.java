package com.ips.sx.ipark.tools;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sangx on 2018/1/28.
 */

public class ActivityCollector {
    //定义Activity收集类
    public  static List<Activity>  ActivityCollect = new ArrayList<>();

    //增加Activity
    public static void  addActivity(Activity activity){
        ActivityCollect.add(activity);
    }

    //移除Activity
    public static void  removeActivity(Activity activity){
        ActivityCollect.remove(activity);
    }

}
