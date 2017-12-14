package com.zxhl.gpsking;


import android.content.Context;
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

public class HomeSy extends Fragment implements View.OnClickListener {

    private View view;
    private Context context;
    private String content;
    private int tag=0;

    public HomeSy(Context context){
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(view==null) {
            view = inflater.inflate(R.layout.hp_content, container, false);
            init();
            content = "Home";
        }
        TextView txt_content1 = (TextView) view.findViewById(R.id.hp_txt_content);
        txt_content1.setText(content);
        return view;
    }

    @Override
    public void onClick(View v) {

    }

    public void init(){}
}
