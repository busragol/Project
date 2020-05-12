package com.example.onlinestoreapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class CatTwoDetailsActivity extends AppCompatActivity {
    private ImageView imageView;
    private TextView title;
    private TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_two_details);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView=findViewById(R.id.image_dt);
        title=findViewById(R.id.title_details);
        description=findViewById(R.id.description_details);
        Intent intent=getIntent();
        String mTitle=intent.getStringExtra("title");
        String mDescription=intent.getStringExtra("description");
        final String mImg=intent.getStringExtra("image");
        title.setText(mTitle);
        description.setText(mDescription);

        Picasso.get()
                .load(mImg)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get().load(mImg).into(imageView);

                    }
                });

    }
}
