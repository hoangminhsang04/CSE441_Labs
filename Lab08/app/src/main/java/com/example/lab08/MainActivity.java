package com.example.lab08;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.activity.EdgeToEdge;

public class MainActivity extends AppCompatActivity {

    Button btnCall, btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khai báo, ánh xạ button theo id từ layout
        btnCall = findViewById(R.id.btncallphone);
        btnSend = findViewById(R.id.btnsendsms);

        // Xử lý sự kiện click cho btnCall
        btnCall.setOnClickListener(v -> {
            Intent intent1 = new Intent(MainActivity.this, CallPhoneActivity.class);
            startActivity(intent1);
        });

        // Xử lý sự kiện click cho btnSend
        btnSend.setOnClickListener(v -> {
            Intent intent2 = new Intent(MainActivity.this, SendSMSActivity.class);
            startActivity(intent2);
        });
    }
}
