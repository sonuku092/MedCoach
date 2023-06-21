package com.medical.medcoach;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class getOTPActivity extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    EditText inputNo1,inputNo2,inputNo3,inputNo4,inputNo5,inputNo6;
    TextView showNo;
    ProgressBar progressBar;

    String getotp, fullnameTxt, passwordTxt, Number;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medcoach-b742f-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_otpactivity);

        Button submit_btn = findViewById(R.id.Submit_btn);

        inputNo1=findViewById(R.id.inputotp1);
        inputNo2=findViewById(R.id.inputotp2);
        inputNo3=findViewById(R.id.inputotp3);
        inputNo4=findViewById(R.id.inputotp4);
        inputNo5=findViewById(R.id.inputotp5);
        inputNo6=findViewById(R.id.inputotp6);

        progressBar=findViewById(R.id.pregress_bar);

        showNo=findViewById(R.id.showNumber);
        showNo.setText(String.format(
                "+91-%s",getIntent().getStringExtra("phoneTxt")
        ));

        getotp = getIntent().getStringExtra("backend");
        fullnameTxt = getIntent().getStringExtra("FullName");
        passwordTxt = getIntent().getStringExtra("Password");
        Number = "+91"+ getIntent().getStringExtra("phoneTxt");
        Number = "+91"+ getIntent().getStringExtra("phoneTxt");

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number1, number2, number3, number4, number5,number6;
                number1=inputNo1.getText().toString().trim();
                number2=inputNo2.getText().toString().trim();
                number3=inputNo3.getText().toString().trim();
                number4=inputNo4.getText().toString().trim();
                number5=inputNo5.getText().toString().trim();
                number6=inputNo6.getText().toString().trim();
                if (!number1.isEmpty() && !number2.isEmpty() && !number3.isEmpty() && !number4.isEmpty() && !number5.isEmpty() && !number6.isEmpty()){
                    String entecodeotp = number1+number2+number3+number4+number5+number6;
                    if (getotp!=null){
                        progressBar.setVisibility(View.VISIBLE);
                        submit_btn.setVisibility(View.INVISIBLE);
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getotp,entecodeotp);
                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        progressBar.setVisibility(View.GONE);
                                        submit_btn.setVisibility(View.VISIBLE);

                                        if (task.isSuccessful()){
                                            String Uid = FirebaseAuth.getInstance().getUid();
                                            Map<String, Object> user = new HashMap<>();
                                            user.put("FullName", fullnameTxt);
                                            user.put("Password", passwordTxt);
                                            user.put("Email", getIntent().getStringExtra("phoneTxt"));
                                            user.put("Uid", Uid);
                                            firebaseFirestore.collection("Users")
                                                    .add(user)
                                                    .addOnSuccessListener(documentReference -> {
                                                        FirebaseAuth.getInstance().signOut();
                                                        Intent intent=new Intent(getOTPActivity.this,MainActivity.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        startActivity(intent);
                                                        finish();
                                                    });

                                        }
                                        else {
                                            Toast.makeText(getOTPActivity.this, "Login Error", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });

                    } else {
                        Toast.makeText(getOTPActivity.this, "Please check Internet Connection", Toast.LENGTH_SHORT).show();
                    }
//                    Toast.makeText(getOTPActivity.this, "OTP verify", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getOTPActivity.this, "Please Enter all No", Toast.LENGTH_SHORT).show();
                }
            }
        });
        numberotpmove();

        findViewById(R.id.resendotp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + Number,
                        60,
                        TimeUnit.SECONDS,
                        getOTPActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                Toast.makeText(getOTPActivity.this, "Error", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(getOTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newSendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                getotp=newSendotp;
//                                Toast.makeText(getOTPActivity.this, "OTP send Successfully ", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });
    }

    private void numberotpmove() {
        inputNo1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inputNo2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputNo2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inputNo3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputNo3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inputNo4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputNo4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inputNo5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        inputNo5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()){
                    inputNo6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}