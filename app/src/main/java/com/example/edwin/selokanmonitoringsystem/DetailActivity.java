package com.example.edwin.selokanmonitoringsystem;

import android.app.ActivityManager;
import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class DetailActivity extends ActionBarActivity {

    private String nama,alamat,ketinggian,arus,status;
    private int position;

    private TextView textTitle, textLokasi, textKetinggian,textArus, textStatus;
    Handler handler;
    Runnable r,s;
    Intent serviceintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Meng-override title dari Action Bar
        getSupportActionBar().setTitle("Detail Titik Pemantauan");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(47, 79, 79)));


        Bundle bundle = getIntent().getBundleExtra("bundle");

        if(bundle != null) {
//            nama = bundle.getString("nama");
//            alamat = bundle.getString("alamat");
//            ketinggian = bundle.getString("ketinggian");
//            arus = bundle.getString("arus");
//            status = bundle.getString("status");
              position = bundle.getInt("position");
        }

        final CodeAdapter codeAdapter = new CodeAdapter();
        final Chapter chapter = codeAdapter.chapterList.get(position);

        handler = new Handler();

        r = new Runnable() {
            @Override
            public void run() {
                final CodeAdapter codeAdapter = new CodeAdapter();
                Chapter chapter = codeAdapter.chapterList.get(position);

                ketinggian = chapter.chapterKetinggian;
                arus = chapter.chapterArus;
                status = chapter.chapterStatus;

                textKetinggian = (TextView) findViewById(R.id.textKetinggian);
                textKetinggian.setText(textKetinggian.getText().toString().substring(0,12) + " " + ketinggian);

                textArus = (TextView) findViewById(R.id.textArus);
                textArus.setText(textArus.getText().toString().substring(0,10) + " " + arus);

                textStatus = (TextView) findViewById(R.id.textStatus);
                textStatus.setText(textStatus.getText().toString().substring(0,8) + " " + status);
                Log.i("Dijalankan", "onCreate");
                handler.postDelayed(this,1000);
            }
        };
        handler.postDelayed(r,1000);


        nama = chapter.chapterName;
        alamat = chapter.chapterLocation;
        ketinggian = chapter.chapterKetinggian;
        arus = chapter.chapterArus;
        status = chapter.chapterStatus;

        textTitle = (TextView) findViewById(R.id.title1);
        textTitle.setText(textTitle.getText() + " " + nama);

        textLokasi = (TextView) findViewById(R.id.textLokasi);
        textLokasi.setText(textLokasi.getText() + " " + alamat);

        textKetinggian = (TextView) findViewById(R.id.textKetinggian);
        textKetinggian.setText(textKetinggian.getText() + " " + ketinggian);

        textArus = (TextView) findViewById(R.id.textArus);
        textArus.setText(textArus.getText() + " " + arus);

        textStatus = (TextView) findViewById(R.id.textStatus);
        textStatus.setText(textStatus.getText() + " " + status);

        Button toGraphButton = (Button) findViewById(R.id.button);
//        toGraphButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new GraphicFragment(),
//                        GraphicFragment.class.getSimpleName())
//                        .addToBackStack(null)
//                        .commit();
//            }
//        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * On Pause : Menghandle kondisi saat aplikasi tidak dijalankan, tidak akan fetch data
     * Dilemma : Aplikasi harus selalu update untuk memberikan warning
     */
    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(r);
//        s = new Runnable() {
//            @Override
//            public void run() {
//                final CodeAdapter codeAdapter = new CodeAdapter();
//                Chapter chapter = codeAdapter.chapterList.get(position);
//
//                ketinggian = chapter.chapterKetinggian;
//                arus = chapter.chapterArus;
//                status = chapter.chapterStatus;
//
//                Log.i("Dijalankan", "onPause");
//                handler.postDelayed(this,5000);
//            }
//        };
//        handler.postDelayed(s, 5000);
        serviceintent = new Intent(new Intent(this,DetailService.class));
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        serviceintent.putExtra("bundle",bundle);
        startService(serviceintent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        handler.removeCallbacks(s);
        serviceintent = new Intent(new Intent(this,DetailService.class));
        Bundle bundle = new Bundle();
        bundle.putInt("position",position);
        serviceintent.putExtra("bundle",bundle);
        startService(serviceintent);
    }

    /**
     * Method untuk mengetahui apakah suatu kelas service sedang berjalan
     * @param serviceClass is the Service Class Name
     * @return true jika service sedang berjalan
     */
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
