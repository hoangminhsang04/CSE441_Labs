package com.example.lab09;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ImageButton btnplay, btnstop;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnplay = findViewById(R.id.imagestart); // trùng với XML
        btnstop = findViewById(R.id.imagestop);

        btnplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyService.class);
                startService(intent);

                if (flag) {
                    btnplay.setImageResource(R.drawable.stop); // bạn cần có ảnh pause.png
                    flag = false;
                } else {
                    btnplay.setImageResource(R.drawable.play);
                    flag = true;
                }
            }
        });

        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyService.class);
                stopService(intent);
                btnplay.setImageResource(R.drawable.play);
                flag = true;
            }
        });
    }
}
