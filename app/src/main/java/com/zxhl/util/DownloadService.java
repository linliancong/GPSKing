package com.zxhl.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.zxhl.gpsking.R;
import com.zxhl.gpsking.SettingSy;
import com.zxhl.util.FileDownloadUtil.DownloadProgressListener;
import com.zxhl.util.FileDownloadUtil.FileDownloadered;

import java.io.File;

/**
 * Created by Administrator on 2017/12/19.
 */

public class DownloadService extends Service{

    private boolean quit=false;
    private boolean stop=false;
    private boolean state=false;
    private myBinder binder=new myBinder();
    private DownloadService dl;

    private int MaxPro;
    private File file=null;
    private NotificationManager manager=null;
    private NotificationCompat.Builder bd=null;

    private Intent intent;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x001:
                    //stop();
                    if(!quit) {
                        int size = msg.getData().getInt("size");
                        float num = (float) size / MaxPro;
                        int result = (int) (num * 100);
                        bd.setProgress(100, result, false);
                        bd.setContentTitle("正在下载");
                        bd.setContentText("下载" + result + "%");
                        intent.putExtra("operator",2);
                        PendingIntent pendingIntent=PendingIntent.getService(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                        bd.setContentIntent(pendingIntent);
                        manager.notify(1, bd.build());
                        if (MaxPro == size) {
                            //Toast.makeText(context,"文件下载成功",Toast.LENGTH_SHORT).show();
                            update();
                            //manager.cancel(1);
                        }
                    }
                    break;
                case 0x002:
                    manager.cancel(1);
                    bd.setContentText("点击重新下载");
                    bd.setContentTitle("下载失败");
                    intent.putExtra("operator",1);
                    PendingIntent pendingIntent=PendingIntent.getService(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    bd.setContentIntent(pendingIntent);
                    bd.setProgress(0,0,false);
                    manager.notify(1,bd.build());
                    state=false;
                    break;
            }
        }
    };

    public class myBinder extends Binder{
        public boolean getStop()
        {
            return stop;
        }
        public boolean getQuit()
        {
            return quit;
        }
        public boolean getState(){
            return state;
        }
    }


    //绑定的时候调用
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    //创建的时候
    @Override
    public void onCreate() {
        super.onCreate();
        getNotification();
        dl=new DownloadService();
        //download();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent = new Intent(DownloadService.this, DownloadService.class);
            intent.setAction("com.zxhl.util.DOWNLOADSERVICE");

        } else {
            intent = new Intent();
            intent.setAction("com.zxhl.util.DOWNLOADSERVICE");

        }
    }

    //start调用的时候开启
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getIntExtra("operator",-1)==0)
        {
            download();
        }
        else if(intent.getIntExtra("operator",-1)==1)
        {
            //this.quit=false;
            //exit();
            //manager.cancel(1);
            download();
        }
        else if (intent.getIntExtra("operator",-1)==2)
        {
            /*if(state){
                download();
            }
            else {
                exit();
                this.state=true;
            }*/
            exit();
            this.quit=true;

            bd.setContentText("点击继续下载");
            bd.setContentTitle("暂停下载");
            intent.putExtra("operator",3);
            PendingIntent pendingIntent=PendingIntent.getService(getApplicationContext(),0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            bd.setContentIntent(pendingIntent);
            bd.setProgress(0,0,false);
            manager.notify(1,bd.build());
        }
        else if(intent.getIntExtra("operator",-1)==3){
            this.quit=false;
            //manager.cancel(1);
            download();

        }
        //Toast.makeText(DownloadService.this,"flage:"+flags+",startid:"+startId,Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    //被关闭
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.quit=true;
        exit();
        manager.cancel(1);
    }

    //断开连接
    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    //重新连接
    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }


    //更新软件的相关操作
    private DownLoadTask task;

    //下载
    private void download(){
        task=new DownLoadTask();
        new Thread(task).start();
    }

    //退出
    public void exit(){
        if(task!=null){
            task.exit();
        }
    }

    //安装APP
    private void update(){
        bd.setContentText("点击安装");
        bd.setContentTitle("下载完成");
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(file,"GPSKing.apk")),"application/vnd.android.package-archive");
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,intent,0);
        bd.setContentIntent(pendingIntent);
        bd.setProgress(0,0,false);
        manager.notify(1,bd.build());
    }


    private final class DownLoadTask implements Runnable{
        String path=Constants.APK_PATH;
        FileDownloadered down=null;
        public DownLoadTask(){
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                file=getApplicationContext().getExternalFilesDir("");
            }
            else {
                file=getApplicationContext().getFilesDir();
            }
        }
        @Override
        public void run() {
            try {
                down=new FileDownloadered(getApplicationContext(),path,file,6);
                //设置进度条的最大刻度
                MaxPro=down.getFileSize();
                //pro.setMax(down.getFileSize());
                down.download(new DownloadProgressListener() {
                    @Override
                    public void onDownloadSize(int downloadedSize) {
                        Message msg = new Message();
                        msg.what = 0x001;
                        msg.getData().putInt("size", downloadedSize);
                        handler.sendMessage(msg);
                    }
                });
            }catch (Exception e)
            {
                e.printStackTrace();
                handler.sendEmptyMessage(0x002);
            }
        }

        public void exit(){
            if(down!=null){
                down.exit();
            }
        }
    }

    //定义Notification
    public void getNotification(){
        manager= (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap bt= BitmapFactory.decodeResource(getResources(), R.drawable.gpsking_logo);
        bd = new NotificationCompat.Builder(this)
                .setTicker("开始下载")
                .setContentTitle("正在下载")
                .setContentText("下载")
                .setWhen(System.currentTimeMillis())
                .setProgress(100,0,false)
                .setLargeIcon(bt)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.gpsking_logo);
    }


}