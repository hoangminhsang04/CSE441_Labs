package com.example.lab122;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.app.AlertDialog;
import android.content.DialogInterface;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ArrayList<String> arraywork;
    ArrayAdapter<String> arrAdapater;
    EditText edtwork, edth, edtm;
    TextView txtdate;
    Button btnwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edth = findViewById(R.id.edthour);
        edtm = findViewById(R.id.edtmi);
        edtwork = findViewById(R.id.edtwork);
        btnwork = findViewById(R.id.btnadd);
        lv = findViewById(R.id.listView1);
        txtdate = findViewById(R.id.txtdate);

        // Khởi tạo danh sách rỗng
        arraywork = new ArrayList<>();

        // Gắn dữ liệu vào adapter
        arrAdapater = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arraywork);
        lv.setAdapter(arrAdapater);

        // Lấy ngày hiện tại
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        txtdate.setText("Hôm nay: " + simpleDateFormat.format(currentDate));

        // Bắt sự kiện nút Thêm công việc
        btnwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra nếu thiếu thông tin
                if (edtwork.getText().toString().equals("") ||
                        edth.getText().toString().equals("") ||
                        edtm.getText().toString().equals("")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Thiếu thông tin");
                    builder.setMessage("Vui lòng nhập đầy đủ công việc, giờ và phút.");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Không làm gì thêm
                        }
                    });
                    builder.show();
                } else {
                    // Thêm dữ liệu vào danh sách
                    String str = edtwork.getText().toString() + " - " +
                            edth.getText().toString() + ":" + edtm.getText().toString();

                    arraywork.add(str);
                    arrAdapater.notifyDataSetChanged();

                    // Xóa nội dung nhập
                    edtwork.setText("");
                    edth.setText("");
                    edtm.setText("");
                }
            }
        });
    }
}
