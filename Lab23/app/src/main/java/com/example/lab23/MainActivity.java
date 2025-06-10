package com.example.lab23;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static ListView lv1;
    public static ListView lvi;

    ArrayList<List> mylist;
    MyArrayAdapter myadapter;
    String nodeName;
    String title = "";
    String link = "";
    String description = "";
    String des = "";
    String urlStr = "";
    Bitmap mIcon_val = null;
    String URL = "https://vnexpress.net/rss/tin-moi-nhat.rss";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        lvi = findViewById(R.id.lv); // hoặc R.id.lv nếu đó là ID thực tế bạn dùng trong XML
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv1 = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        myadapter = new MyArrayAdapter(MainActivity.this, R.layout.layout_listview, mylist);
        lv1.setAdapter(myadapter);
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                List item = mylist.get(position);
                if (item.getLink() != null && !item.getLink().isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink()));
                    startActivity(intent);
                }
            }
        });

        LoadExampleTask task = new LoadExampleTask();
        task.execute();
    }

    class LoadExampleTask extends AsyncTask<Void, Void, ArrayList<List>> {
        @Override
        protected ArrayList<List> doInBackground(Void... voids) {
            mylist = new ArrayList<>();
            try {
                XmlPullParserFactory fc = XmlPullParserFactory.newInstance();
                XmlPullParser parser = fc.newPullParser();

                XMLParser myparser = new XMLParser();
                String xml = myparser.getXmlFromUrl(URL);
                parser.setInput(new StringReader(xml));

                int eventType = -1;
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    eventType = parser.next();
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            break;

                        case XmlPullParser.END_DOCUMENT:
                            break;

                        case XmlPullParser.START_TAG:
                            nodeName = parser.getName();

                            if (nodeName.equals("title")) {
                                title = parser.nextText();
                            } else if (nodeName.equals("link")) {
                                link = parser.nextText();
                            } else if (nodeName.equals("description")) {
                                description = parser.nextText();
                                try {
                                    urlStr = description.substring(
                                            description.indexOf("src=") + 5,
                                            description.indexOf("></a") - 2
                                    );
                                } catch (Exception e1) {
                                    e1.printStackTrace();
                                }

                                des = description.substring(description.indexOf("</br>") + 5);

                                try {
                                    URL newurl = new URL(urlStr);
                                    mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                            break;

                        case XmlPullParser.END_TAG:
                            nodeName = parser.getName();
                            if (nodeName.equals("item")) {
                                mylist.add(new List(mIcon_val, title, des, link));
                            }
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mylist;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            myadapter.clear();
        }

        @Override
        protected void onPostExecute(ArrayList<List> result) {
            super.onPostExecute(result);
            myadapter.clear();
            myadapter.addAll(result);
            myadapter.notifyDataSetChanged();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
