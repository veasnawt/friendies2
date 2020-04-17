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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.friendies.login.LoginActivity;
import com.example.friendies.MainActivity;
import com.example.friendies.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUserName,editTextPassword,editTextEmail,editTextConfirmPassword;
    private Button btn_register;
    TextView tv_skip, tv_login;
    private RequestQueue requestQueue;
    private boolean testEmail=true;

//    private static String URL_REGISTER="http://192.168.0.112:8000/api/user/create";
//    private static String URL_LOGIN="http://192.168.0.112:8000/api/user/show";
private static String URL_REGISTER="http://192.168.43.56:8000/api/user/create";
    private static String URL_LOGIN="http://192.168.43.56:8000/api/user/show";


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
        requestQueue = Volley.newRequestQueue(this);

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
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
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                intent.putExtra("profileImage", "");
                intent.putExtra("email", "");
                intent.putExtra("name", "");
                intent.putExtra("password", "");
                startActivity(intent);
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
            if (TestEmailIsExist(email)){
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
                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                                            intent.putExtra("profileImage", "");
//                                            intent.putExtra("email", email);
//                                            intent.putExtra("name", name);
//                                            intent.putExtra("password", password);
                                            startActivity(intent);
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
                    Toast.makeText(RegisterActivity.this, "Confirm password is incorrect", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(RegisterActivity.this, "Sorry This email is tooken"+TestEmailIsExist(email), Toast.LENGTH_SHORT).show();
            }

        }

    }

    private boolean TestEmailIsExist(final String email){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_LOGIN, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        boolean c = true;
                        try {
                            JSONArray jsonArray = response.getJSONArray("login");
                            for (int i=0; i<jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if (email.equals(jsonObject.getString("email"))){
                                    x(false);

                                    c=false;
                                    break;
                                }
                            }
                            if (c){
                                x(true);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonObjectRequest);

      return testEmail;
    }

    private void x(boolean d){

        testEmail = d;
    }


}