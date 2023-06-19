package com.medical.medcoach;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class cont_blog extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://medcoach-b742f-default-rtdb.firebaseio.com/");
    AppCompatButton btn1;
    TextView title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_cont_blog);
        String TitleTxt = "Meditation", ContentTxt = "Meditation has proven difficult to define as it covers a wide range of dissimilar practices in different traditions. In popular usage, the word \"meditation\" and the phrase \"meditative practice\" are often used imprecisely to designate practices found across many cultures.";
        String id = "1235";

        title=findViewById(R.id.title);
        content=findViewById(R.id.content);

        btn1=findViewById(R.id.update);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                databaseReference.child("Blogs").addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        //Check if phone is not register
//                        if (snapshot.hasChild(id)){
//                            Toast.makeText(cont_blog.this, " No is already registered", Toast.LENGTH_SHORT).show();
//                        } else {
//                            databaseReference.child("Blogs").child(id).child("Title").setValue(TitleTxt);
//                            databaseReference.child("Blogs").child(id).child("Content").setValue(ContentTxt);
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
                databaseReference.child("Blogs").child("1234").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()){
                            if (task.getResult().exists()){
                                DataSnapshot dataSnapshot = task.getResult();
                                String titletxt = String.valueOf(dataSnapshot.child("Title").getValue());
                                title.setText(titletxt);
                                String contenttxt = String.valueOf(dataSnapshot.child("Content").getValue());
                                content.setText(contenttxt);

                            }else {
                                Toast.makeText(cont_blog.this, "User Not Exist", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(cont_blog.this, "Failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });

    }
}