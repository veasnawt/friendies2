package com.example.friendies.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.friendies.MainActivity;
import com.example.friendies.R;
import com.example.friendies.login.LoginActivity;

public class UseSingOutAccount extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_sing_out_account);
        Button btn_signIn = findViewById(R.id.btn_signIn);
        ImageView tv_cancel = findViewById(R.id.btn_cancel_signIn);
        getSupportActionBar().hide();

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UseSingOutAccount.this, LoginActivity.class));
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UseSingOutAccount.this,MainActivity.class);
                intent.putExtra("profileImage", "");
                intent.putExtra("email", "");
                intent.putExtra("name", "");
                intent.putExtra("password", "");
                startActivity(intent);
            }
        });
    }
}
