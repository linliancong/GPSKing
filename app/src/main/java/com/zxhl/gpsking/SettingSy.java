package com.zxhl.gpsking;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/12/1.
 */

public class SettingSy extends Fragment implements View.OnClickListener {

    private View view;
    private int tag=0;

    public SettingSy(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(view==null) {
            view = inflater.inflate(R.layout.sy_setting, container, false);
            init();
        }
        return view;
    }

    @Override
    public void onClick(View v) {

    }

    public void init(){}
}
