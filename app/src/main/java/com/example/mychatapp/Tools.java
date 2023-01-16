package com.example.mychatapp;

import android.content.Context;
import android.widget.Toast;

public class Tools {
    public static Context context;
    public static void showMessage(String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
}
