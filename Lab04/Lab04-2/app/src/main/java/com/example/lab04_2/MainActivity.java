package com.example.lab04_2;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editten, editchieucao, editcannang, editBMI, editchuandoan;
    Button btnBMI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Đảm bảo tên layout đúng với file XML bạn dùng

        // Ánh xạ View
        editten = findViewById(R.id.editten);
        editchieucao = findViewById(R.id.editchieucao);
        editcannang = findViewById(R.id.editcannang);
        editBMI = findViewById(R.id.editBMI);
        editchuandoan = findViewById(R.id.editchuandoan);
        btnBMI = findViewById(R.id.btnBMI);

        btnBMI.setOnClickListener(v -> {
            String strChieuCao = editchieucao.getText().toString().trim();
            String strCanNang = editcannang.getText().toString().trim();

            if (strChieuCao.isEmpty() || strCanNang.isEmpty()) {
                Toast.makeText(MainActivity.this, "Vui lòng nhập chiều cao và cân nặng!", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                float chieuCao = Float.parseFloat(strChieuCao);
                float canNang = Float.parseFloat(strCanNang);

                if (chieuCao <= 0 || canNang <= 0) {
                    Toast.makeText(MainActivity.this, "Chiều cao và cân nặng phải lớn hơn 0", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tính BMI
                float bmi = canNang / (chieuCao * chieuCao);
                String bmiStr = String.format("%.2f", bmi);
                editBMI.setText(bmiStr);

                // Chẩn đoán BMI
                String chuanDoan;
                if (bmi < 18.5) {
                    chuanDoan = "Gầy";
                } else if (bmi < 25) {
                    chuanDoan = "Bình thường";
                } else if (bmi < 30) {
                    chuanDoan = "Thừa cân";
                } else {
                    chuanDoan = "Béo phì";
                }
                editchuandoan.setText(chuanDoan);

            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Giá trị nhập không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
