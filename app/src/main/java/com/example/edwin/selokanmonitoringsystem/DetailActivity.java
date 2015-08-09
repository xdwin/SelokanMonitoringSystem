package com.example.edwin.selokanmonitoringsystem;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailActivity extends ActionBarActivity {

    private String nama,alamat,ketinggian,arus,status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Meng-override title dari Action Bar
        getSupportActionBar().setTitle("Detail Titik Pemantauan");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(47, 79, 79)));


        Bundle bundle = getIntent().getBundleExtra("bundle");
        
        if(bundle != null) {
            nama = bundle.getString("nama");
            alamat = bundle.getString("alamat");
            ketinggian = bundle.getString("ketinggian");
            arus = bundle.getString("arus");
            status = bundle.getString("status");
        }
        TextView textTitle = (TextView) findViewById(R.id.title1);
        textTitle.setText(textTitle.getText() + " " + nama);

        TextView textLokasi = (TextView) findViewById(R.id.textLokasi);
        textLokasi.setText(textLokasi.getText() + " " + alamat);

        TextView textKetinggian = (TextView) findViewById(R.id.textKetinggian);
        textKetinggian.setText(textKetinggian.getText() + " " + ketinggian);

        TextView textArus = (TextView) findViewById(R.id.textArus);
        textArus.setText(textArus.getText() + " " + arus);

        TextView textStatus = (TextView) findViewById(R.id.textStatus);
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
}
