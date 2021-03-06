package com.example.friendies.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.friendies.MainActivity;
import com.example.friendies.R;

public class Profile_view extends AppCompatActivity {
    Button btn_cancel;
    LinearLayout edit_profile, switch_account, setting, data, logout, help;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);

        btn_cancel = findViewById(R.id.btn_cancel);
        edit_profile = findViewById(R.id.linear_edit);
        switch_account = findViewById(R.id.switch_account);
        setting = findViewById(R.id.setting);
        data = findViewById(R.id.data);
        logout = findViewById(R.id.logout);
        help = findViewById(R.id.help);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile_view.this, MainActivity.class));
            }
        });

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
