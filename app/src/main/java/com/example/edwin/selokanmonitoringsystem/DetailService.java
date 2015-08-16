package com.example.edwin.selokanmonitoringsystem;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Edwin on 8/10/2015.
 */
public class DetailService extends Service {

    private String nama,alamat,ketinggian,arus,status,batasKetinggian;
    private String lastNama, lastAlamat, lastKetinggian, lastArus, lastStatus, lastBatasKetinggian = "No Internet";
    private int position;
    ConnectChecker connectChecker = new ConnectChecker();
    Handler handler;
    Runnable r;

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (connectChecker.isConnected(getApplicationContext())){
            Bundle bundle = new Bundle();
            try {
                bundle = intent.getBundleExtra("bundle");
            }
            catch (NullPointerException n){
                Log.i("Kena null pointer",n.getMessage());
                return START_STICKY;
            }
            position = bundle.getInt("position");

            handler = new Handler();
            r = new Runnable() {
                @Override
                public void run() {
                    final CodeAdapter codeAdapter = new CodeAdapter();
                    Chapter chapter = codeAdapter.chapterList.get(position);

                    ketinggian = chapter.chapterKetinggian;
                    arus = chapter.chapterArus;
                    status = chapter.chapterStatus;
                    batasKetinggian = chapter.batasKetinggian;
                    if(Integer.parseInt(ketinggian) < Integer.parseInt(batasKetinggian)
                            && Integer.parseInt(arus) < 3){
                        Notify("Peringatan","Air mendekati batas permukaan \n Selokan tidak mengalir");
                    }
                    else if (Integer.parseInt(ketinggian) < Integer.parseInt(batasKetinggian)){
                        Notify("Peringatan","Air mendekati batas permukaan");
                    }
                    else if (Integer.parseInt(arus) < 3){
                        Notify("Peringatan","Selokan tidak mengalir");
                    }
                    Log.i("Dijalankan", "Service");
                    handler.postDelayed(this,20000);

                }
            };
            handler.postDelayed(r, 20000);
        }
        else {
                Notify("Peringatan","No Internet Connection");
            Log.i("Dijalankan", "Service No Internet");
        }
        return START_STICKY;
    }

    private void Notify(String notificationTitle, String notificationMessage){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new Notification(R.drawable.ic_lup,"Peringatan",System.currentTimeMillis());
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,new Intent(this,PosActivity.class),0);
        notification.setLatestEventInfo(this, notificationTitle, notificationMessage, pendingIntent);
        notificationManager.notify(1337, notification);
    }
}