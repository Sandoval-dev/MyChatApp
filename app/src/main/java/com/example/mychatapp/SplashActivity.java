package com.example.mychatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {
    ProgressBar progressBar3;
    FirebaseAuth firebaseAuth;
    Thread wait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Tools.context=getApplicationContext();
        setContentView(R.layout.activity_splash);
        progressBar3=(ProgressBar) findViewById(R.id.progressBar3);
        progressBar3.setVisibility(View.INVISIBLE);
        firebaseAuth=FirebaseAuth.getInstance();
        SplashThread();
        //firebaseAuth.signOut();

        if (firebaseAuth.getCurrentUser() != null){
            progressBar3.setVisibility(View.VISIBLE);
            Tools.showMessage("Bir kullanıcı tanımlı, ana sayfaya yönlendiriliyorsunuz.");
            wait.start();

        }else{
            progressBar3.setVisibility(View.INVISIBLE);
            Tools.showMessage("Giriş yapın ya da üye olun!");
        }
    }


    public void loginClick(View view) {
        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
    }

    public void signupClick(View view) {
        startActivity(new Intent(SplashActivity.this,RegisterActivity.class));

    }

    public void SplashThread(){
         wait=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                    startActivity(new Intent(SplashActivity.this,MainActivity.class));
                    progressBar3.setVisibility(View.INVISIBLE);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

    }
}