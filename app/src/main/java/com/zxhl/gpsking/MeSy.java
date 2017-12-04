package com.zxhl.gpsking;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zxhl.util.Constants;
import com.zxhl.util.ImgTxtLayout;
import com.zxhl.util.SharedPreferenceUtils;
import com.zxhl.util.WebServiceUtils;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/12/4.
 */

public class MeSy extends Fragment implements View.OnClickListener {

    private View view;
    private int tag=0;
    private SharedPreferenceUtils sp;
    private Context context;
    HashMap<String,String> map=null;
    List<Map<String,String>> list=null;

    private RelativeLayout me_ly_tx;
    private RelativeLayout me_ly_zh;
    private RelativeLayout me_ly_xm;
    private RelativeLayout me_ly_lx;
    private RelativeLayout me_ly_fz;
    private RelativeLayout me_ly_gd;

    private ImageView me_img_tx;
    private TextView me_txt_tx;
    private TextView me_txt_tx2;

    private ImgTxtLayout me_imgtxt_zh;
    private ImgTxtLayout me_imgtxt_xm;
    private ImgTxtLayout me_imgtxt_lx;
    private ImgTxtLayout me_imgtxt_fz;

    public MeSy(Context context){
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sy_me, container, false);
        sp=new SharedPreferenceUtils(context, Constants.SAVE_USER);
        list=new ArrayList<Map<String,String>>();
        init();
        getUserInfo();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.me_ly_tx:
                break;
            case R.id.me_ly_zh:
                break;
            case R.id.me_ly_xm:
                break;
            case R.id.me_ly_lx:
                break;
            case R.id.me_ly_fz:
                break;
            case R.id.me_ly_gd:
                Intent it=new Intent(context,MeSyGd.class);
                Bundle bd=new Bundle();
                ArrayList bundlist=new ArrayList();
                bundlist.add(list);
                bd.putParcelableArrayList("list",bundlist);
                it.putExtras(bd);
                startActivity(it);
                break;
        }

    }

    public void init(){
        if(tag==0) {
            me_ly_tx= (RelativeLayout) view.findViewById(R.id.me_ly_tx);
            me_ly_zh= (RelativeLayout) view.findViewById(R.id.me_ly_zh);
            me_ly_xm= (RelativeLayout) view.findViewById(R.id.me_ly_xm);
            me_ly_lx= (RelativeLayout) view.findViewById(R.id.me_ly_lx);
            me_ly_fz= (RelativeLayout) view.findViewById(R.id.me_ly_fz);
            me_ly_gd= (RelativeLayout) view.findViewById(R.id.me_ly_gd);

            me_img_tx= (ImageView) view.findViewById(R.id.me_img_tx);
            me_txt_tx= (TextView) view.findViewById(R.id.me_txt_tx);
            me_txt_tx2= (TextView) view.findViewById(R.id.me_txt_tx2);

            me_imgtxt_zh= (ImgTxtLayout) view.findViewById(R.id.me_imgtxt_zh);
            me_imgtxt_xm= (ImgTxtLayout) view.findViewById(R.id.me_imgtxt_xm);
            me_imgtxt_lx= (ImgTxtLayout) view.findViewById(R.id.me_imgtxt_lx);
            me_imgtxt_fz= (ImgTxtLayout) view.findViewById(R.id.me_imgtxt_fz);

            me_ly_tx.setOnClickListener(this);
            me_ly_zh.setOnClickListener(this);
            me_ly_xm.setOnClickListener(this);
            me_ly_lx.setOnClickListener(this);
            me_ly_fz.setOnClickListener(this);
            me_ly_gd.setOnClickListener(this);
            tag=1;
        }

    }

    public void getUserInfo(){
        HashMap<String,String> prepro=new HashMap<String,String>();
        prepro.put("OperatorID",sp.getOperatorID());

        WebServiceUtils.callWebService(WebServiceUtils.WEB_SERVER_URL, "GetOperatorInfo", prepro, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(SoapObject result) {
                if(result!=null)
                {
                    map=parseSoap(result);
                    if(map.size()==0)
                    {
                        return;
                    }
                    me_txt_tx.setText(map.get("姓名"));
                    me_txt_tx2.setText(map.get("帐号"));
                    me_imgtxt_zh.setText(map.get("帐号"));
                    me_imgtxt_xm.setText(map.get("姓名"));
                    me_imgtxt_lx.setText(map.get("类型"));
                    me_imgtxt_fz.setText(map.get("分组"));
                    list.add(map);
                }
            }
        });
    }

    /*
    *解析SoapObject对象
    *@param result
    * @return
    * */
    public HashMap<String,String> parseSoap(SoapObject result){
        HashMap<String,String> map=new HashMap<String,String>();
        String[] str=new String[2];
        SoapObject soapObject= (SoapObject) result.getProperty("GetOperatorInfoResult");
        if(soapObject==null)
        {
            return null;
        }
        for (int i=0;i<soapObject.getPropertyCount();i++){
            //查询回来的数据为user:lin的格式，故先截取
            str=soapObject.getProperty(i).toString().split(":");
            if (str.length==2) {
                map.put(str[0],str[1]);
            }
            else
            {
                map.put(str[0],"");
            }
        }
        return map;

    }
}
