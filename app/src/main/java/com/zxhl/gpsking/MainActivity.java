package com.zxhl.gpsking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView img;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what)
            {
                case 0x123:
                    Intent intent=new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                    finish();
                    break;
                case 0x001:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img= (ImageView) findViewById(R.id.img);
        int tag=scaleImage(this,img,R.drawable.login_bg);

        handler.sendEmptyMessageDelayed(tag,3000);

    }

    public static int scaleImage(final Activity activity, final View view, int drawableResId) {

        // 获取屏幕的高宽
        Point outSize = new Point();
        activity.getWindow().getWindowManager().getDefaultDisplay().getSize(outSize);

        // 解析将要被处理的图片
        Bitmap resourceBitmap = BitmapFactory.decodeResource(activity.getResources(), drawableResId);

        if (resourceBitmap == null) {
            return 0x001;
        }

        // 开始对图片进行拉伸或者缩放

        // 使用图片的缩放比例计算将要放大的图片的高度
        int bitmapScaledHeight = Math.round(resourceBitmap.getHeight() * outSize.x * 1.0f / resourceBitmap.getWidth());

        // 以屏幕的宽度为基准，如果图片的宽度比屏幕宽，则等比缩小，如果窄，则放大
        final Bitmap scaledBitmap = Bitmap.createScaledBitmap(resourceBitmap, outSize.x, bitmapScaledHeight, false);

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                //这里防止图像的重复创建，避免申请不必要的内存空间
                if (scaledBitmap.isRecycled())
                    //必须返回true
                    return true;


                // 当UI绘制完毕，我们对图片进行处理
                int viewHeight = view.getMeasuredHeight();


                // 计算将要裁剪的图片的顶部以及底部的偏移量
                int offset = (scaledBitmap.getHeight() - viewHeight) / 2;


                // 对图片以中心进行裁剪，裁剪出的图片就是非常适合做引导页的图片了
                Bitmap finallyBitmap = Bitmap.createBitmap(scaledBitmap, 0, offset, scaledBitmap.getWidth(),
                        scaledBitmap.getHeight() - offset * 2);


                if (!finallyBitmap.equals(scaledBitmap)) {//如果返回的不是原图，则对原图进行回收
                    scaledBitmap.recycle();
                    System.gc();
                }


                // 设置图片显示
                view.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), finallyBitmap));
                return true;
            }
        });
        return 0x123;
    }
}
