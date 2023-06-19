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

        RelativeLayout relativeLayout = view.findViewById(R.id.relativeLayout_ayurveda);
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Ayurveda", Toast.LENGTH_SHORT).show();
            }
        });
        RelativeLayout relativeLayout1=view.findViewById(R.id.relativeLayout_meditation);
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Meditation", Toast.LENGTH_SHORT).show();
            }
        });
        RelativeLayout relativeLayout2=view.findViewById(R.id.relativeLayout_acupunture);
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Meditation", Toast.LENGTH_SHORT).show();
            }
        });
        RelativeLayout relativeLayout3=view.findViewById(R.id.relativeLayout_yoga);
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Meditation", Toast.LENGTH_SHORT).show();
            }
        });
        RelativeLayout relativeLayout4=view.findViewById(R.id.relativeLayout_hypnotherapy);
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Meditation", Toast.LENGTH_SHORT).show();
            }
        });

        RelativeLayout relativeLayout5=view.findViewById(R.id.relativeLayout_reiki);
        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Meditation", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}