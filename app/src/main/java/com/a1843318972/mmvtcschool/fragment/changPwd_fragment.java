package com.a1843318972.mmvtcschool.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a1843318972.mmvtcschool.R;
import com.a1843318972.mmvtcschool.config.jwcUtil;

public class changPwd_fragment extends Fragment {
    private static changPwd_fragment fragment;
    private jwcUtil jwcutil = com.a1843318972.mmvtcschool.config.jwcUtil.getInstance();

    public static changPwd_fragment newInstance() {
        if (fragment == null) {
            fragment = new changPwd_fragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xiugaimima_fragment, null);
    }
}
