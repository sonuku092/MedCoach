package com.medical.medcoach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Login extends AppCompatActivity {

    TextInputEditText log_email, log_password;
    Button login_btn, register_tab;
    FirebaseAuth mAuth;
    int counter=3;

        @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent= new Intent(Login.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        log_email=findViewById(R.id.login_email);
        log_password=findViewById(R.id.login_password);

        login_btn=findViewById(R.id.Login_btn1);
        register_tab=findViewById(R.id.register_Tab);

        mAuth = FirebaseAuth.getInstance();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(log_email.getText());
                password = String.valueOf(log_password.getText());

                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password))
                {
                    Toast.makeText(Login.this,"Enter email & Password",Toast.LENGTH_SHORT).show();
                    counter--;
                }
                else {
                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(Login.this, "Login Successful.",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent= new Intent(Login.this,MainActivity.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(Login.this, "Login failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
//                    Toast.makeText(Login.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
//                    counter--;
                }
                if(counter==0){
                    Toast.makeText(getBaseContext(),"failed to login attempts",Toast.LENGTH_SHORT).show();
                    login_btn.setEnabled(false);
                }

            }
        });

        register_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToRegister();
            }

            private void GoToRegister() {
                Intent intent= new Intent(Login.this,Register.class);
                startActivity(intent);
                finish();
            }
        });

    }
}