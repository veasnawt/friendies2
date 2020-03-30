package com.example.friendies.profile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.friendies.MainActivity;
import com.example.friendies.R;

import java.io.ByteArrayOutputStream;

public class EditProfile extends AppCompatActivity {

    ImageView edit_profile, profile_img;
    TextView btn_cancel, btn_done;
    EditText ed_email, ed_username, ed_new_password, ed_old_password;

    private static final int PICK_IMAGE = 100;
    private static final int CAMERA_PIC_REQUEST = 1337;

    private final static String URL_EDIT = "http://192.168.0.112:8000/api/user/edit/";

    Uri imgUri;
    int getId;



    private final String URL_EditProfile = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        edit_profile = findViewById(R.id.edit_img);
        profile_img = findViewById(R.id.profile_image);
        btn_cancel = findViewById(R.id.btn_cancel_edit);
        btn_done = findViewById(R.id.btn_done_edit);
        ed_email = findViewById(R.id.edit_email);
        ed_username = findViewById(R.id.edit_username);

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

            }
        });

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
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
            profile_img.setImageURI(imgUri);

            uploadImage(getId,  imgUri.toString());
        }

        if(requestCode==CAMERA_PIC_REQUEST){
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            profile_img.setImageBitmap(photo);
            uploadImage(getId, BitMapToString(photo));
        }



    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private void uploadImage(final int userID, final String photo ){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, URL_EditProfile+getId,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

}
