package com.a1843318972.mmvtcschool;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.a1843318972.mmvtcschool.Interface.Ibitmap;
import com.a1843318972.mmvtcschool.Interface.Ilogin;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity extends BaseActivity {

    private TextView mBtnLogin;
    private View progress;
    private View mInputLayout;
    private float mWidth, mHeight;
    private LinearLayout linearLayout1, linearLayout2, linearLayout3;
    private ImageView yzm_img;
    private EditText name, pwd, yzm;
    private String codestr, usestr, pwdstr;
    private Timer timer;
    private TimerTask timerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        BaseActivity.instance.initTopbar(2, "#df0000");
        initView();
    }

    private void initView() {
        timer = new Timer();
        mBtnLogin = (TextView) findViewById(R.id.main_btn_login);
        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);
        linearLayout1 = (LinearLayout) findViewById(R.id.input_layout_1);
        linearLayout2 = (LinearLayout) findViewById(R.id.input_layout_2);
        linearLayout3 = (LinearLayout) findViewById(R.id.input_layout_3);
        yzm_img = findViewById(R.id.input_layout_yzm_img);
        name = findViewById(R.id.input_layout_name);
        pwd = findViewById(R.id.input_layout_pwd);
        yzm = findViewById(R.id.input_layout_yzm);
        initEdit();
        mBtnLogin.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        recovery();
                    }
                });
                return true;
            }
        });
    }

    public void showYzm(View view) {
        showYzm();
    }

    public void showYzm() {
        jwcutil.getCheckCodeImg(new Ibitmap() {
            @Override
            public void setBitmap(final Bitmap bitmap) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        yzm_img.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    public void login(View view) {
        //获取字符
        usestr = name.getText().toString().trim();
        pwdstr = pwd.getText().toString().trim();
        codestr = yzm.getText().toString().trim();
        // 判断参数是否为空
        if (usestr.equals("")) {
            name.requestFocus();
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            recovery();
            return;
        }
        if (pwdstr.equals("")) {
            pwd.requestFocus();
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            recovery();
            return;
        }
        if (codestr.equals("")) {
            yzm.requestFocus();
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
            recovery();
            return;
        }
        mWidth = mBtnLogin.getMeasuredWidth();
        mHeight = mBtnLogin.getMeasuredHeight();
        linearLayout1.setVisibility(View.INVISIBLE);
        linearLayout2.setVisibility(View.INVISIBLE);
        linearLayout3.setVisibility(View.INVISIBLE);
        inputAnimator(mInputLayout, mWidth, mHeight);
        jwcutil.Login(usestr, pwdstr, codestr, new Ilogin() {
            @Override
            public void login(String[] result) {
                switch (result[0]) {
                    case "checkCode_error":
                    case "pwd_error":
                    case "user_error":
                    case "user_locked":
                        yzm.setText("");
                        showHintsMsg(result[1]);
                        break;
                    case "ok":
                        saveUser(usestr, pwdstr);
                        showHintsMsg(result[1]);
                        gooMain();
                        break;
                    default:
                        recovery();
                        showHintsMsg("程序出错，登录失败");
                        break;
                }
            }
        });
    }

    private void gooMain() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    //初始化账号密码
    private void initEdit() {
        SharedPreferences sharedPreferences = this.getSharedPreferences("mmvtc", MODE_PRIVATE);
        String username = sharedPreferences.getString("name", "");
        String password = sharedPreferences.getString("pwd", "");
        name.setText(username);
        pwd.setText(password);
        yzm.requestFocus();
        showYzm();
    }

    //登入成功提示
    private void showHintsMsg(final String s) {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                        recovery();
                    }
                });
            }
        };
        timer.schedule(timerTask, 1000);
    }

    private void inputAnimator(final View view, float w, float h) {
        AnimatorSet set = new AnimatorSet();
        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });

    }

    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new myInterpolator());
        animator3.start();
    }

    private void recovery() {
        progress.setVisibility(View.GONE);
        mInputLayout.setVisibility(View.VISIBLE);
        linearLayout1.setVisibility(View.VISIBLE);
        linearLayout2.setVisibility(View.VISIBLE);
        linearLayout3.setVisibility(View.VISIBLE);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputLayout.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        mInputLayout.setLayoutParams(params);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 0.5f, 1f);
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();
        showYzm();
    }

    public void pwdShowTv(View view) {
        HideReturnsTransformationMethod show = HideReturnsTransformationMethod.getInstance();
        pwd.setTransformationMethod(show);
        view.setVisibility(View.GONE);
        findViewById(R.id.pwdHide).setVisibility(View.VISIBLE);
        pwd.requestFocus();
        pwd.setSelection(pwd.getText().toString().length());
    }

    public void pwdHideTv(View view) {
        PasswordTransformationMethod hide = PasswordTransformationMethod.getInstance();
        pwd.setTransformationMethod(hide);
        view.setVisibility(View.GONE);
        findViewById(R.id.pwdShow).setVisibility(View.VISIBLE);
        pwd.requestFocus();
        pwd.setSelection(pwd.getText().toString().length());
    }
}

class myInterpolator extends LinearInterpolator {
    private float factor;

    public myInterpolator() {
        this.factor = 0.15f;
    }

    @Override
    public float getInterpolation(float input) {
        return (float) (Math.pow(2, -10 * input)
                * Math.sin((input - factor / 4) * (2 * Math.PI) / factor) + 1);
    }
}
