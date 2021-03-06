package com.example.edwin.selokanmonitoringsystem;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    de.hdodenhof.circleimageview.CircleImageView circle1;
    ConnectChecker connectChecker = new ConnectChecker();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circle1 = (de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.image1);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.rgb(47, 79, 79)));

            circle1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (connectChecker.isConnected(getApplicationContext())) {
                        if (circle1.getBorderWidth() == 2) {
                            circle1.setBorderWidth(6);
                        }
                    }
                    return false;
                }
            });
            circle1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (connectChecker.isConnected(getApplicationContext())) {
                        Intent intent = new Intent(getApplicationContext(), PosActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Turn On Your Internet", Toast.LENGTH_LONG).show();
                    }
                }
            });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (circle1.getBorderWidth()!=2)
            circle1.setBorderWidth(2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
