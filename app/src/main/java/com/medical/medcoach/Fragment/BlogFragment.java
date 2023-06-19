package com.medical.medcoach.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.medical.medcoach.R;
import com.medical.medcoach.cont_blog;


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
                Intent intent =new Intent(getActivity(), cont_blog.class);
                startActivity(intent);
            }
        });

        return view;
    }
}