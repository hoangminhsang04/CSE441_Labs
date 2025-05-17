package com.example.lab072;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {

    TextView edtAA, edtBB;
    Button btnSendTong, btnSendHieu;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        edtAA = findViewById(R.id.edtAA);
        edtBB = findViewById(R.id.edtBB);
        btnSendTong = findViewById(R.id.btnsendtong);
        btnSendHieu = findViewById(R.id.btnsendhieu);

        intent = getIntent();
        int a = intent.getIntExtra("soa", 0);
        int b = intent.getIntExtra("sob", 0);

        edtAA.setText(String.valueOf(a));
        edtBB.setText(String.valueOf(b));

        btnSendTong.setOnClickListener(view -> {
            int sum = a + b;
            intent.putExtra("kq", sum);
            setResult(33, intent);
            finish();
        });

        btnSendHieu.setOnClickListener(view -> {
            int sub = a - b;
            intent.putExtra("kq", sub);
            setResult(34, intent);
            finish();
        });
    }
}
