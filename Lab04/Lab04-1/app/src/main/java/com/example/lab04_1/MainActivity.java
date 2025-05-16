package com.example.lab04_1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    EditText txtFar, txtCel;
    Button btnFar, btnCel, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ
        txtFar = findViewById(R.id.txtFar);
        txtCel = findViewById(R.id.txtCel);
        btnFar = findViewById(R.id.btnFar);
        btnCel = findViewById(R.id.btnCel);
        btnClear = findViewById(R.id.btnClear);

        // Fahrenheit -> Celsius
        btnFar.setOnClickListener(v -> {
            String doF = txtFar.getText().toString();
            if (!doF.isEmpty()) {
                double f = Double.parseDouble(doF);
                double c = (f - 32) * 5 / 9;
                txtCel.setText(String.format("%.2f", c));
            }
        });

        // Celsius -> Fahrenheit
        btnCel.setOnClickListener(v -> {
            String doC = txtCel.getText().toString();
            if (!doC.isEmpty()) {
                double c = Double.parseDouble(doC);
                double f = (c * 9 / 5) + 32;
                txtFar.setText(String.format("%.2f", f));
            }
        });

        // Xóa cả hai
        btnClear.setOnClickListener(v -> {
            txtFar.setText("");
            txtCel.setText("");
            txtFar.requestFocus();
        });
    }
}