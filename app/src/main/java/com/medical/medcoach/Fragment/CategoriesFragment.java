package com.medical.medcoach.Fragment;

import android.content.Intent;
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
import com.medical.medcoach.cate_acupuncture;
import com.medical.medcoach.cate_ayurveda;
import com.medical.medcoach.cate_hypnotherapy;
import com.medical.medcoach.cate_meditation;
import com.medical.medcoach.cate_reiki;
import com.medical.medcoach.cate_yoga;

import android.widget.ScrollView;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class CategoriesFragment extends Fragment {

    private ScrollView scrollView1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        RelativeLayout relativeLayout = view.findViewById(R.id.relativeLayout_ayurveda);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), cate_ayurveda.class);
                startActivity(intent);
            }
        });
        RelativeLayout relativeLayout1=view.findViewById(R.id.relativeLayout_meditation);
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), cate_meditation.class);
            }
        });
        RelativeLayout relativeLayout2=view.findViewById(R.id.relativeLayout_acupunture);
        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), cate_acupuncture.class);
            }
        });
        RelativeLayout relativeLayout3=view.findViewById(R.id.relativeLayout_yoga);
        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), cate_yoga.class);
            }
        });
        RelativeLayout relativeLayout4=view.findViewById(R.id.relativeLayout_hypnotherapy);
        relativeLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), cate_hypnotherapy.class);
            }
        });

        RelativeLayout relativeLayout5=view.findViewById(R.id.relativeLayout_reiki);
        relativeLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), cate_reiki.class);
            }
        });
        return view;
    }
}