package com.medical.medcoach;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.medical.medcoach.Adapter.AdapterViewPager;
import com.medical.medcoach.Fragment.LoginTabFragment;
import com.medical.medcoach.Fragment.RegisterTabFragment;

import java.util.ArrayList;

public class LoginRegisterActivity extends AppCompatActivity {

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

}