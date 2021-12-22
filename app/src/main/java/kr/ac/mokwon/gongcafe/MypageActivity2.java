package kr.ac.mokwon.gongcafe;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MypageActivity extends AppCompatActivity {
    Button b;
    Button b2;
    Button b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);

        b = findViewById(R.id.inquire_btn);
        b2 = findViewById(R.id.notice_btn);
        b3 = findViewById(R.id.leave_btn);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MypageActivity.this, Mypageinquiry.class);
                startActivity(i);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2=new Intent(MypageActivity.this, MypagenoticeActivity.class);
                startActivity(i2);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3=new Intent(MypageActivity.this, MypageleaveActivity.class);
                startActivity(i3);
            }
        });


    }
}

//    public void loginListener(ViewTarget) {
//
//    };
