package com.example.edwin.selokanmonitoringsystem;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.ArrayList;
import java.util.EventListener;

/**
 * Created by Edwin on 8/13/2015.
 */
public class ConnectChecker {


    protected boolean isConnected(Context context){
        ArrayList<String> result = new ArrayList<>();
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo wifiNetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobileNetwork = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiNetwork != null && wifiNetwork.isConnected()){
                result.add("Wifi");
            }
            if (mobileNetwork != null && mobileNetwork.isConnected()){
                result.add("mobile");
            }
        }
        catch (Exception e){
            Log.e("Koneksi Error",e.getMessage());
        }

        if (result.size() > 0){
            Log.i("Isi Array", result.get(0));
            return true;
        }
        else {
            return false;
        }
    }
}
