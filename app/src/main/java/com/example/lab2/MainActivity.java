package com.example.lab2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lab2.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
FirebaseStorage Storage;
StorageReference storageReference;
    StorageReference child;
    ImageView imageview;
    ProgressBar progressBar;
    ActivityMainBinding binding;
    ProgressDialog progressDialog;
Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
binding.selectBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
selectImage();




    }


});

    binding.butUpload.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            uploadImage();

        }
    });

    }

    private void uploadImage() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Upload file....");
        progressDialog.show();
SimpleDateFormat formatter=new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
Date now =new Date();
String fileName=formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("image/"+fileName);
        storageReference.putFile(imageUri)
        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
binding.imageView.setImageURI(null);
                Toast.makeText(MainActivity.this,"sucessfully uploaded",Toast.LENGTH_SHORT).show();
           if(progressDialog.isShowing())
               progressDialog.dismiss();


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,"Failer uploaded",Toast.LENGTH_SHORT).show();
if(progressDialog.isShowing())
    progressDialog.dismiss();
 Toast.makeText(MainActivity.this,"Failer uploaded",Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void selectImage() {
        Intent intent=new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && data != null && data.getData() != null){

            imageUri = data.getData();
            binding.imageView.setImageURI(imageUri);

        }
    }
}