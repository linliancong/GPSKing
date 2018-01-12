package com.zxhl.gpsking;


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
import android.widget.TextView;
import android.widget.Toast;

import com.zxhl.entity.Icon;
import com.zxhl.util.AdapterUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/4.
 */

public class QuerySy extends Fragment implements View.OnClickListener {

    private View view;
    private Context context;
    private GridView grid_icon;
    private AdapterUtil adapter=null;
    private ArrayList<Icon> mData=null;
    private int tag=0;

    public QuerySy(){
    }
    public QuerySy(Context context){
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if(view==null) {
            view = inflater.inflate(R.layout.sy_query, container, false);
            init();
        }
        return view;
    }

    @Override
    public void onClick(View v) {

    }

    public void init(){
        grid_icon= (GridView) view.findViewById(R.id.grid_icon);

        mData=new ArrayList<Icon>();
        mData.add(new Icon(R.drawable.ssdw,"实时定位"));
        mData.add(new Icon(R.drawable.ccdh,"查车导航"));
        mData.add(new Icon(R.drawable.gjhf,"轨迹回放"));
        mData.add(new Icon(R.drawable.gkxx,"工况信息"));
        mData.add(new Icon(R.drawable.bbtj,"报表统计"));
        mData.add(new Icon(R.drawable.yjcl,"样机车辆"));
        mData.add(new Icon(R.drawable.bjxx,"报警信息"));
        mData.add(new Icon(R.drawable.scxx,"锁车信息"));
        mData.add(new Icon(R.drawable.bytx,"保养提醒"));
        mData.add(new Icon(R.drawable.sbxx,"设备信息"));
        mData.add(new Icon(R.drawable.yycl,"预约车辆"));
        mData.add(new Icon(0,null));

        adapter=new AdapterUtil<Icon>(mData,R.layout.item_grid_icon) {
            @Override
            public void bindView(ViewHolder holder, Icon obj) {
                holder.setImageResource(R.id.img_icon,obj.getmId());
                holder.setText(R.id.txt_icon,obj.getmName());
                //holder.setVisibility(R.id.view_icon,View.VISIBLE);
            }
        };

        grid_icon.setAdapter(adapter);

        grid_icon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        //实时定位
                        Intent it0=new Intent(context,QuerySyLocation.class);
                        startActivity(it0);
                        break;
                    case 1:
                        //查车导航
                        Intent it1=new Intent(context,QuerySyNavi.class);
                        startActivity(it1);
                        break;
                    case 2:
                        //轨迹回放
                        Intent it2=new Intent(context,QuerySyTrackPlayback.class);
                        startActivity(it2);
                        break;
                    case 3:
                        //工况信息
                        Toast.makeText(context,"工况信息功能还没有开放",Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        //报表统计
                        Toast.makeText(context,"报表统计功能还没有开放",Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        //样机车辆
                        Toast.makeText(context,"样机车辆功能还没有开放",Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        //报警信息
                        Toast.makeText(context,"报警信息功能还没有开放",Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        //锁车信息
                        Toast.makeText(context,"锁车信息功能还没有开放",Toast.LENGTH_SHORT).show();
                        break;
                    case 8:
                        //保养提醒
                        Toast.makeText(context,"保养提醒功能还没有开放",Toast.LENGTH_SHORT).show();
                        break;
                    case 9:
                        //设备信息
                        Toast.makeText(context,"设备信息功能还没有开放",Toast.LENGTH_SHORT).show();
                        break;
                    case 10:
                        //预约车辆
                        Toast.makeText(context,"预约车辆功能还没有开放",Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });


    }
}
