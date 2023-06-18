package com.medical.medcoach.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.medical.medcoach.R;


public class BlogFragment extends Fragment {

    LinearLayout blog1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_blog, container, false);

        blog1=view.findViewById(R.id.blog);
        blog1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Yess Working", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}