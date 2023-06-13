package com.medical.medcoach;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.medical.medcoach.Adapter.AdapterViewPager;
import com.medical.medcoach.Fragment.LoginTabFragment;
import com.medical.medcoach.Fragment.RegisterTabFragment;

import java.util.ArrayList;
import java.util.Objects;

public class LoginRegisterActivity extends AppCompatActivity {

    //create database
//    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    ViewPager2 viewPager2;
    ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    BottomNavigationView bottomNavigationView;
    CardView cardView;

    LottieAnimationView animationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);


        animationView = findViewById(R.id.therapy);

        cardView = findViewById(R.id.cardview);

        viewPager2 = findViewById(R.id.login_viewpager);

        bottomNavigationView=findViewById(R.id.LoginNav);


        fragmentArrayList.add(new LoginTabFragment());
        fragmentArrayList.add(new RegisterTabFragment());

        AdapterViewPager adapterViewPager =new AdapterViewPager(this,fragmentArrayList);
        //set adapter
        viewPager2.setAdapter(adapterViewPager);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position)
                {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.login);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.register);
                        break;
                }
                super.onPageSelected(position);
            }
        });


        bottomNavigationView.setOnItemSelectedListener(item -> {

            int id = item.getItemId();
            if (id==R.id.login) {
                viewPager2.setCurrentItem(0);
            }
            else if (id==R.id.register) {
                viewPager2.setCurrentItem(1);
            }
            return true;
        });



    }

//    private boolean validateEmail(){
//        String email = Objects.requireNonNull(log_email.getText()).toString().trim();
//
//        if (email.isEmpty()){
//            log_email.setError("Field can't be Empty");
//            return false;
//        }
//        else {
//            log_email.setError(null);
//            return true;
//        }
//    }
//
//    private boolean validatePassword(){
//        String password = Objects.requireNonNull(log_password.getText()).toString().trim();
//
//        if (password.isEmpty()){
//            log_password.setError("Field can't be Empty");
//            return false;
//        }
//        else {
//            log_password.setError(null);
//            return true;
//        }
//    }
//
//    public  void confirmInput(View v){
//        if (!validateEmail() | !validatePassword()){
//            return;
//        }
//        String input = "email: " + log_email.getText().toString();
//        input +="\n";
//        input = "Password: " + log_password.getText().toString();
//
////        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
////        Toast.makeText(LoginRegisterActivity.this, "Please long press the key", Toast.LENGTH_LONG ).show();
//
//        Toast.makeText(this, "Your Text Here!", Toast.LENGTH_SHORT).show();
//
//    }

}