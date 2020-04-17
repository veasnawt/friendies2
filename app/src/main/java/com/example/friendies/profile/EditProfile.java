package com.example.friendies.profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
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
import com.example.friendies.MainActivity;
import com.example.friendies.R;
import com.example.friendies.login.LoginActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import kotlin.UByteArray;

public class EditProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    ImageView edit_profile, profile_img;
    TextView btn_cancel, btn_done;
    EditText ed_email, ed_username, tv_test;
    LinearLayout btn_change_password;
    Spinner spinner;


    private String newEmail = MainActivity.EMAIL;
    private String newUsername = MainActivity.NAME;

    private String newPassword = MainActivity.PASSWORD;

    private String imgUrlToString = MainActivity.URL_IMAGE;

    private Bitmap bitmap;

    private static final int PICK_IMAGE = 100;
    private static final int CAMERA_PIC_REQUEST = 1337;
    private static final int CHANGE_PASSWORD = 1001;

    private final static String URL_EDIT = "http://192.168.43.56:8000/api/user/edit/";
    private final static String URL_LOGIN = "http://192.168.43.56:8000/api/user/show/";

    Uri imgUri;
    int getId;


    boolean x = true;



    private final String URL_EditProfile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        profile_img = findViewById(R.id.profile_image);
        btn_cancel = findViewById(R.id.btn_cancel_edit);
        btn_done = findViewById(R.id.btn_done_edit);
        ed_email = findViewById(R.id.edit_email);
        ed_username = findViewById(R.id.edit_username);
        btn_change_password = findViewById(R.id.btn_change_password);
        ed_email.setText(newEmail);
        tv_test= findViewById(R.id.test);

        spinner = findViewById(R.id.spinner_camera);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.camera, R.layout.support_simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        ed_username.setText(newUsername);


        if (!MainActivity.URL_IMAGE.equals("")){
            Picasso.get().load(MainActivity.URL_IMAGE).into(profile_img);
        }

        btn_change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditProfile.this, ChangePassword.class);
                startActivityForResult(intent, CHANGE_PASSWORD);
            }
        });

//        Intent intent = getIntent();
//        newPassword = intent.getStringExtra("newPassword");

        getId = MainActivity.ID;

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
                newEmail = ed_email.getText().toString().trim();
                newUsername = ed_username.getText().toString().trim();
                editProfile(getId, imgUrlToString, newEmail, newUsername, newPassword);


                    upData();
                    startActivity(new Intent(EditProfile.this,Profile_view.class));
                 
            }
        });


    }

    public void openCam(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, CAMERA_PIC_REQUEST);
    }



    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==PICK_IMAGE){
            imgUri = data.getData();

//            imgUrlToString = imgUri.toString();
//            Toast.makeText(EditProfile.this, imgUrlToString, Toast.LENGTH_LONG).show();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);
                profile_img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

            imgUrlToString = BitMapToString(bitmap);


        }
        else if(requestCode ==CAMERA_PIC_REQUEST){
            bitmap = (Bitmap) data.getExtras().get("data");
            profile_img.setImageBitmap(bitmap);
            imgUrlToString = BitMapToString(bitmap);
        }
        else if (requestCode == CHANGE_PASSWORD){
            newPassword = data.getStringExtra("NewPassword");
        }

    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte[] b=baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private void editProfile(final int userID, final String photo, final String mEmail, final String mName, final String mPassword ){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();


        StringRequest request = new StringRequest(Request.Method.POST, URL_EDIT+userID,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                x = false;
                Toast.makeText(EditProfile.this, "Try again" + error, Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", mName);
                params.put("email", mEmail);
                params.put("password", mPassword);
                params.put("profileImg", photo);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void upData(){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL_LOGIN, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("dataById");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    String Email = jsonObject.getString("email");
                    String Password = jsonObject.getString("password");
                    String ProfileImg = jsonObject.getString("profileImg");
                    String Name = jsonObject.getString("name");

                    MainActivity.EMAIL = Email;
                    MainActivity.PASSWORD = Password;
                    MainActivity.URL_IMAGE = ProfileImg;
                    MainActivity.NAME = Name;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

    public boolean test(boolean x){
        return x;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        ((TextView)view).setText(null);
        if (text.equals("Take Photo")) openCam();
        else if (text.equals("Choose Photo")) openGallery();



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
