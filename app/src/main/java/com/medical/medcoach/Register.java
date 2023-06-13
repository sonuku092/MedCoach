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

import java.util.Objects;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

//    FirebaseAuth mAuth;
//    TextInputEditText reg_email, reg_password;
//    Button register_btn, reset_btn, logintab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        reg_email=findViewById(R.id.reg_email);
//        reg_password=findViewById(R.id.reg_password);
//
//        logintab=findViewById(R.id.login_Tab);
//
//        register_btn=findViewById(R.id.reg_Btn);
//        reset_btn= findViewById(R.id.reset_Btn);
//
//        mAuth=FirebaseAuth.getInstance();
//
//        // Function for Registration
//
//        register_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = reg_email.getText().toString();
//                String password = reg_password.getText().toString();
//                if(password.isEmpty()) {
//                    if (!validateEmail() | !validatePassword()){
//                        return;
//                    }
//                    String input = "email: " + reg_email.getText().toString();
//                    input +="\n";
//                    input = "Password: " + reg_password.getText().toString();
//
//                    Toast.makeText(Register.this, input, Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                mAuth.createUserWithEmailAndPassword(email, password)
//                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                if (task.isSuccessful()) {
//                                    // Sign in success, update UI with the signed-in user's information
////                                    FirebaseUser user = mAuth.getCurrentUser();
//
//                                    Toast.makeText(Register.this, "Account Created.",
//                                            Toast.LENGTH_SHORT).show();
//
//                                    Intent intent = new Intent(Register.this,Login.class);
////                                    intent.putExtra("email",email);
////                                    intent.putExtra("password",password);
//                                    startActivity(intent);
//
//                                } else {
//                                    // If sign in fails, display a message to the user.
//                                    Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        });
//            }
//        });
//
//        logintab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                GoToLogin();
//            }
//
//            private void GoToLogin() {
//                Intent intent= new Intent(Register.this,Login.class);
//                startActivity(intent);
//                finish();
//            }
//        });


    }
//    private boolean validateEmail(){
//        String email = Objects.requireNonNull(reg_email.getText()).toString().trim();
//
//        if (email.isEmpty()){
//            reg_email.setError("Field can't be Empty");
//            return false;
//        }
//        else {
//            reg_email.setError(null);
//            return true;
//        }
//    }
//
//    private boolean validatePassword(){
//        String password = Objects.requireNonNull(reg_password.getText()).toString().trim();
//
//        if (password.isEmpty()){
//            reg_password.setError("Field can't be Empty");
//            return false;
//        }
//        else {
//            reg_password.setError(null);
//            return true;
//        }
//    }
//
//    Pattern lowerCase= Pattern.compile("^.*[a-z].*$");
//    Pattern upperCase=Pattern.compile("^.*[A-Z].*$");
//    Pattern number = Pattern.compile("^.*[0-9].*$");
//    Pattern special_Chara = Pattern.compile("^.*[^a-zA-Z0-9].*$");
//    private Boolean isValidPassword(String password){
//        if(password.length()<8) {
//            return false;
//        }
//        if(!lowerCase.matcher(password).matches()) {
//            return false;
//        }
//        if(!upperCase.matcher(password).matches()) {
//            return false;
//        }
//        if(!number.matcher(password).matches()) {
//            return false;
//        }
//        if(!special_Chara.matcher(password).matches()) {
//            return false;
//        }
//        return true;
//    }
//
//    public  void confirmInput(View v){
//        if (!validateEmail() | !validatePassword()){
//            return;
//        }
//        String input = "email: " + reg_email.getText().toString();
//        input +="\n";
//        input = "Password: " + reg_password.getText().toString();
//
//        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
//
//    }

}