package com.zxhl.gpsking;


import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.zxhl.util.Constants;
import com.zxhl.util.ImgTxtLayout;
import com.zxhl.util.SharedPreferenceUtils;
import com.zxhl.util.ShowKeyboard;
import com.zxhl.util.StatusBarUtil;
import com.zxhl.util.WebServiceUtils;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/3/11.
 */

public class QuerySyClgl extends StatusBarUtil implements View.OnClickListener{

    //顶部操作框
    private ImgTxtLayout back;
    private EditText img1;
    private ImageView img2;
    private TextView title;
    private AutoCompleteTextView vehicle;
    private ImageView search;
    private Button getVeh;

    private List<String> autoVehLic=new ArrayList<>();
    private List<String> vehicleInfo=new ArrayList<>();
    private ArrayAdapter<String> adapter;

    //数据显示栏
    private EditText jh;
    private Spinner fz;
    private Spinner jxxh;
    private Button update;


    //查询相关
    private RelativeLayout clgl_ly_sche;
    private ImageView clgl_img_sche;
    private AnimationDrawable anima;

    private SharedPreferenceUtils sp;
    private Map<String,String> map;

    //分组信息绑定
    private ArrayAdapter<String> adapterGroup =null;
    private List<String> groupID =new ArrayList<>();
    private List<String> groupName =new ArrayList<>();
    private int mGroupPosition =0;

    //机械型号信息绑定
    private ArrayAdapter<String> adapterModel =null;
    private List<String> modelID =new ArrayList<>();
    private List<String> modelName =new ArrayList<>();
    private int mModelPosition =0;

    private int state=0;

    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x403:
                    anima.stop();
                    clgl_ly_sche.setVisibility(View.GONE);
                    Toast.makeText(QuerySyClgl.this,"没有找到车辆信息，请换一个机号再试一次",Toast.LENGTH_SHORT).show();
                    break;
                case 0x001:
                    adapter=new ArrayAdapter<String>(QuerySyClgl.this,R.layout.simple_autoedit_dropdown_item,R.id.tv_spinner,autoVehLic);
                    vehicle.setAdapter(adapter);
                    break;
                case 0x002:
                    //显示车辆信息
                    anima.stop();
                    clgl_ly_sche.setVisibility(View.GONE);
                    jh.setText(vehicleInfo.get(1));
                    jh.setSelection(vehicleInfo.get(1).length());
                    int i;
                    for(i=0;i<groupID.size();i++){
                        if(groupID.get(i).toString().equals(vehicleInfo.get(2))){
                            break;
                        }
                    }
                    fz.setSelection(i,true);
                    for(i=0;i<modelID.size();i++){
                        if(modelID.get(i).toString().equals(vehicleInfo.get(3))){
                            break;
                        }
                    }
                    jxxh.setSelection(i,true);
                    update.setEnabled(true);
                    break;
                case 0x003:
                    adapterGroup =new ArrayAdapter<String>(QuerySyClgl.this, R.layout.simple_spinner_item,R.id.tv_spinner, groupName);
                    adapterGroup.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

                    fz.setAdapter(adapterGroup);

                    fz.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            mGroupPosition =position;
                            ShowKeyboard.hideKeyboard(jh);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    break;
                case 0x004:
                    adapterModel =new ArrayAdapter<String>(QuerySyClgl.this, R.layout.simple_spinner_item,R.id.tv_spinner, modelName);
                    adapterModel.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

                    jxxh.setAdapter(adapterModel);

                    jxxh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            mModelPosition =position;
                            ShowKeyboard.hideKeyboard(jh);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    break;
                case 0x005:
                    if(state==1){
                        getVehicleLic();
                        update.setEnabled(false);
                        jh.setText("");
                        jxxh.setSelection(0,true);
                        fz.setSelection(0,true);
                        Toast.makeText(QuerySyClgl.this,"修改车辆信息成功",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(QuerySyClgl.this,"修改车辆信息失败，请稍后重试",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }

        }
    };


    @Override
    protected int getLayoutResId() {
        return R.layout.query_clgl;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        getVehicleLic();
        getVGroupInfo();
        getVModelInfo();
    }

    private void init() {
        sp=new SharedPreferenceUtils(this, Constants.SAVE_USER);
        map=new HashMap<>();

        clgl_ly_sche=findViewById(R.id.clgl_ly_sche);
        clgl_img_sche=findViewById(R.id.clgl_img_sche);
        anima= (AnimationDrawable) clgl_img_sche.getDrawable();

        //顶部操作栏
        back=findViewById(R.id.clgl_imgtxt_title);
        img1=findViewById(R.id.clgl_edit_img);
        img2=findViewById(R.id.clgl_img_img);
        title=findViewById(R.id.clgl_txt_title);
        vehicle=findViewById(R.id.clgl_auto_vehiclelic);
        search=findViewById(R.id.clgl_img_serch);
        getVeh=findViewById(R.id.clgl_btn_get);

        //数据显示栏
        jh=findViewById(R.id.query_clgl_jh);
        fz=findViewById(R.id.query_clgl_fz);
        jxxh=findViewById(R.id.query_clgl_jxxh);
        update=findViewById(R.id.query_clgl_xg);

        back.setOnClickListener(new ImgTxtLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        update.setEnabled(false);
        search.setOnClickListener(this);
        getVeh.setOnClickListener(this);
        update.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.clgl_img_serch:
                title.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
                img1.setVisibility(View.VISIBLE);
                img2.setVisibility(View.VISIBLE);
                vehicle.setVisibility(View.VISIBLE);
                break;
            case R.id.clgl_btn_get:
                ShowKeyboard.hideKeyboard(vehicle);
                int permiss=0;
                for(int i=0;i<autoVehLic.size();i++)
                {
                    if(vehicle.getText().toString().equalsIgnoreCase(autoVehLic.get(i))){
                        permiss=1;
                        break;
                    }
                }
                if(permiss==1){
                    anima.start();
                    clgl_ly_sche.setVisibility(View.VISIBLE);
                    GetVehiclInfo();
                    title.setVisibility(View.VISIBLE);
                    search.setVisibility(View.VISIBLE);
                    img1.setVisibility(View.GONE);
                    img2.setVisibility(View.GONE);
                    vehicle.setVisibility(View.GONE);
                }
                else{
                    Toast.makeText(QuerySyClgl.this,"机号输入有误或者您没有权限操作",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.query_clgl_xg:
                //更新信息
                ShowKeyboard.hideKeyboard(jh);
                UpdateVehicleInfo();
                break;
        }

    }

    //获取车辆列表
    private void getVehicleLic(){
        HashMap<String,String> proper=new HashMap<>();
        proper.put("OperatorID",sp.getOperatorID());
        proper.put("Key","");

        WebServiceUtils.callWebService(WebServiceUtils.WEB_SERVER_URL, "GetVehiclelicByKey", proper, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(SoapObject result) {
                if(result!=null){
                    List<String> list=new ArrayList<String>();
                    list=parase(result);
                    if(list!=null){
                        autoVehLic=list;
                        handler.sendEmptyMessage(0x001);
                    }
                }
            }
        });
    }

    //获取车辆信息
    public void GetVehiclInfo(){
        HashMap<String,String> proper=new HashMap<>();
        proper.put("OperatorID",sp.getOperatorID());
        proper.put("VehicleLic",vehicle.getText().toString());

        WebServiceUtils.callWebService(WebServiceUtils.WEB_SERVER_URL, "VehicleInfo", proper, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(SoapObject result) {
                if(result!=null){
                    List<String> list=new ArrayList<String>();
                    list=parase(result);
                    if(list.size()>0){
                        vehicleInfo=list;
                        handler.sendEmptyMessage(0x002);
                    }else
                    {
                        handler.sendEmptyMessage(0x403);
                    }
                }
            }
        });
    }

    private List<String> parase(SoapObject result){
        List<String> list=new ArrayList<>();
        SoapObject soap= (SoapObject) result.getProperty(0);
        if(soap==null) {
            return null;
        }
        for (int i=0;i<soap.getPropertyCount();i++){
            list.add(soap.getProperty(i).toString());
        }
        return list;
    }

    private void UpdateVehicleInfo(){
        //VUpdateVehicle 更新操作double VehicleID, String VehicleLic, int MModelID, double VGroupID
        HashMap<String, String> proper = new HashMap<String, String>();
        proper.put("VehicleID", vehicleInfo.get(0));
        proper.put("VehicleLic", jh.getText().toString());
        proper.put("MModelID", modelID.get(mModelPosition));
        proper.put("VGroupID", groupID.get(mGroupPosition));
        WebServiceUtils.callWebService(WebServiceUtils.WEB_SERVER_URL, "VUpdateVehicle", proper, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(SoapObject result) {
                if (result != null) {
                    Integer it = new Integer(result.getProperty(0).toString());
                    state = it.intValue();
                } else {
                    state = 0;
                }
                handler.sendEmptyMessage(0x005);
            }
        });
    }

    //获取分组信息
    public void getVGroupInfo(){
        HashMap<String, String> proper = new HashMap<String, String>();
        WebServiceUtils.callWebService(WebServiceUtils.WEB_SERVER_URL, "VGroupInfo", proper, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(SoapObject result) {
                if (result != null) {
                    List<String> list = new ArrayList<String>();
                    list = parases(result);
                    if (list != null) {
                        for (int i=0;i<list.size();i++) {
                            groupID.add(list.get(i));
                            groupName.add(list.get(++i));
                        }
                        handler.sendEmptyMessage(0x003);
                    }
                }
            }
        });
    }

    //获取机械型号信息
    public void getVModelInfo(){
        HashMap<String, String> proper = new HashMap<String, String>();
        WebServiceUtils.callWebService(WebServiceUtils.WEB_SERVER_URL, "VMachineModel", proper, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(SoapObject result) {
                if (result != null) {
                    List<String> list = new ArrayList<String>();
                    list = parases(result);
                    if (list != null) {
                        for (int i=0;i<list.size();i++) {
                            modelID.add(list.get(i));
                            modelName.add(list.get(++i));
                        }
                        handler.sendEmptyMessage(0x004);
                    }
                }
            }
        });
    }

    private List<String> parases(SoapObject result){
        List<String> list=new ArrayList<>();
        SoapObject soap= (SoapObject) result.getProperty(0);
        if(soap==null) {
            return null;
        }
        for (int i=0;i<soap.getPropertyCount();i++){
            list.add(soap.getProperty(i).toString());
        }
        return list;
    }
}
