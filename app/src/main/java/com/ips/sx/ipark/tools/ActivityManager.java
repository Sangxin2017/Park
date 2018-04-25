package com.ips.sx.ipark.tools;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.FrameMetrics;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by sangx on 2018/1/28.
 */

public class ActivityManager extends AppCompatActivity {
    public  static  final  int  STATUEBAR_FULLSCREEN = 0;
    public  static  final  int  STATUEBAR_FULLSCREEN_NOACTIONBAR = 1;
    public  static  final  int  STATUEBAR_NOACTIONBAR = 3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    //标题栏
    protected  void setStatueBar(int statue){
        View decorView = getWindow().getDecorView();
        int option =0;
        switch (statue){
            case  STATUEBAR_FULLSCREEN:
                option=View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;    //全屏
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,                                                                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
                break;

            case  STATUEBAR_FULLSCREEN_NOACTIONBAR:
                option=View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;   //设置全屏
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,                                                                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
                getSupportActionBar().hide();   //隐藏标题栏
                break;

            case  STATUEBAR_NOACTIONBAR:
                getSupportActionBar().hide();   //隐藏标题栏
                break;




        }
        decorView.setSystemUiVisibility(option);

    }


    //启动一个ACtivity
    protected  void  startActivity(Class<?>  activity){
        Intent intent  = new Intent(this,activity);
        startActivity(intent);
    }
}
