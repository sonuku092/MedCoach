package com.medical.medcoach.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.medical.medcoach.LoginRegisterActivity;
import com.medical.medcoach.MainActivity;
import com.medical.medcoach.R;
import com.medical.medcoach.getOTPActivity;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


public class LoginTabFragment extends Fragment {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medcoach-b742f-default-rtdb.firebaseio.com/");
    TextInputEditText log_email, log_password;
    Button login_btn;
    FirebaseAuth mAuth;
    int counter=3;


   @Override
   public void onStart() {
       super.onStart();
       // Check if user is signed in (non-null) and update UI accordingly.
       FirebaseUser currentUser = mAuth.getCurrentUser();
       if(currentUser != null){
           Intent intent= new Intent(getActivity(),MainActivity.class);
           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
           startActivity(intent);
//            finish();
       }
   }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_tab, container, false);

        log_email=view.findViewById(R.id.Login_Email);
        log_password=view.findViewById(R.id.Login_Password);

        login_btn=view.findViewById(R.id.Login_Btn);

        mAuth = FirebaseAuth.getInstance();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(log_email.getText());
                password = String.valueOf(log_password.getText());

                if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password))
                {
                    Toast.makeText(getActivity(),"Enter email & Password",Toast.LENGTH_SHORT).show();
                    counter--;
                } else if (email.length()==10) {
                    databaseReference=FirebaseDatabase.getInstance().getReference("Users");
                    databaseReference.child("+91"+email).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if (task.isSuccessful()){
                                if (task.getResult().exists()){
                                    DataSnapshot dataSnapshot = task.getResult();
                                    String Password = String.valueOf(dataSnapshot.child("Password").getValue());
                                    Toast.makeText(getActivity(), Password, Toast.LENGTH_SHORT).show();

                                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                            "+91" + email,
                                            60,
                                            TimeUnit.SECONDS,
                                            getActivity(),
                                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                                @Override
                                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }

                                                @Override
                                                public void onCodeSent(@NonNull String Sendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                                    Intent intent = new Intent(getActivity(), getOTPActivity.class);
                                                    intent.putExtra("phoneTxt",email);
                                                    intent.putExtra("backend",Sendotp);
                                                    startActivity(intent);
                                                }
                                            }
                                    );

                                }else {
                                    Toast.makeText(getActivity(), "User Not Exist", Toast.LENGTH_SHORT).show();
                                }
                                
                            }else {
                                Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                } else {

                    mAuth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Toast.makeText(getActivity(), "Login Successful.",
                                                Toast.LENGTH_SHORT).show();
                                        Intent intent= new Intent(getActivity(),MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
//                                        finish();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(getActivity(), "Login failed.",Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
//                    Toast.makeText(Login.this,"Invalid Credentials",Toast.LENGTH_SHORT).show();
//                    counter--;
                }
                if(counter==0){
                    Toast.makeText(getActivity(),"failed to login attempts",Toast.LENGTH_SHORT).show();
                    login_btn.setEnabled(false);
                }

            }

        });



        return view;

    }

    private boolean validateEmail(){
        String email = Objects.requireNonNull(log_email.getText()).toString().trim();

        if (email.isEmpty()){
            log_email.setError("Field can't be Empty");
            return false;
        }
        else {
            log_email.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){
        String password = Objects.requireNonNull(log_password.getText()).toString().trim();

        if (password.isEmpty()){
            log_password.setError("Field can't be Empty");
            return false;
        }
        else {
            log_password.setError(null);
            return true;
        }
    }

    public  void confirmInput(View v){
        if (!validateEmail() | !validatePassword()){
            return;
        }
        String input = "email: " + log_email.getText().toString();
        input +="\n";
        input = "Password: " + log_password.getText().toString();

        Toast.makeText(getActivity(), "Please Enter", Toast.LENGTH_SHORT).show();

    }
}
