package com.example.lab24;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends Activity {
    ListView lvTigia;
    TextView txtdate;
    ArrayList<Tygia> dstygia;
    MyArrayAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTigia = findViewById(R.id.lv1);
        txtdate = findViewById(R.id.txtdate);

        getdate();

        dstygia = new ArrayList<>();
        myadapter = new MyArrayAdapter(MainActivity.this, R.layout.layout_listview, dstygia);
        lvTigia.setAdapter(myadapter);

        new TyGiaTask().execute();
    }

    public void getdate() {
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
        txtdate.setText("Hôm Nay: " + simpleDate.format(currentDate));
    }

    class TyGiaTask extends AsyncTask<Void, Void, ArrayList<Tygia>> {
        @Override
        protected ArrayList<Tygia> doInBackground(Void... voids) {
            ArrayList<Tygia> ds = new ArrayList<>();
            try {
                URL url = new URL("https://dongabank.com.vn/exchange/export");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-type", "application/json; charset=utf-8");
                connection.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible)");
                connection.setRequestProperty("Accept", "*/*");

                InputStream is = connection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
                String json = builder.toString();
                json = json.replace("(", "").replace(")", "");

                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONArray("items");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject item = jsonArray.getJSONObject(i);
                    Tygia tyGia = new Tygia();

                    tyGia.setType(item.optString("type", ""));
                    tyGia.setMuatienmat(item.optString("muatienmat", ""));
                    tyGia.setMuack(item.optString("muack", ""));
                    tyGia.setBantienmat(item.optString("bantienmat", ""));
                    tyGia.setBanck(item.optString("banck", ""));

                    String imgUrl = item.optString("imageurl", "");
                    if (!imgUrl.isEmpty()) {
                        URL img = new URL(imgUrl);
                        HttpURLConnection imgConnection = (HttpURLConnection) img.openConnection();
                        imgConnection.setRequestMethod("GET");
                        imgConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (compatible)");
                        imgConnection.setRequestProperty("Accept", "*/*");
                        Bitmap bmp = BitmapFactory.decodeStream(imgConnection.getInputStream());
                        tyGia.setBitmap(bmp);
                    }

                    ds.add(tyGia);
                }
            } catch (Exception e) {
                Log.e("TygiaError", e.toString());
            }
            return ds;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myadapter.clear();
        }

        @Override
        protected void onPostExecute(ArrayList<Tygia> result) {
            super.onPostExecute(result);
            myadapter.clear();
            myadapter.addAll(result);
            myadapter.notifyDataSetChanged();
        }
    }
}
