package com.ips.sx.ipark.tools;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by sangx on 2018/1/14.
 */

public class MyApplication extends Application {
    private static Context context;

    public  static  final  int  LOG_LEVEL_DEBUUG = 1 ;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        SDKInitializer.initialize(getApplicationContext()); //初始化Contxt信息，百度地图特有的

    }


    //获取context
    public  static  Context getContext(){
            return context;
    }

    //封装Toast
    public  static  void  MyToast(String content){
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
    }

    //动态申请权限
    public static void RequestPerission(Activity activity, String permissionname){
        if(context.checkSelfPermission(permissionname)!= PackageManager.PERMISSION_GRANTED){
            //进行动态权限申请
            ActivityCompat.requestPermissions(activity,new String[]{permissionname},permissionname.getBytes()[1]);
        }
    }

    //封装广播
    public static void broadcastUpdate(Context context ,final String action) {
        final Intent intent = new Intent(action);
        context.sendBroadcast(intent);   //发送广播通知
    }

    //打印日志
    public static void PrintLog(String Tag ,String  content,int level) {
        switch (level){
            case  LOG_LEVEL_DEBUUG:
                Log.d(Tag,content);
                break;
            default:
        }
    }


}
