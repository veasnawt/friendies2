package com.example.friendies.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.friendies.login.LoginActivity;
import com.example.friendies.MainActivity;
import com.example.friendies.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUserName,editTextPassword,editTextEmail,editTextConfirmPassword;
    private Button btn_register;
    TextView tv_skip, tv_login;

    private static String URL_REGISTER="http://192.168.0.112:8000/api/user/create";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        getSupportActionBar().hide();
        editTextUserName=findViewById(R.id.edtUsername);
        editTextConfirmPassword=findViewById(R.id.edtConfirmPass);
        editTextEmail=findViewById(R.id.edtEmail);
        editTextPassword=findViewById(R.id.edtPass);
        btn_register=findViewById(R.id.btnSignup);
        tv_skip = findViewById(R.id.skip);
        tv_login = findViewById(R.id.txtLogin);

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });

        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }
        });

    }


    private void Register(){
        final String name = this.editTextUserName.getText().toString().trim();
        final String password = this.editTextPassword.getText().toString().trim();
        final String email = this.editTextEmail.getText().toString().trim();
        final String c_password = this.editTextConfirmPassword.getText().toString().trim();

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(getApplication(), "Please Fill Information", Toast.LENGTH_LONG).show();
        }else{
            if (password.equals(c_password)){
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGISTER,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String success = jsonObject.getString("success");
                                    if (success.equals("1")) {
                                        Toast.makeText(RegisterActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                    Toast.makeText(RegisterActivity.this, "Register Error!" + e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(RegisterActivity.this, "Register Error!" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<>();
                        params.put("name", name);
                        params.put("email", email);
                        params.put("password", password);
                        return params;

                    }
                };

                editTextUserName.setText("");
                editTextEmail.setText("");
                editTextPassword.setText("");
                editTextConfirmPassword.setText("");

                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }
            else {
                Toast.makeText(getApplication(), "Confirm password is incorrect", Toast.LENGTH_LONG).show();
            }
        }

    }


}