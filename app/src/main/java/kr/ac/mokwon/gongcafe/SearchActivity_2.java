package kr.ac.mokwon.gongcafe;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SearchActivity_2 extends AppCompatActivity {

    ImageView mainImageView;
    TextView title, description;

    String data1, data2, myImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cafeinfo);

        mainImageView = findViewById(R.id.cafePicture1_img);
        title = findViewById(R.id.subTitle_cafeInfo);
        description = findViewById(R.id.review1_text);

        getData();
        setData();
    }

    private void getData(){
        if(getIntent().hasExtra("images") ||
        getIntent().hasExtra("title") || getIntent().hasExtra("description"))
        {
            data1 = getIntent().getStringExtra("title");
            data2 = getIntent().getStringExtra("description");
            myImage = getIntent().getStringExtra("images");

        } else{
            Toast.makeText(this,"No data", Toast.LENGTH_SHORT).show();
        }


    }

    private void setData() {
        title.setText(data1);
        description.setText(data2);
        //mainImageView.setImageURI(myImage);
    }
}
