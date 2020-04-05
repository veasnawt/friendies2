package com.example.friendies.profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.friendies.MainActivity;
import com.example.friendies.R;

public class ChangePassword extends AppCompatActivity {

    TextView btn_cancel;
    EditText ed_oldPassword, ed_newPassword, ed_confirmPassword;
    Button btn_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getSupportActionBar().hide();

        btn_cancel = findViewById(R.id.change_password_btn_cancel);
        ed_confirmPassword = findViewById(R.id.change_password_confirm_password);
        ed_newPassword = findViewById(R.id.change_password_new_password);
        ed_oldPassword  = findViewById(R.id.change_password_edit_old_password);
        btn_confirm = findViewById(R.id.change_password_btn_confirm);



        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("NewPassword", MainActivity.PASSWORD);
                setResult(1001, intent);
                finish();

            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (testNCPassword(ed_newPassword.getText().toString(), ed_confirmPassword.getText().toString()) && testOldPassword(ed_oldPassword.getText().toString())){
                    Toast.makeText(ChangePassword.this, "Change successfully", Toast.LENGTH_SHORT).show();

                    String newPassowrd = ed_newPassword.getText().toString();
                    Intent intent = new Intent();
                    intent.putExtra("NewPassword", newPassowrd);
                    setResult(1001, intent);
                    finish();
                }
                else if (!testOldPassword(ed_oldPassword.getText().toString())){
                    Toast.makeText(ChangePassword.this, "Your old password is incorrect!", Toast.LENGTH_SHORT).show();
                }
                else if (!testNCPassword(ed_newPassword.getText().toString(), ed_confirmPassword.getText().toString())){
                    Toast.makeText(ChangePassword.this, "Confirm Password is incorrect!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private boolean testOldPassword(String oldPassword){

        boolean test = false;

        if (oldPassword.equals(MainActivity.PASSWORD)){
            test = true;
        }

        return test;
    }

    private boolean testNCPassword(String newPassword, String confirmPassword){

        boolean test = false;
        if (newPassword.equals(confirmPassword)){
            test = true;
        }
        return test;
    }

}
