package com.medical.medcoach.Fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.medical.medcoach.LoginRegisterActivity;
import com.medical.medcoach.R;
import com.medical.medcoach.getOTPActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


public class RegisterTabFragment extends Fragment {
    //create Database
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;
    TextInputEditText Name, reg_phone, reg_password , c_password;
    Button register_btn, reset_btn;
    int flag = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_tab, container, false);

        Name=view.findViewById(R.id.Name);
        reg_phone=view.findViewById(R.id.phoneno);
        reg_password=view.findViewById(R.id.regpassword);
        c_password=view.findViewById(R.id.cpassword);

        register_btn=view.findViewById(R.id.Reg_Btn);
        reset_btn=view.findViewById(R.id.Res_Btn);

        mAuth=FirebaseAuth.getInstance();

        register_btn.setOnClickListener(v -> {
            //Get data from the XML design page
            final String fullnameTxt, phoneTxt, passwordTxt, cpasswordTxt;
            fullnameTxt = String.valueOf(Name.getText());
            phoneTxt = String.valueOf(reg_phone.getText());
            passwordTxt = String.valueOf(reg_password.getText());
            cpasswordTxt = String.valueOf(c_password.getText());

            //Check field is empty
            if (fullnameTxt.isEmpty()||phoneTxt.isEmpty()||passwordTxt.isEmpty()||cpasswordTxt.isEmpty()){
                Toast.makeText(getActivity(), "Please fill all Fields.", Toast.LENGTH_SHORT).show();
            } else if (!isValidPassword(passwordTxt)) {
                reg_password.setError("Weak Password");
                reg_password.requestFocus();
            } else if (!passwordTxt.equals(cpasswordTxt)) {
                c_password.setError("Not Match");
                c_password.requestFocus();
            } else {

                if (!isEmail(phoneTxt)){

                    firebaseFirestore.collection("Users")
                        .whereEqualTo("Email",phoneTxt)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                String Password = "";
                                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                    Password = (String) documentSnapshot.get("Password");
                                }
                                if (!passwordTxt.equals(Password)){
                                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                            "+91" + phoneTxt,
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
                                                    intent.putExtra("phoneTxt", phoneTxt);
                                                    intent.putExtra("FullName", fullnameTxt);
                                                    intent.putExtra("Password", passwordTxt);
                                                    intent.putExtra("backend", Sendotp);
                                                    startActivity(intent);
                                                }
                                            });

                                }
                                else {

                                }
                            }
                        }).addOnFailureListener(e -> {

                        });
                    if(!(flag ==1)){

                        flag=0;
                    }
                }else {
                    firebaseFirestore.collection("Users")
                                    .whereEqualTo("Email",phoneTxt)
                                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()){
                                    String userpass = "";
                                    for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                        userpass = (String) documentSnapshot.get("Password").toString();
                                    }
                                    if (!passwordTxt.equals(userpass)){
                                        mAuth.createUserWithEmailAndPassword(phoneTxt, passwordTxt)
                                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            String Uid=FirebaseAuth.getInstance().getUid();
                                                            Map<String, Object> user = new HashMap<>();
                                                            user.put("FullName", fullnameTxt);
                                                            user.put("Password", passwordTxt);
                                                            user.put("Email", phoneTxt);
                                                            user.put("Uid", Uid);
                                                            firebaseFirestore.collection("Users")
                                                                    .add(user)
                                                                    .addOnSuccessListener(documentReference -> {
                                                                        FirebaseAuth.getInstance().signOut();
                                                                        startActivity(new Intent(getContext(), LoginRegisterActivity.class));
                                                                        getActivity().finish();
                                                                    }).addOnFailureListener(e -> {
                                                                    });
                                                        } else {
                                                            // If sign in fails, display a message to the user.
                                                            Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                    }else {
                                        Toast.makeText(getActivity(), "User Already Exist!", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }).addOnFailureListener(e -> {
                            });

                }

            }

        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name.setText("");
                reg_phone.setText("");
                reg_password.setText("");
                c_password.setText("");
                Name.requestFocus();
            }
        });


        return view;
    }

    Pattern lowerCase= Pattern.compile("^.*[a-z].*$");
    Pattern upperCase=Pattern.compile("^.*[A-Z].*$");
    Pattern number = Pattern.compile("^.*[0-9].*$");
    Pattern special_Chara = Pattern.compile("^.*[^a-zA-Z0-9].*$");
    private Boolean isValidPassword(String password){
        if(password.length()<8) {
            return false;
        }
        if(!lowerCase.matcher(password).matches()) {
            return false;
        }
        if(!upperCase.matcher(password).matches()) {
            return false;
        }
        if(!number.matcher(password).matches()) {
            return false;
        }
        if(!special_Chara.matcher(password).matches()) {
            return false;
        }
        return true;
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