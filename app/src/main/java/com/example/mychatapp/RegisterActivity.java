package com.example.mychatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.mychatapp.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    Button signup;
    EditText email,pass;
    FirebaseAuth firebaseAuth;
    ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signup=(Button) findViewById(R.id.signup);
        email=(EditText) findViewById(R.id.loginemail);
        pass=(EditText) findViewById(R.id.loginpassword);
        firebaseAuth=FirebaseAuth.getInstance();
        progressBar2=(ProgressBar) findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.INVISIBLE);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupClick();
                progressBar2.setVisibility(View.VISIBLE);
            }
        });
    }

    private void signupClick() {
        String userEmail=email.getText().toString();
        String userPass=pass.getText().toString();
        if (userEmail.isEmpty()){
           Tools.showMessage("E mail boş olamaz");
        }
        if(userPass.isEmpty() || userPass.length()<6){
            Tools.showMessage("Şifreniz geçersizdir.");
        }
        firebaseAuth.createUserWithEmailAndPassword(userEmail,userPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    UserModel user=new UserModel(userEmail,userPass);
                    progressBar2.setVisibility(View.INVISIBLE);
                    Tools.showMessage("Kayıt Başarılı.");
                    String uid=task.getResult().getUser().getUid();
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("users").child(uid);
                    myRef.setValue(user);

                    startActivity(new Intent(RegisterActivity.this,SplashActivity.class));
                }else{
                    Tools.showMessage("Kayıt Başarısız.");
                }
            }
        });
    }


    public void loginClick(View view) {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }
}