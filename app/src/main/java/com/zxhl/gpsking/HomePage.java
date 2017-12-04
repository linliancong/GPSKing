package com.zxhl.gpsking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zxhl.util.FragmentUtils;
import com.zxhl.util.MyFragmentPagerAdapter;
import com.zxhl.util.NetWorkBroadcastReceiver;


import org.w3c.dom.Text;

/**
 * Created by Administrator on 2017/11/24.
 */

public class HomePage extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,ViewPager.OnPageChangeListener{

    public static final int PAG_ONE=0;
    public static final int PAG_TWO=1;
    public static final int PAG_THREE=2;
    public static final int PAG_FOUR=3;

    private RadioGroup rg_tab_bar;
    private RadioButton rb_home;
    private RadioButton rb_query;
    private RadioButton rb_me;
    private RadioButton rb_setting;

    private View view_home;
    private View view_query;
    private View view_me;
    private View view_setting;
    private TextView txt_topbar;

    private ViewPager vpager;
    private MyFragmentPagerAdapter mAdapter=null;

    private long mTime=0;
    NetWorkBroadcastReceiver net;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        mAdapter=new MyFragmentPagerAdapter(getSupportFragmentManager(),HomePage.this);
        bindView();
        rb_home.setChecked(true);
    }

    public void bindView() {
        //设置菜单上方的区块
        view_home=findViewById(R.id.view_home);
        view_query=findViewById(R.id.view_query);
        view_me=findViewById(R.id.view_me);
        view_setting=findViewById(R.id.view_setting);
        //顶部标题栏
        txt_topbar= (TextView) findViewById(R.id.txt_topbar);

        //按钮
        rg_tab_bar= (RadioGroup) findViewById(R.id.rg_tab_bar);
        rg_tab_bar.setOnCheckedChangeListener(this);
        //获取第一个按钮，并设置其状态为选中
        rb_home= (RadioButton) findViewById(R.id.rb_home);
        rb_query= (RadioButton) findViewById(R.id.rb_query);
        rb_me= (RadioButton) findViewById(R.id.rb_me);
        rb_setting= (RadioButton) findViewById(R.id.rb_setting);

        //viewPager相关的设置
        vpager= (ViewPager) findViewById(R.id.vpager);
        vpager.setAdapter(mAdapter);
        //vpager.setCurrentItem(0);
        vpager.addOnPageChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_home:
                txt_topbar.setText("首页");
                setSelected();
                view_home.setSelected(true);
                vpager.setCurrentItem(PAG_ONE);
                break;
            case R.id.rb_query:
                txt_topbar.setText("查询");
                setSelected();
                view_query.setSelected(true);
                vpager.setCurrentItem(PAG_TWO);
                break;
            case R.id.rb_me:
                txt_topbar.setText("我的");
                setSelected();
                view_me.setSelected(true);
                vpager.setCurrentItem(PAG_THREE);
                break;
            case R.id.rb_setting:
                txt_topbar.setText("设置");
                setSelected();
                view_setting.setSelected(true);
                vpager.setCurrentItem(PAG_FOUR);
                break;
            default:break;
        }
    }

    public void setSelected(){
        view_home.setSelected(false);
        view_query.setSelected(false);
        view_me.setSelected(false);
        view_setting.setSelected(false);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state状态有3个，0：什么都没做，1：正在滑动，2：滑动完毕
        // 由于ViewPager 放在 RadioButton 后，所以RadioButton 的点击事件会失效。
        if (state==2)
        {
            switch (vpager.getCurrentItem()){
                case HomePage.PAG_ONE:
                    rb_home.setChecked(true);
                    break;
                case HomePage.PAG_TWO:
                    rb_query.setChecked(true);
                    break;
                case HomePage.PAG_THREE:
                    rb_me.setChecked(true);
                    break;
                case HomePage.PAG_FOUR:
                    rb_setting.setChecked(true);
                    break;
            }

        }

    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()-mTime>2000)
        {
            Toast.makeText(getApplicationContext(),"再按一次退出",Toast.LENGTH_SHORT).show();
            mTime=System.currentTimeMillis();
        }
        else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onResume() {
       /* if(net==null)
        {
            net=new NetWorkBroadcastReceiver();
        }
        IntentFilter it=new IntentFilter("android.intent.action.NETWORK_CONNECT_STATE");
        registerReceiver(net,it);*/
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        //unregisterReceiver(net);
        super.onDestroy();
    }

}


