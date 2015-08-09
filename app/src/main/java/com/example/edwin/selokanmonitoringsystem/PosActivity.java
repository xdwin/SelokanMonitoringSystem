package com.example.edwin.selokanmonitoringsystem;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class PosActivity extends ActionBarActivity {

    protected ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos);

        //Meng-override deskripsi ActionBar
        getSupportActionBar().setTitle("Titik Pemantauan");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(47, 79, 79)));

        //Instansiasi List View
        listView = (ListView)findViewById(R.id.listView);

        //New Custom Code Adapter
        final CodeAdapter codeAdapter = new CodeAdapter();
        listView.setAdapter(codeAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chapter chapter = codeAdapter.chapterList.get(position);
                String nama = chapter.chapterName;
                String alamat = chapter.chapterLocation;
                String ketinggian = chapter.chapterKetinggian;
                String arus = chapter.chapterArus;
                String status = chapter.chapterStatus;

                Intent intent = new Intent(getApplicationContext(),DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nama", nama);
                bundle.putString("alamat", alamat);
                bundle.putString("ketinggian", ketinggian);
                bundle.putString("arus", arus);
                bundle.putString("status", status);
                intent.putExtra("bundle", bundle);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pos, menu);
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
