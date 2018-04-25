package com.ips.sx.ipark.view;

import android.os.CountDownTimer;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

/**
 * Created by sangx on 2018/1/29.
 */

public class MyCountDownTimer extends CountDownTimer {
    private  TextView button ;
    private  String gogo;

    public MyCountDownTimer(long millisInFuture, long countDownInterval, TextView button , String  gogo) {
        super(millisInFuture, countDownInterval);
        this.button=button;
        this.gogo=gogo;

    }

    @Override
    public void onTick(long millisUntilFinished) {
        button.setText(millisUntilFinished/1000+"");
        final AlphaAnimation  alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setFillAfter(true);
        button.startAnimation(alphaAnimation);

        final ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f,1.5f,1.0f,1.5f, Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,1.0f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);
        button.setAnimation(scaleAnimation);

    }

    @Override
    public void onFinish() {
        button.setText("go");
        final AlphaAnimation  alphaAnimation = new AlphaAnimation(0,1);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setFillAfter(true);
        button.startAnimation(alphaAnimation);

        final ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f,1.5f,1.0f,1.5f, Animation.RELATIVE_TO_SELF,1.0f,Animation.RELATIVE_TO_SELF,1.0f);
        scaleAnimation.setDuration(1000);
        scaleAnimation.setFillAfter(true);
        button.setAnimation(scaleAnimation);

    }


}
