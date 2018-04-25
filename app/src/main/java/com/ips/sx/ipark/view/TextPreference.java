package com.ips.sx.ipark.view;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ips.sx.ipark.R;

/**
 * Created by sangx on 2018/2/10.
 */

public class TextPreference extends Preference {
    private TextView text;
    private FrameLayout fra;

    public TextPreference(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setLayoutResource(R.layout.about);
    }

    public TextPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutResource(R.layout.about);
    }

    public TextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayoutResource(R.layout.about);
    }

    public TextPreference(Context context) {
        super(context);
        setLayoutResource(R.layout.about);
    }


    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        fra=view.findViewById(R.id.fragment);
        text=view.findViewById(R.id.about_text);
    }
}
