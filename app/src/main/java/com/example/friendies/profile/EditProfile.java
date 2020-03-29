package com.example.friendies.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.friendies.R;

public class EditProfile extends AppCompatActivity {

    ImageView edit_profile;
    TextView btn_cancel, btn_done;
    EditText ed_email, ed_username, ed_new_password, ed_old_password;

    private final String URL_EditProfile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        edit_profile = findViewById(R.id.edit_img);
        btn_cancel = findViewById(R.id.btn_cancel_edit);
        btn_done = findViewById(R.id.btn_done_edit);
        ed_email = findViewById(R.id.edit_email);
        ed_username = findViewById(R.id.edit_username);

        getSupportActionBar().hide();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditProfile.this, Profile_view.class));
            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }
}
