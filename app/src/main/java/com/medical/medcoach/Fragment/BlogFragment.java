package com.medical.medcoach.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.medical.medcoach.Adapter.Adapter;
import com.medical.medcoach.Adapter.Model;
import com.medical.medcoach.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class BlogFragment extends Fragment {
    RecyclerView recyclerView;
    Button SaveBtn, cancel;
    EditText Titles,Contents,AuthorName;
    ImageView UploadImg,cancleX;
    TextView Date;
    String currentDate, CurrentUserID;
    FloatingActionButton floatingActionButton;
    SearchView searchView;
    Calendar calendar = Calendar.getInstance();
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    Uri ImageUrl;
    ArrayList<Model> list;
    Adapter adapter;
    Model model;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_blog, container, false);

        recyclerView=view.findViewById(R.id.recyclerv);
        list= new ArrayList<>();

        SetupRV();

        searchView=view.findViewById(R.id.search1);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });


        floatingActionButton=view.findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            final Dialog dialog = new Dialog(getContext());
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.addblogs);
                Titles = dialog.findViewById(R.id.title);
                Contents = dialog.findViewById(R.id.contents);
                AuthorName = dialog.findViewById(R.id.AuthorName);
                Date = dialog.findViewById(R.id.Date);
                SaveBtn = dialog.findViewById(R.id.Save_Btn);
                cancel = dialog.findViewById(R.id.CancelBtn);
                UploadImg = dialog.findViewById(R.id.uploadimg);
                cancleX = dialog.findViewById(R.id.cancel);
                currentDate = DateFormat.getDateInstance().format(calendar.getTime());
                Date.setText(currentDate);
                CurrentUserID=FirebaseAuth.getInstance().getCurrentUser().getUid();
                firebaseFirestore.collection("Users")
                        .whereEqualTo("Uid",CurrentUserID)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){
                                String UserName = "";
                                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                                    UserName = (String) documentSnapshot.get("FullName");
                                }
                                AuthorName.setText(UserName);
                            }
                        }).addOnFailureListener(e -> {

                        });

                UploadImg.setOnClickListener(view12 -> selectIMG());

                SaveBtn.setOnClickListener(view1 -> {
                    if (!Titles.getText().toString().isEmpty()||!Contents.getText().toString().isEmpty()) {
                        ProgressDialog pd = new ProgressDialog(getContext());
                        pd.setTitle("Uploading....");
                        pd.setMessage("Please Wait for a while..");
                        pd.setCancelable(false);
                        pd.show();

                        if (ImageUrl != null)
                        {
                            FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                            StorageReference storageReference = firebaseStorage.getReference().child("Blog Images/"+ImageUrl.toString()+".jpg");
                            storageReference.putFile(ImageUrl)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            storageReference.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Uri> task) {
                                                    String img_Url = task.getResult().toString();

                                                    String title = Titles.getText().toString();
                                                    String contents = Contents.getText().toString();
                                                    String author = AuthorName.getText().toString();
                                                    String date = Date.getText().toString();
                                                    int ShereCount=0;

                                                    Map<String, Object> Blog = new HashMap<>();
                                                    Blog.put("Title", title);
                                                    Blog.put("Contents", contents);
                                                    Blog.put("Date", date);
                                                    Blog.put("Author", author);
                                                    Blog.put("ImgUrl", img_Url);
                                                    Blog.put("ShareCount", ShereCount);
                                                    firebaseFirestore.collection("Blogs")
                                                            .add(Blog)
                                                            .addOnSuccessListener(documentReference -> {
                                                                pd.dismiss();
                                                                dialog.dismiss();
                                                                if(getActivity()!=null){
                                                                    getActivity().recreate();
                                                                }
                                                            }).addOnFailureListener(e -> {
                                                            });
                                                }
                                            });
                                        }
                                    })
                                    .addOnProgressListener(snapshot -> {

                                    });
                        }


                    }else {
                        Titles.setError("Should Not Empty");
                        Titles.requestFocus();
                    }
                });

                cancel.setOnClickListener(view1 -> {
                    dialog.dismiss();
                });
                cancleX.setOnClickListener(view1 -> {
                    dialog.dismiss();
                });
                dialog.show();
            }

        });
        return view;
    }

    private void filter(String s) {
        ArrayList<Model> filtered_list = new ArrayList<>();
        for (Model item:list){
            if (item.getTitle().toLowerCase().contains(s)){
                filtered_list.add(item);
            }
        }
        if (filtered_list.isEmpty()){
            //
        }
        else {
            adapter.filter_list(filtered_list);
        }
    }


    private void SetupRV() {
        firebaseFirestore.collection("Blogs").addSnapshotListener((value, error) -> {
            list.clear();
            for (DocumentSnapshot snapshot:value.getDocuments()){
                model = snapshot.toObject(Model.class);
                model.setId(snapshot.getId());
                list.add(model);
            }
            adapter.notifyDataSetChanged();
        });
        adapter = new Adapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
    }

    private void selectIMG() {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Your Image"),101);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==101 && resultCode == RESULT_OK && data!=null && data.getData()!=null)
        {
            ImageUrl=data.getData();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}