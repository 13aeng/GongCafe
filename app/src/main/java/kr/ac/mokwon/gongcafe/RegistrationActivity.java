package kr.ac.mokwon.gongcafe;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

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

//        /*권한*/
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
//        }

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

            Uri uri = null;
            if(data != null){
                uri = data.getData();
            }
            if(uri != null){
                imageView.setImageURI(uri);
                imagePath = createCopyAndReturnRealPath(this,uri);

                new AlertDialog.Builder(this).setMessage(uri.toString()+"\n"+imagePath).create().show();
            }
        }
    }

    @Nullable
    public static String createCopyAndReturnRealPath(@NonNull Context context, @NonNull Uri uri) {
        final ContentResolver contentResolver = context.getContentResolver();

        if (contentResolver == null)
            return null;

        // 파일 경로를 만듬
        String filePath = context.getApplicationInfo().dataDir + File.separator
                + System.currentTimeMillis();
        File file = new File(filePath);
        try {
            // 매개변수로 받은 uri 를 통해  이미지에 필요한 데이터를 불러 들인다.

            InputStream inputStream = contentResolver.openInputStream(uri);
            if (inputStream == null)
                return null;
            // 이미지 데이터를 다시 내보내면서 file 객체에  만들었던 경로를 이용한다.

            OutputStream outputStream = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = inputStream.read(buf)) > 0)
                outputStream.write(buf, 0, len);
            outputStream.close();

            inputStream.close();

        } catch (IOException ignore) {
            return null;
        }

        return file.getAbsolutePath();
    }

    private void upload(String uri){
        StorageReference storageRef = storage.getReferenceFromUrl("gs://gong-cafe.appspot.com");


        Uri file = Uri.fromFile(new File(uri));
        StorageReference riversRef = storageRef.child("Cafe/"+file.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(file);

        uploadTask.addOnFailureListener(exception -> {
            // Handle unsuccessful uploads
        }).addOnSuccessListener(taskSnapshot -> {
            Uri downloadUrl = taskSnapshot.getUploadSessionUri();


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