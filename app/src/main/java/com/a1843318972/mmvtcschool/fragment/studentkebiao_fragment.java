package com.a1843318972.mmvtcschool.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.a1843318972.mmvtcschool.Adapter.kb_Adapter;
import com.a1843318972.mmvtcschool.Interface.Ikebiao;
import com.a1843318972.mmvtcschool.R;
import com.a1843318972.mmvtcschool.config.jwcUtil;
import com.a1843318972.mmvtcschool.entity.kebiao;

import java.util.ArrayList;

import static com.a1843318972.mmvtcschool.entity.kebiao.getkebiao;

public class studentkebiao_fragment extends Fragment {
    private jwcUtil jwcutil = com.a1843318972.mmvtcschool.config.jwcUtil.getInstance();
    private ListView ls_kb;
    private LinearLayout top_kb;
    private kb_Adapter kb_adapter;
    private static studentkebiao_fragment fragment;

    public static studentkebiao_fragment newInstance() {
        if (fragment == null) {
            fragment = new studentkebiao_fragment();
        }
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.xueshengkebiao_fragment, null);
        top_kb = (LinearLayout) view.findViewById(R.id.top_kb);
        ls_kb = (ListView) view.findViewById(R.id.ls_kb);
        jwcutil.getKeBiao(new Ikebiao() {
            @Override
            public void kebiao(final ArrayList<kebiao> arrayList) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initTable(arrayList);
                    }
                });
            }
        });
        setRetainInstance(true);
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void initTable(ArrayList<kebiao> dataList) {
        for (int t = 0; t < getkebiao(dataList, 0).size(); t++) {
            View tp_tv = LayoutInflater.from(getContext()).inflate(R.layout.kb_top, null);
            TextView title = (TextView) tp_tv.findViewById(R.id.t_title);
            title.setText(getkebiao(dataList, 0).get(t));
            title.setEms(9);
            if (t == 0)
                title.setWidth(110);
            else
                title.setWidth(250);
            title.setHeight(100);
            top_kb.addView(tp_tv);
        }
        kb_adapter = new kb_Adapter(getContext(), dataList, 8);
        ls_kb.setAdapter(kb_adapter);
        Log.i("课表", "initTable: " + dataList.size() + dataList.get(0).toString());
    }
}
