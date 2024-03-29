package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

public class FullImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        Intent i = getIntent();

        int position = i.getExtras().getInt("id");
        ImageAdapter adapter = new ImageAdapter(this);
        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        //ImageView imageView = (ImageView) findViewById(R.id.imageView);

        imageView.setImageDrawable(getResources().getDrawable(adapter.images[position]));

        //ImageView.setImageResource(adapter.images[position]);

    }
}
