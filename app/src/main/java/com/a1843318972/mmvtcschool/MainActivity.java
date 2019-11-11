package com.a1843318972.mmvtcschool;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.a1843318972.mmvtcschool.fragment.studentchenji_fragment;
import com.a1843318972.mmvtcschool.fragment.studentinfo_fragment;
import com.a1843318972.mmvtcschool.fragment.studentkebiao_fragment;

public class MainActivity extends BaseActivity {

    private SlidingDrawer slidingDrawer;
    private ImageView lay_img;
    private LinearLayout content_tv;
    private int[] tubiaoimg = {R.drawable.xuesheng, R.drawable.kebiao, R.drawable.chengji};
    private String[] tubiaostr = {"学生信息", "学生课表", "学生成绩"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        showANDelouse();
        findViewById(R.id.fragment_main).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                slidingDrawer.animateClose();
                return true;
            }
        });
    }

    private void initView() {
        findViewById(R.id.login_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        slidingDrawer = findViewById(R.id.SlidingDrawer);
        lay_img = findViewById(R.id.handle);
        content_tv = findViewById(R.id.content_tv);
        slidingDrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                lay_img.setImageDrawable(getResources().getDrawable(R.drawable.up_fill));
            }
        });
        slidingDrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {
            @Override
            public void onDrawerOpened() {
                lay_img.setImageDrawable(getResources().getDrawable(R.drawable.down_fill));
            }
        });
        slidingDrawer.setOnDrawerScrollListener(new SlidingDrawer.OnDrawerScrollListener() {
            @Override
            public void onScrollStarted() {
                lay_img.setImageDrawable(getResources().getDrawable(R.drawable.yuan));
            }

            @Override
            public void onScrollEnded() {
                lay_img.setImageDrawable(getResources().getDrawable(R.drawable.yuan));
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, studentinfo_fragment.newInstance()).commit();

        for (int i = 0; i < tubiaoimg.length; i++) {
            final View tp_tv = LayoutInflater.from(this).inflate(R.layout.item_fragment, null);
            tp_tv.setId(i);
            tp_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case 0:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, studentinfo_fragment.newInstance()).commit();
                            slidingDrawer.animateClose();
                            break;
                        case 1:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, studentkebiao_fragment.newInstance()).commit();
                            slidingDrawer.animateClose();
                            break;
                        case 2:
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_main, studentchenji_fragment.newInstance()).commit();
                            slidingDrawer.animateClose();
                            break;
                    }
                }
            });
            ImageView imageView = tp_tv.findViewById(R.id.a2);
            TextView textView = tp_tv.findViewById(R.id.a3);
            textView.setText(tubiaostr[i]);
            imageView.setImageDrawable(getResources().getDrawable(tubiaoimg[i]));
            content_tv.addView(tp_tv);
        }

    }

    private void showANDelouse() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                slidingDrawer.animateOpen();
            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                slidingDrawer.animateClose();
            }
        }, 2000);
    }
}
