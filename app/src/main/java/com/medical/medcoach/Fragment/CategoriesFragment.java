package com.medical.medcoach.Fragment;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ImageView;


import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.medical.medcoach.R;
import android.widget.ScrollView;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class CategoriesFragment extends Fragment {

    private ScrollView scrollView1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        scrollView1 = view.findViewById(R.id.scrollView);
        RelativeLayout relativeLayout = view.findViewById(R.id.relativeLayout_ayurveda);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Ayurveda", Toast.LENGTH_SHORT).show();
            }
        });

        return inflater.inflate(R.layout.fragment_categories, container, false);
    }
}