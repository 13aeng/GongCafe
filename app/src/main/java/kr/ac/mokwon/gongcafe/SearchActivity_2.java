package kr.ac.mokwon.gongcafe;

import static android.graphics.drawable.Drawable.*;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.net.URL;

public class SearchActivity_2 extends AppCompatActivity{
    ImageView mainImageView,ImageView_2,ImageView_3;
    TextView title, description, address2;

    String data1, data2, data3;
    String myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cafeinfo);

        ImageView_3 = findViewById(R.id.cafePicture3_img);
        ImageView_2 = findViewById(R.id.cafePicture2_img);
        mainImageView = findViewById(R.id.cafePicture1_img);
        title = findViewById(R.id.subTitle_cafeInfo);
        description = findViewById(R.id.review1_text);
        address2 = findViewById(R.id.address_text);

        getData();
        setData();
    }

    private void getData(){
        if(getIntent().hasExtra("images") ||
        getIntent().hasExtra("title") || getIntent().hasExtra("description")
                ||getIntent().hasExtra("address2"))
        {
            data1 = getIntent().getStringExtra("title");
            data2 = getIntent().getStringExtra("description");
            data3 = getIntent().getStringExtra("address2");
            myImage = getIntent().getStringExtra("images");
        } else{
            Toast.makeText(this,"No data", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        title.setText(data1);
        description.setText(data2);
        address2.setText(data3);
        Glide.with(this).load(myImage).into(mainImageView);
        Glide.with(this).load(myImage).into(ImageView_2);
        Glide.with(this).load(myImage).into(ImageView_3);
    }

}
