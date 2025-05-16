package com.example.lab05_2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    Button btnTieptuc, btnGiai, btnThoat;
    EditText edita, editb, editc;
    TextView txtkq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnTieptuc = findViewById(R.id.btntieptuc);
        btnGiai = findViewById(R.id.btngiaipt);
        btnThoat = findViewById(R.id.btnthoat);
        edita = findViewById(R.id.edita);
        editb = findViewById(R.id.editb);
        editc = findViewById(R.id.editc);
        txtkq = findViewById(R.id.txtkq);

        btnGiai.setOnClickListener(v -> {
            String sa = edita.getText().toString();
            String sb = editb.getText().toString();
            String sc = editc.getText().toString();

            try {
                int a = Integer.parseInt(sa);
                int b = Integer.parseInt(sb);
                int c = Integer.parseInt(sc);
                String kq = "";
                DecimalFormat dcf = new DecimalFormat("0.00");

                if (a == 0) {
                    if (b == 0) {
                        kq = (c == 0) ? "PT vô số nghiệm" : "PT vô nghiệm";
                    } else {
                        kq = "PT có 1 nghiệm: x = " + dcf.format(-1.0 * c / b);
                    }
                } else {
                    double delta = b * b - 4 * a * c;
                    if (delta < 0) {
                        kq = "PT vô nghiệm";
                    } else if (delta == 0) {
                        kq = "PT có nghiệm kép x1 = x2 = " + dcf.format(-1.0 * b / (2 * a));
                    } else {
                        double x1 = (-b + Math.sqrt(delta)) / (2 * a);
                        double x2 = (-b - Math.sqrt(delta)) / (2 * a);
                        kq = "PT có 2 nghiệm:\nx1 = " + dcf.format(x1) + "\nx2 = " + dcf.format(x2);
                    }
                }
                txtkq.setText(kq);
            } catch (NumberFormatException e) {
                txtkq.setText("Vui lòng nhập đủ 3 hệ số a, b, c!");
            }
        });

        btnTieptuc.setOnClickListener(v -> {
            edita.setText("");
            editb.setText("");
            editc.setText("");
            txtkq.setText("");
            edita.requestFocus();
        });

        btnThoat.setOnClickListener(v -> finish());
    }
}
