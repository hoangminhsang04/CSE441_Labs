package com.example.lab18;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String DB_PATH_SUFFIX = "/databases/";
    public static SQLiteDatabase database = null;
    public static String DATABASE_NAME = "arirang.db";

    EditText edttim;
    ListView lv1, lv2, lv3;
    ArrayList<Item> list1, list2, list3;
    myarrayAdapter myarray1, myarray2, myarray3;
    TabHost tab;
    ImageButton btnxoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        processCopy(); // Copy CSDL arirang.sqlite

        // Mở cơ sở dữ liệu đã copy. Lưu vào biến database
        File dbFile = getDatabaseFullPath();
        database = SQLiteDatabase.openDatabase(
                dbFile.getAbsolutePath(),
                null,
                SQLiteDatabase.OPEN_READWRITE
        );
        addControl();  // Thêm các Controls
        addTim();      // Xử lý tìm kiếm
        addEvents();   // Xử lý sự kiện
    }

    // Khai báo và thêm Controls
    private void addControl() {
        btnxoa = findViewById(R.id.btnxoa);
        tab = findViewById(R.id.tabhost);
        tab.setup();

        TabHost.TabSpec tab1 = tab.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("", getResources().getDrawable(R.drawable.search));
        tab.addTab(tab1);

        TabHost.TabSpec tab2 = tab.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("", getResources().getDrawable(R.drawable.list));
        tab.addTab(tab2);

        TabHost.TabSpec tab3 = tab.newTabSpec("t3");
        tab3.setContent(R.id.tab3);
        tab3.setIndicator("", getResources().getDrawable(R.drawable.like));
        tab.addTab(tab3);

        edttim = (EditText) findViewById(R.id.edttim);
        lv1 = (ListView) findViewById(R.id.lv1);
        lv2 = (ListView) findViewById(R.id.lv2);
        lv3 = (ListView) findViewById(R.id.lv3);

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();

        myarray1 = new myarrayAdapter(MainActivity.this, R.layout.listitem, list1);
        myarray2 = new myarrayAdapter(MainActivity.this, R.layout.listitem, list2);
        myarray3 = new myarrayAdapter(MainActivity.this, R.layout.listitem, list3);

        lv1.setAdapter(myarray1);
        lv2.setAdapter(myarray2);
        lv3.setAdapter(myarray3);
    }

    // Xử lý sự kiện
    private void addEvents() {
        tab.setOnTabChangedListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId.equalsIgnoreCase("t2")) {
                    addDanhsach();
                }
                if (tabId.equalsIgnoreCase("t3")) {
                    addYeuthich();
                }
            }
        });

        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edttim.setText("");
            }
        });
    }

    // Thêm bài hát yêu thích
    private void addYeuthich() {
        myarray3.clear();
        Cursor c = database.rawQuery("SELECT * FROM ArirangSongList WHERE YEUTHICH = 1", null);
        if (c.moveToFirst()) {
            do {
                list3.add(new Item(c.getInt(1), c.getString(2), c.getInt(6)));
            } while (c.moveToNext());
        }
        c.close();
        myarray3.notifyDataSetChanged();
    }

    // Thêm danh sách bài hát
    private void addDanhsach() {
        myarray2.clear();
        Cursor c = database.rawQuery("SELECT * FROM ArirangSongList", null);
        if (c.moveToFirst()) {
            do {
                list2.add(new Item(
                        c.getInt(0),        // MASO
                        c.getString(1),     // TENBAIHAT
                        c.getInt(4)         // YEUTHICH
                ));
            } while (c.moveToNext());
        }
        c.close();
        myarray2.notifyDataSetChanged();
    }


    // Xử lý tìm kiếm bài hát theo tiêu đề và mã số
    private void addTim() {
        edttim.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getdata();
            }

            private void getdata() {
                String dulieunhap = edttim.getText().toString();
                myarray1.clear();
                if (!dulieunhap.equals("")) {
                    Cursor c = database.rawQuery(
                            "SELECT * FROM ArirangSongList WHERE TENBH1 LIKE ? OR MABH LIKE ?",
                            new String[]{"%" + dulieunhap + "%", "%" + dulieunhap + "%"});
                    if (c.moveToFirst()) {
                        do {
                            list1.add(new Item(c.getInt(1), c.getString(2), c.getInt(6)));
                        } while (c.moveToNext());
                    }
                    c.close();
                }
                myarray1.notifyDataSetChanged();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không sử dụng
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Không sử dụng
            }
        });
    }

    // Copy CSDL từ assets vào thư mục app
    private void processCopy() {
        File dbFile = getDatabaseFullPath();
        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Copying success from Assets folder", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    // Lấy đường dẫn đầy đủ của database
    private File getDatabaseFullPath() {
        return new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME);
    }

    // Thực hiện copy file database
    public void CopyDataBaseFromAsset() throws IOException {
        InputStream myInput = getAssets().open(DATABASE_NAME);
        String outFileName = getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;

        File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists()) f.mkdirs();

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;

        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        myOutput.flush();
        myOutput.close();
        myInput.close();
    }
}
