package com.zxhl.gpsking;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.zxhl.util.Constants;
import com.zxhl.util.ImgTxtLayout;
import com.zxhl.util.QRCodeUtil;
import com.zxhl.util.StatusBarUtil;

/**
 * Created by Administrator on 2018/1/24.
 */

public class SettingSyGyShare extends StatusBarUtil {
    private ImgTxtLayout back;
    private ImageView code;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        back=findViewById(R.id.setting_imgtxt_share);
        code=findViewById(R.id.setting_img_share);

        back.setOnClickListener(new ImgTxtLayout.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //以下为生成二维码的代码
        TwoDimensionCode();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.setting_gy_share;
    }

    public void TwoDimensionCode(){
        Bitmap bt= QRCodeUtil.createQRCodeBitmap(Constants.APK_PATH,
                300,"UTF-8","",
                "0", Color.parseColor("#000000"),
                Color.parseColor("#ffffff"),null,
                BitmapFactory.decodeResource(getResources(),R.drawable.gpsking_logo2),
                0.2F);

        code.setBackground(new BitmapDrawable(getResources(), bt));

    }
}
