package kr.ac.mokwon.gongcafe;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class RegistrationActivity extends AppCompatActivity {

    private static final int GALLERY_CODE = 10;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private FirebaseDatabase database;
    private ImageView imageView;
    private EditText cafeName;
    private EditText address;
    private EditText info;
    private Button cafeRegist;
    private Button pictureAttach;
    private String imagePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();

        imageView = findViewById(R.id.img1);
        cafeName = findViewById(R.id.cafeName_edit);
        address = findViewById(R.id.address_edit);
        info = findViewById(R.id.info_edit) ;
        cafeRegist = findViewById(R.id.cafeRegist_btn);
        pictureAttach = findViewById(R.id.pictureAttach_btn);

        /*권한*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }

        pictureAttach.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
            startActivityForResult(intent, GALLERY_CODE);
        });

        cafeRegist.setOnClickListener(view -> upload(imagePath));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE) {

            imagePath = getPath(data.getData());
            File f = new File(imagePath);
            imageView.setImageURI(Uri.fromFile(f));

        }
    }
    public String getPath(Uri uri){

        String [] proj = {MediaStore.Images.Media.DATA};
        CursorLoader cursorLoader = new CursorLoader(this,uri,proj,null,null,null);

        Cursor cursor = cursorLoader.loadInBackground();
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        return cursor.getString(index);

    }

    private void upload(String uri){
        StorageReference storageRef = storage.getReferenceFromUrl("gs://gong-cafe.appspot.com");


        Uri file = Uri.fromFile(new File(uri));
        StorageReference riversRef = storageRef.child("Cafe/"+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
        }).addOnSuccessListener(taskSnapshot -> {
            @SuppressWarnings("VisibleForTests")
            Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();


            CafeDTO cafeDTO = new CafeDTO();
            cafeDTO.imageUrl = downloadUrl.toString();
            cafeDTO.cafeName = cafeName.getText().toString();
            cafeDTO.address = address.getText().toString();
            cafeDTO.info = info.getText().toString();
            cafeDTO.uid = auth.getCurrentUser().getUid();
            cafeDTO.userId = auth.getCurrentUser().getEmail();

            database.getReference().child("Cafe").push().setValue(cafeDTO);

        });

    }
}