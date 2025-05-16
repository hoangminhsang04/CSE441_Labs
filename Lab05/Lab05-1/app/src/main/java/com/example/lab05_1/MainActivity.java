package com.example.lab05_1;

import android.media.DrmInitData;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnDoi = (Button) findViewById(R.id.button1);
        btnDoi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0){
                EditText editNamDuongLich = findViewById(R.id.editNamDuongLich);
                TextView txtNamAmLich = findViewById(R.id.textView3);

                try {
                    int namDuong = Integer.parseInt(editNamDuongLich.getText().toString().trim());

                    String[] CAN = {"Canh", "Tân", "Nhâm", "Quý", "Giáp", "Ất", "Bính", "Đinh", "Mậu", "Kỷ"};
                    String[] CHI = {"Thân", "Dậu", "Tuất", "Hợi", "Tý", "Sửu", "Dần", "Mão", "Thìn", "Tỵ", "Ngọ", "Mùi"};

                    String can = CAN[namDuong % 10];
                    String chi = CHI[namDuong % 12];
                    String namAm = can + " " + chi;

                    txtNamAmLich.setText(namAm);
                } catch (NumberFormatException e) {
                    txtNamAmLich.setText("Vui lòng nhập đúng năm!");
                }
            }
        });
    }
}
