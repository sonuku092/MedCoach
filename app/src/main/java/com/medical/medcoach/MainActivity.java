package com.medical.medcoach;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.medical.medcoach.Fragment.BlogFragment;
import com.medical.medcoach.Fragment.CategoriesFragment;
import com.medical.medcoach.Fragment.HomeFragment;
import com.medical.medcoach.Fragment.ProfileFragment;
import com.medical.medcoach.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        replaceFragment(new HomeFragment());

        binding.bottomNav.setOnItemSelectedListener(item -> {

            int id = item.getItemId();
                if (id==R.id.home1) {
                    replaceFragment(new HomeFragment());
                }
                else if (id==R.id.cate1) {
                    replaceFragment(new CategoriesFragment());
                }
                else if (id==R.id.blog1) {
                    replaceFragment(new BlogFragment());
                }
                else if (id==R.id.profile1)
                    replaceFragment(new ProfileFragment());


            return true;
        });


    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }
}
