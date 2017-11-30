package com.zxhl.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zxhl.gpsking.R;


/**
 * Created by Administrator on 2017/11/28.
 */

public class FragmentUtils extends Fragment {

    public static final int PAG_ONE=0;
    public static final int PAG_TWO=1;
    public static final int PAG_THREE=2;
    public static final int PAG_FOUR=3;

    private View view;
    private String content;
    private int tag;
    public FragmentUtils(){}
    public FragmentUtils(String content,int tag) {
        this.content = content;
        this.tag=tag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        switch (tag) {
            case PAG_ONE:
                View view1 = inflater.inflate(R.layout.hp_content, container, false);
                TextView txt_content1 = (TextView) view1.findViewById(R.id.hp_txt_content);
                txt_content1.setText(content);
                view=view1;
            break;
            case PAG_TWO:
                View view2 = inflater.inflate(R.layout.hp_content, container, false);
                TextView txt_content2 = (TextView) view2.findViewById(R.id.hp_txt_content);
                txt_content2.setText(content);
                view=view2;
                break;
            case PAG_THREE:
                View view3 = inflater.inflate(R.layout.hp_content, container, false);
                TextView txt_content3 = (TextView) view3.findViewById(R.id.hp_txt_content);
                txt_content3.setText(content);
                view=view3;
                break;
            case PAG_FOUR:
                View view4 = inflater.inflate(R.layout.hp_content, container, false);
                TextView txt_content4 = (TextView) view4.findViewById(R.id.hp_txt_content);
                txt_content4.setText(content);
                view=view4;
                break;
        }
        return view;
    }
}
