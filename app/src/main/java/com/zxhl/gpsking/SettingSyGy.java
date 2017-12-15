package com.zxhl.gpsking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.zxhl.util.ImgTxtLayout;

/**
 * Created by Administrator on 2017/12/14.
 */

public class SettingSyGy extends AppCompatActivity {
    private ImgTxtLayout imgTxtLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sy_setting_gy);

        imgTxtLayout= (ImgTxtLayout) findViewById(R.id.settinggy_imgtxt_gy);
        imgTxtLayout.setOnClickListener(new ImgTxtLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
