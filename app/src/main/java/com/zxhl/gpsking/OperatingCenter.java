package com.zxhl.gpsking;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.zxhl.entity.Icon;
import com.zxhl.util.AdapterUtil;
import com.zxhl.util.ImgTxtLayout;
import com.zxhl.util.StatusBarUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/4.
 */

public class OperatingCenter extends StatusBarUtil implements View.OnClickListener {

    private Context context;

    private ImgTxtLayout back;
    private RelativeLayout location;
    private RelativeLayout lock;
    private RelativeLayout unlock;
    private RelativeLayout monitor;
    private RelativeLayout oil_ele;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=OperatingCenter.this;

        location=findViewById(R.id.opc_ly_ssdw);
        lock=findViewById(R.id.opc_ly_sc);
        unlock=findViewById(R.id.opc_ly_js);
        monitor=findViewById(R.id.opc_ly_jk);
        oil_ele=findViewById(R.id.opc_ly_yd);
        back=findViewById(R.id.opc_imgtxt_back);

        location.setOnClickListener(this);
        lock.setOnClickListener(this);
        unlock.setOnClickListener(this);
        monitor.setOnClickListener(this);
        oil_ele.setOnClickListener(this);
        back.setOnClickListener(new ImgTxtLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.operatingcenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.opc_ly_ssdw:
                //实时定位
                Intent it1=new Intent(context,OpcLocation.class);
                startActivity(it1);
                break;
            case R.id.opc_ly_sc:
                //锁车
                break;
            case R.id.opc_ly_js:
                //解锁
                break;
            case R.id.opc_ly_jk:
                //监控
                break;
            case R.id.opc_ly_yd:
                //油电控制
                break;
        }

    }


}