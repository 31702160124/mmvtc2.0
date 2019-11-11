package com.a1843318972.mmvtcschool;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.a1843318972.mmvtcschool.config.jwcUtil;

public class BaseActivity extends AppCompatActivity {
    public static BaseActivity instance = null;
    public jwcUtil jwcutil = jwcUtil.getInstance();

    //沉浸式状态栏  0 透明 1 指定颜色 2 主布局背景颜色
    public void initTopbar(int i, String... color) {
        //4.4以上设置状态栏为透明
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        // 5.0以上系统状态栏透明，
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            if (i == 0) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//设置状态栏为透明
                }
            }
            if (i == 1) {
                window.setStatusBarColor(Color.parseColor(color[0]));//设置状态栏为指定颜色
            }
            if (i == 2) {
                window.setStatusBarColor(Color.TRANSPARENT);//设置状态栏颜色和主布局背景颜色相同
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
    }

    public String getUsername() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("mmvtc", MODE_PRIVATE);
        return sharedPreferences.getString("name", null);
    }

    public void saveUser(String... s) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("mmvtc", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", s[0]);
        editor.putString("pwd", s[1]);
        editor.apply();
    }

    public String getCookie() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("mmvtc", MODE_PRIVATE);
        return sharedPreferences.getString("coolie", "");
    }

    public void saveCookie(String s) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("mmvtc", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("coolie", s);
        editor.apply();
    }

    public Boolean getCanCookie() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("mmvtc", MODE_PRIVATE);
        return sharedPreferences.getBoolean("Cancoolie", false);
    }

    public void saveCanCookie(Boolean s) {
        SharedPreferences sharedPreferences = this.getSharedPreferences("mmvtc", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("Cancoolie", s);
        editor.apply();
    }

}
