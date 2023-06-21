package com.medical.medcoach.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.medical.medcoach.LoginRegisterActivity;
import com.medical.medcoach.R;
import com.medical.medcoach.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    FragmentProfileBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    TextView UserName, UserEmail;

    @Override
    public void onStart() {
        super.onStart();
        String UID;
        UID=auth.getCurrentUser().getUid();
        firebaseFirestore.collection("Users")
                .whereEqualTo("Uid",UID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        String UserName = "";
                        String UserEmail = "";
                        for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                            UserName = (String) documentSnapshot.get("FullName");
                            UserEmail = (String) documentSnapshot.get("Email");
                        }
                        binding.profileName1.setText(UserName);
                        binding.useremail1.setText(UserEmail);
                    }
                }).addOnFailureListener(e -> {

                });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        UserName=view.findViewById(R.id.profile_name1);
        UserEmail=view.findViewById(R.id.useremail1);

        binding = FragmentProfileBinding.inflate(inflater,container,false);

        binding.logoutBtn.setOnClickListener(v->{

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getContext(),LoginRegisterActivity.class));
            getActivity().finish();
        });

     return binding.getRoot();
    }

}