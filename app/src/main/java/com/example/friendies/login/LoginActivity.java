package com.example.friendies.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.friendies.MainActivity;
import com.example.friendies.R;
import com.example.friendies.register.RegisterActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private TextView tv_Signup, tv_skip;
    private Button btn_login;
    private ProgressBar loading;
    private RequestQueue mQueue;

    private final static String URL_LOGIN="http://192.168.0.112:8000/api/user/show";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        getSupportActionBar().hide();
        editTextPassword=findViewById(R.id.edtPass);
        editTextEmail=findViewById(R.id.edtEmail);
        tv_Signup=findViewById(R.id.tv_signup);
        btn_login=findViewById(R.id.btnLogin);
        tv_skip = findViewById(R.id.tvSkip);
        loading= findViewById(R.id.login_loading);
        mQueue = Volley.newRequestQueue(this);



        tv_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("profileImage", "");
                intent.putExtra("email", "");
                intent.putExtra("name", "");
                intent.putExtra("password", "");
                startActivity(intent);

            }
        });

        tv_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email= editTextEmail.getText().toString();
                String password=editTextPassword.getText().toString();

                if (!email.isEmpty() || !password.isEmpty()){
                    Login(email,password);
                }

                else{
                    Toast.makeText(LoginActivity.this, "Please Fill Information", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void Login(final String Email, final String password) {

        loading.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL_LOGIN, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int tr = 0;
                        try {
                            JSONArray jsonArray = response.getJSONArray("login");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String mEmail = jsonObject.getString("email");
                                String mPassword = jsonObject.getString("password");
                                String mProfileImage = jsonObject.getString("profileImg");
                                String mName = jsonObject.getString("name");
                                int id = jsonObject.getInt("id");
                                if (Email.equals(mEmail) && password.equals(mPassword)){
                                    Toast.makeText(LoginActivity.this,"Login successful", Toast.LENGTH_SHORT).show();
                                   Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                   intent.putExtra("profileImage", mProfileImage);
                                   intent.putExtra("email", mEmail);
                                   intent.putExtra("name", mName);
                                   intent.putExtra("password", mPassword);
                                   intent.putExtra("id", id);
                                   startActivity(intent);
                                   tr=1;
                                    break;
                                }

                            }
                            if (tr == 0){
                                Toast.makeText(LoginActivity.this,"Incorrect email or Password", Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                btn_login.setVisibility(View.VISIBLE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this,"Error"+e.toString(),Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btn_login.setVisibility(View.VISIBLE);

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(LoginActivity.this,"Error"+ error.toString(),Toast.LENGTH_SHORT).show();
                Log.e("error",error.toString());
                loading.setVisibility(View.GONE);
                btn_login.setVisibility(View.VISIBLE);
            }
        });

        mQueue.add(request);
    }



}
