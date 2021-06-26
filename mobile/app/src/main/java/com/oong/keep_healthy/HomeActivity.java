package com.oong.keep_healthy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private TextView tvUsername, tvName;
    Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        data = getIntent();

        tvName = findViewById(R.id.tv_name);
        tvUsername = findViewById(R.id.tv_username);

        tvName.setText(data.getStringExtra("name"));
        tvUsername.setText(data.getStringExtra("username"));
    }
}