package com.example.edwin.selokanmonitoringsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;


public class Splash extends Activity {

    private final static int SPLASH_TIME_OUT = 3000;
    ShimmerTextView tv;
    ShimmerTextView tvJargon;
    Shimmer shimmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tv = (ShimmerTextView) findViewById(R.id.app_title);
        tvJargon = (ShimmerTextView) findViewById(R.id.jargon);
        toggleAnimation(tv);
        toggleAnimation(tvJargon);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    public void toggleAnimation(View target) {
        if (shimmer != null && shimmer.isAnimating()) {
            shimmer.cancel();
        } else {
            shimmer = new Shimmer();
            shimmer.start(tv);
            shimmer.start(tvJargon);
        }
    }


}
