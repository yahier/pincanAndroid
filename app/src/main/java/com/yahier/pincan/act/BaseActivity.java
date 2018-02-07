package com.yahier.pincan.act;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.yahier.pincan.R;


/**
 * Created by yahier on 2018/1/25.
 */

public class BaseActivity extends AppCompatActivity {
    Toolbar toolBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Activity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager((this));
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.MAIN);
        }
    }

    @Override
    public void setContentView(int layoutResID) {
        setContentView(View.inflate(this, layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(View.inflate(this, R.layout.activity_base, null));
        ViewGroup container = findViewById(R.id.activity_base_frame);
        AppBarLayout appBar = (AppBarLayout) findViewById(R.id.activity_base_bar);
        toolBar = (Toolbar) findViewById(R.id.navigation_bar);
        setSupportActionBar(toolBar);


        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        container.addView(view, params);
    }

    protected void startActivity(Class mClass) {
        startActivity(new Intent(this, mClass));
    }

    protected void setTitleText(Object resId) {
        toolBar.post(() -> {
            if (resId instanceof Integer) {
                toolBar.setTitle((int) resId);
            } else if (resId instanceof CharSequence) {
                toolBar.setTitle((CharSequence) resId);
            }
        });
    }
}
