package com.example.mychatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    Button login;
    EditText email;
    EditText pass;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login=(Button) findViewById(R.id.login);
        email=(EditText) findViewById(R.id.loginemail);
        pass=(EditText) findViewById(R.id.loginpassword);
        firebaseAuth=FirebaseAuth.getInstance();
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void userLogin() {
        String userEmail=email.getText().toString();
        String userPass=pass.getText().toString();

        if (userEmail.isEmpty()){
            Tools.showMessage("E mail boş bırakılamaz.");
        }
        if (userPass.isEmpty() || userPass.length()<6){
            Tools.showMessage("Geçersiz şifre");
        }
        firebaseAuth.signInWithEmailAndPassword(userEmail,userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    progressBar.setVisibility(View.INVISIBLE);
                    Tools.showMessage("Hoşgeldiniz.");
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Tools.showMessage("Giriş başarısız.");
                }
            }
        });
    }


    public void signupClick(View view) {
        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }
}