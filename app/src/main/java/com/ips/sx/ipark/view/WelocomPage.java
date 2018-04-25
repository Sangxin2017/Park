package com.ips.sx.ipark.view;

import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;

import com.ips.sx.ipark.MainActivity;
import com.ips.sx.ipark.R;
import com.ips.sx.ipark.tools.ActivityManager;



public class WelocomPage extends ActivityManager {
    private TextView countdownbutton  = null;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcomepage_layout);
        setStatueBar(ActivityManager.STATUEBAR_FULLSCREEN_NOACTIONBAR);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                WelocomPage.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(MainActivity.class);
                        finish();
                    }
                });
            }
        }).start();
    }




}
