package com.ips.sx.ipark.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ips.sx.ipark.R;
import com.ips.sx.ipark.tools.MyApplication;
import com.ips.sx.ipark.tools.ParkOpeart;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by sangx on 2018/2/22.
 */

public class BottomDialog extends Dialog {
    final OkHttpClient client = new OkHttpClient();


    public BottomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        View view = getLayoutInflater().inflate(R.layout.chose_park,null);
        view.findViewById(R.id.chose_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
                MyApplication.MyToast("确认停车");
                EventBus.getDefault().post(new ParkOpeart(1,"停车点在这！以为你预定，请尽快前往！"));
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            client.newCall( new Request.Builder().url("http://120.79.0.116/park/ipark?n=10001&s=2").build()).execute();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        super.setContentView(view);
    }



    public BottomDialog(@NonNull Context context) {
        super(context);
        View view = getLayoutInflater().inflate(R.layout.chose_park,null);
        view.findViewById(R.id.chose_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
                MyApplication.MyToast("确认停车");
            }
        });
        super.setContentView(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        getWindow().setAttributes(p);
        p.height = (int) (d.getHeight() * 0.3);
        p.width = d.getWidth();
        p.gravity = Gravity.LEFT | Gravity.BOTTOM;
        dialogWindow.setAttributes(p);
    }
}
