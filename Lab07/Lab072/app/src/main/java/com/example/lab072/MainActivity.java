package com.example.lab072;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText edtA, edtB, edtKQ;
    Button btnRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ các view
        edtA = findViewById(R.id.edtA);
        edtB = findViewById(R.id.edtB);
        edtKQ = findViewById(R.id.edtkq);
        btnRequest = findViewById(R.id.btnrequest);

        // Gán sự kiện cho nút
        btnRequest.setOnClickListener(view -> {
            int a = Integer.parseInt(edtA.getText().toString());
            int b = Integer.parseInt(edtB.getText().toString());

            Intent intent = new Intent(MainActivity.this, SubActivity.class);
            intent.putExtra("soa", a);
            intent.putExtra("sob", b);
            startActivityForResult(intent, 99);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && data != null) {
            int result = data.getIntExtra("kq", 0);
            if (resultCode == 33) {
                edtKQ.setText("Tổng 2 số là: " + result);
            } else if (resultCode == 34) {
                edtKQ.setText("Hiệu 2 số là: " + result);
            }
        }
    }
}
