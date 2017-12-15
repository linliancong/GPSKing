package com.zxhl.gpsking;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zxhl.util.Constants;
import com.zxhl.util.SharedPreferenceUtils;

/**
 * Created by Administrator on 2017/12/1.
 */

public class SettingSy extends Fragment implements View.OnClickListener {

    private View view;
    private Context context;
    private int tag=0;

    private RelativeLayout setting_ly_gy;
    private RelativeLayout setting_ly_jcgx;
    private RelativeLayout setting_ly_wtfk;
    private RelativeLayout setting_ly_xgmm;
    private RelativeLayout setting_ly_tc;

    //private View ad_view;
    private AlertDialog alert;
    private AlertDialog.Builder builder;
    private LayoutInflater inflater;

    private ShowAct showAct;

    public SettingSy(Context context){
        this.context=context;
    }

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x0001:
                    showAct = (ShowAct) getActivity();
                    showAct.callBack(0x0001);
                    break;
                case 0x0002:
                    showAct = (ShowAct) getActivity();
                    showAct.callBack(0x0002);
                    break;
            }
        }
    };



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
        switch (v.getId()){
            case R.id.setting_ly_gy:
                Intent it1=new Intent(context,SettingSyGy.class);
                startActivity(it1);
                break;
            case R.id.setting_ly_jcgx:
                break;
            case R.id.setting_ly_wtfk:
                break;
            case R.id.setting_ly_xgmm:
                View ad_view2= getAlert(R.layout.ad_input_pass);
                final EditText editText= (EditText) ad_view2.findViewById(R.id.ad_edit_pass);
                ad_view2.findViewById(R.id.ad_btn_pass_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //handler.sendEmptyMessage(0x0001);
                        alert.dismiss();
                    }
                });
                ad_view2.findViewById(R.id.ad_btn_pass_confirm).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //handler.sendEmptyMessage(0x0002);
                        SharedPreferenceUtils sp=new SharedPreferenceUtils(context, Constants.SAVE_USER);
                        if(sp.getPWD().equals(editText.getText().toString())){
                            handler.sendEmptyMessage(0x0003);
                            alert.dismiss();
                        }
                        else {
                            alert.dismiss();
                            View view=getAlert(R.layout.ad_pass_erro);
                            TextView txt= (TextView) view.findViewById(R.id.ad_txt_erro2);
                            //String name=editText.getText().toString();
                            if(editText.getText().toString().equals("")){
                                txt.setText("原密码不能为空。");
                            }
                            view.findViewById(R.id.ad_btn_erro_confirm).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alert.dismiss();
                                }
                            });
                        }
                    }
                });
                break;
            case R.id.setting_ly_tc:
                View ad_view1=getAlert(R.layout.ad_exit);
                ad_view1.findViewById(R.id.ad_ly_user).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler.sendEmptyMessage(0x0001);
                        alert.dismiss();
                    }
                });
                ad_view1.findViewById(R.id.ad_ly_exit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        handler.sendEmptyMessage(0x0002);
                        alert.dismiss();
                    }
                });
                break;
        }

    }

    public void init(){


        setting_ly_gy= (RelativeLayout) view.findViewById(R.id.setting_ly_gy);
        setting_ly_jcgx= (RelativeLayout) view.findViewById(R.id.setting_ly_jcgx);
        setting_ly_wtfk= (RelativeLayout) view.findViewById(R.id.setting_ly_wtfk);
        setting_ly_xgmm= (RelativeLayout) view.findViewById(R.id.setting_ly_xgmm);
        setting_ly_tc= (RelativeLayout) view.findViewById(R.id.setting_ly_tc);

        setting_ly_gy.setOnClickListener(this);
        setting_ly_jcgx.setOnClickListener(this);
        setting_ly_wtfk.setOnClickListener(this);
        setting_ly_xgmm.setOnClickListener(this);
        setting_ly_tc.setOnClickListener(this);
    }

    //定义接口
    public interface ShowAct{
        public void callBack(int result);
    }
    //定义接口回调
    public void getData(ShowAct act){
        act.callBack(tag);
    }

    //定义弹窗方法
    public View getAlert(int mLayout){
        View ad_view;
        //初始化Builder
        builder=new AlertDialog.Builder(context);
        //完成相关设置
        inflater=getActivity().getLayoutInflater();
        ad_view=inflater.inflate(mLayout,null,false);
        builder.setView(ad_view);
        builder.setCancelable(true);
        alert=builder.create();
        alert.show();
        return ad_view;
    }
}
