package com.zxhl.gpsking;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zxhl.entity.CarInfo;
import com.zxhl.util.AdapterUtil;
import com.zxhl.util.Constants;
import com.zxhl.util.ImgTxtLayout;
import com.zxhl.util.SharedPreferenceUtils;
import com.zxhl.util.ShowKeyboard;
import com.zxhl.util.StatusBarUtil;
import com.zxhl.util.SwipeRefreshView;
import com.zxhl.util.WebServiceUtils;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2018/1/17.
 */

public class QuerySyBjxx extends StatusBarUtil implements View.OnClickListener,TextWatcher{

    //控件
    private ListView list;
    private ImageView img;
    private TextView text;

    //顶部操作框
    private ImgTxtLayout back;
    private EditText img1;
    private ImageView img2;
    private TextView title;
    private AutoCompleteTextView vehicle;
    private ImageView search;
    private Button getVeh;

    private List<String> autoVehLic=new ArrayList<>();
    private ArrayAdapter<String> adapter;

    //查询等待
    private RelativeLayout bjxx_ly_sche;
    private ImageView bjxx_img_sche;
    private AnimationDrawable anima;

    private SharedPreferenceUtils sp;
    private Context context;

    private List<List<String>> info;
    private AdapterUtil adapterUtil;
    private ArrayList<CarInfo> carInfos;


    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x001:
                    showInfo();
                    list.setVisibility(View.VISIBLE);
                    bjxx_ly_sche.setVisibility(View.GONE);
                    anima.stop();
                    img.setVisibility(View.GONE);
                    text.setVisibility(View.GONE);
                    break;
                case 0x002:
                    list.setVisibility(View.GONE);
                    bjxx_ly_sche.setVisibility(View.GONE);
                    anima.stop();
                    img.setVisibility(View.VISIBLE);
                    text.setVisibility(View.VISIBLE);
                    //Toast.makeText(context,"信息查询失败，请重新查询",Toast.LENGTH_SHORT).show();
                    break;
                case 0x003:
                    adapter=new ArrayAdapter<String>(context,R.layout.simple_autoedit_dropdown_item,R.id.tv_spinner,autoVehLic);
                    vehicle.setAdapter(adapter);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.query_bjxx);

        context=QuerySyBjxx.this;

        init();

        getVehicleLic();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.query_bjxx;
    }

    public void init(){
        sp=new SharedPreferenceUtils(context, Constants.SAVE_USER);
        back=findViewById(R.id.bjxx_imgtxt_title);
        list=findViewById(R.id.bjxx_list);
        img=findViewById(R.id.bjxx_img);
        text=findViewById(R.id.bjxx_text);

        //顶部操作栏
        back=findViewById(R.id.bjxx_imgtxt_title);
        img1=findViewById(R.id.bjxx_edit_img);
        img2=findViewById(R.id.bjxx_img_img);
        title=findViewById(R.id.bjxx_txt_title);
        vehicle=findViewById(R.id.bjxx_auto_vehiclelic);
        search=findViewById(R.id.bjxx_img_serch);
        getVeh=findViewById(R.id.bjxx_btn_get);

        bjxx_ly_sche=findViewById(R.id.bjxx_ly_sche);
        bjxx_img_sche=findViewById(R.id.bjxx_img_sche);
        anima= (AnimationDrawable) bjxx_img_sche.getDrawable();

        carInfos=new ArrayList<>();

        search.setOnClickListener(this);
        getVeh.setOnClickListener(this);
        //vehicle.addTextChangedListener(this);

        back.setOnClickListener(new ImgTxtLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bjxx_img_serch:
                title.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
                img1.setVisibility(View.VISIBLE);
                img2.setVisibility(View.VISIBLE);
                vehicle.setVisibility(View.VISIBLE);
                break;
            case R.id.bjxx_btn_get:
                ShowKeyboard.hideKeyboard(vehicle);
                list.setVisibility(View.GONE);
                bjxx_ly_sche.setVisibility(View.VISIBLE);
                anima.start();
                getVehicleAlarmInfo();
                title.setVisibility(View.VISIBLE);
                search.setVisibility(View.VISIBLE);
                img1.setVisibility(View.GONE);
                img2.setVisibility(View.GONE);
                vehicle.setVisibility(View.GONE);
                break;
        }

    }

    //获取报警信息
    private void getVehicleAlarmInfo(){
        HashMap<String,String> proper=new HashMap<>();
        proper.put("OperatorID",sp.getOperatorID());
        proper.put("VehicleLic",vehicle.getText().toString());

        WebServiceUtils.callWebService(WebServiceUtils.WEB_SERVER_URL, "GetVehicleAlarmInfo", proper, new WebServiceUtils.WebServiceCallBack() {
            @Override
            public void callBack(SoapObject result) {
                if(result!=null){
                    List<List<String>> list=new ArrayList<>();
                    list=parase(result);
                    if(list.size()!=0){
                        info=list;
                        handler.sendEmptyMessage(0x001);
                    }
                    else{
                        handler.sendEmptyMessage(0x002);
                    }
                }
            }
        });
    }

    /**
     *解析SoapObject对象
     *@param result
     * @return
     * */
    private List<List<String>> parase(SoapObject result){
        List<List<String>> lists=new ArrayList<>();
        List<String> list;
        SoapObject soap= (SoapObject) result.getProperty(0);
        if(soap==null) {
            return null;
        }
        for (int i=0;i<soap.getPropertyCount();i++){
            SoapObject soapObject= (SoapObject) soap.getProperty(i);
            list=new ArrayList<>();
            list.add(soapObject.getProperty(0).toString());
            list.add(soapObject.getProperty(1).toString());
            list.add(soapObject.getProperty(2).toString());
            lists.add(list);
        }
        return lists;
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
                    list=parases(result);
                    if(list!=null){
                        autoVehLic=list;
                        handler.sendEmptyMessage(0x003);
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

    public void showInfo(){
        carInfos = new ArrayList<>();

        for(int i=0;i<info.size();i++) {
            if(i==info.size()){
                break;
            }else {
                carInfos.add(new CarInfo(info.get(i).get(0), info.get(i).get(2), info.get(i).get(1), "报警类型：", "报警位置：","报警时间："));
            }
        }

        adapterUtil=new AdapterUtil<CarInfo>(carInfos,R.layout.query_clxx_item){
            @Override
            public void bindView(ViewHolder holder, CarInfo obj) {
                holder.setText(R.id.clxx_item_vehicle_title,obj.getVehicle_title());
                holder.setText(R.id.clxx_item_vehicle,obj.getVehicle());
                holder.setText(R.id.clxx_item_time_title,obj.getTime_title());
                holder.setText(R.id.clxx_item_time,obj.getTime());
                holder.setText(R.id.clxx_item_info_title,obj.getInfo_title());
                holder.setText(R.id.clxx_item_info,obj.getInfo());
            }
        };
        list.setAdapter(adapterUtil);


        /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //这里处理点击，暂时不用
            }
        });*/

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        getVeh.setEnabled(false);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(vehicle.getText().length()!=0){
            getVeh.setEnabled(true);
        }
    }
}