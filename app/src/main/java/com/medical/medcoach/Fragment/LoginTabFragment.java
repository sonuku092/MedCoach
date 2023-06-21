package com.medical.medcoach.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.medical.medcoach.MainActivity;
import com.medical.medcoach.R;
import com.medical.medcoach.getOTPActivity;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


public class LoginTabFragment extends Fragment {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    TextInputEditText log_email, log_password;
    Button login_btn;
    FirebaseAuth mAuth;
    int counter=3;

    @Override
    public void onStart() {
        // Check if user is signed in (non-null) and update UI accordingly.
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_tab, container, false);

        log_email=view.findViewById(R.id.Login_Email);
        log_password=view.findViewById(R.id.Login_Password);

        login_btn=view.findViewById(R.id.Login_Btn);

        mAuth = FirebaseAuth.getInstance();

        login_btn.setOnClickListener(v -> {
            String email, password;
            email = String.valueOf(log_email.getText());
            password = String.valueOf(log_password.getText());

            if(TextUtils.isEmpty(email) && TextUtils.isEmpty(password))
            {
                Toast.makeText(getActivity(),"Enter email & Password",Toast.LENGTH_SHORT).show();
                counter--;
            } else if (!isEmail(email)) {
                firebaseFirestore.collection("Users")
                        .whereEqualTo("Email",email)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    String pass = "";
                                    for (QueryDocumentSnapshot queryDocumentSnapshot: task.getResult()){
                                        pass = (String) queryDocumentSnapshot.get("Password");
                                    }
                                    if (password.equals(pass)) {
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
                                                        intent.putExtra("phoneTxt", email);
                                                        intent.putExtra("backend", Sendotp);
                                                        startActivity(intent);
                                                    }
                                                });
                                    }else {
                                        log_password.setError("Password Incorrect");
                                    }
                                }
                            }
                        });

            } else {

                firebaseFirestore.collection("Users")
                        .whereEqualTo("Email",email)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                String userpass = "";
                                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                    userpass = (String) documentSnapshot.get("Password");
                                }
                                if (password.equals(userpass)){
                                    mAuth.signInWithEmailAndPassword(email, password)
                                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        // Sign in success, update UI with the signed-in user's information
                                                        Intent intent= new Intent(getActivity(),MainActivity.class);
                                                        intent.putExtra("Email",email);
                                                        intent.putExtra("Password",password);
                                                        startActivity(intent);
                                                        getActivity().finish();

                                                    } else {
                                                        // If sign in fails, display a message to the user.
                                                        Toast.makeText(getActivity(), "Login failed.",Toast.LENGTH_SHORT).show();

                                                    }
                                                }
                                            });
                                }
                                else {
                                    log_password.setError("Password Incorrect");
                                }
                            }
                        }).addOnFailureListener(e -> {

                        });

                counter--;
            }
            if(counter==0){
                Toast.makeText(getActivity(),"failed to login attempts",Toast.LENGTH_SHORT).show();
                login_btn.setEnabled(false);
            }

        });

        return view;
    }

    private  Boolean isEmail(String Email){
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(Email).matches()){
            return false;
        }
        return true;
    }
}
