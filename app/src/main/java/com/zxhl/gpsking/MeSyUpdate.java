package com.zxhl.gpsking;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.zxhl.util.ImgTxtLayout;

/**
 * Created by Administrator on 2017/12/7.
 */

public class MeSyUpdate extends AppCompatActivity implements TextWatcher,View.OnClickListener{

    private ImgTxtLayout me_imgtxt_update;
    private Button me_btn_update;
    private EditText me_txt_update;

    private String content;
    private int value;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sy_me_info);

        init();


    }

    public void init() {
        me_imgtxt_update= (ImgTxtLayout) findViewById(R.id.me_imgtxt_update);
        me_btn_update= (Button) findViewById(R.id.me_btn_update);
        me_txt_update= (EditText) findViewById(R.id.me_txt_update);

        Intent it=getIntent();
        Bundle bd=it.getExtras();
        content=bd.getString("STR");
        value= bd.getInt("VALUE");
        me_txt_update.setText(content);
        me_txt_update.setSelection(content.length());

        me_txt_update.addTextChangedListener(this);
        me_btn_update.setOnClickListener(this);
        me_imgtxt_update.setOnClickListener(new ImgTxtLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        switch (value){
            case 1:
                me_imgtxt_update.setText("姓名");
                break;
            case 2:
                me_imgtxt_update.setText("公司名称");
                break;
            case 3:
                me_imgtxt_update.setText("联系电话");
                break;
            case 4:
                me_imgtxt_update.setText("QQ");
                break;
            case 5:
                me_imgtxt_update.setText("Email");
                break;
            case 6:
                me_imgtxt_update.setText("身份证号");
                break;
            case 7:
                me_imgtxt_update.setText("籍贯");
                break;
            case 8:
                me_imgtxt_update.setText("家庭地址");
                break;
            case 9:
                me_imgtxt_update.setText("家庭电话");
                break;
            case 10:
                me_imgtxt_update.setText("备注");
                break;
            default:break;

        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        me_btn_update.setEnabled(false);

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(me_txt_update.getText().toString().length()!=0){
            me_btn_update.setEnabled(true);
        }

    }
}
