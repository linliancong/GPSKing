package com.zxhl.gpsking;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/12/4.
 */

public class QuerySy extends Fragment implements View.OnClickListener {

    private View view;
    private String content;
    private int tag=0;

    public QuerySy(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.hp_content, container, false);
        content="Query";
        init();
        TextView txt_content1 = (TextView) view.findViewById(R.id.hp_txt_content);
        txt_content1.setText(content);
        return view;
    }

    @Override
    public void onClick(View v) {

    }

    public void init(){}
}
