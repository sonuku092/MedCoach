package com.medical.medcoach;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;
import com.medical.medcoach.databinding.ActivityBlogDetailBinding;

public class BlogDetail extends AppCompatActivity {
    ActivityBlogDetailBinding binding;
    TextView author,content,title,date;
    String id;
    String shareTitle, shareContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityBlogDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        author=findViewById(R.id.author11);
        content=findViewById(R.id.content11);
        title=findViewById(R.id.title11);
        date=findViewById(R.id.date11);
        showdata();
    }

    private void showdata() {
        id= getIntent().getStringExtra("id");
        FirebaseFirestore.getInstance().collection("Blogs").document(id).addSnapshotListener((value, error) -> {
            assert value != null;
            Glide.with(getApplicationContext()).load(value.getString("ImgUrl")).into(binding.imageView11);

            author.setText(Html.fromHtml("<font color ='#B7B7B7'>By </font> <font color='#800000'>"+value.getString("Author")));
            content.setText(value.getString("Contents"));
            title.setText(value.getString("Title"));
            date.setText(value.getString("Date"));
            shareTitle=value.getString("Title");
            shareContent=value.getString("Contents");
        });


        binding.floatingActionButton2.setOnClickListener(view -> {
            Intent intent =new Intent(Intent.ACTION_SEND);
            String shareBody= shareContent;
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_SUBJECT,shareTitle);
            intent.putExtra(Intent.EXTRA_TEXT,shareBody);
            startActivity(Intent.createChooser(intent,"Share Using"));
        });
        binding.imageView2.setOnClickListener(view -> onBackPressed());
    }
}