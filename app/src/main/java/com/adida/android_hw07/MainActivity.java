package com.adida.android_hw07;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {

    ListView listView;
    Context context;
    SingleItem selectedNewsItem;
    ArrayAdapter<String> adapter;

    String[][] vnexpress = {
            {"https://vnexpress.net/rss/tin-moi-nhat.rss", "Trang chủ"},
            {"https://vnexpress.net/rss/the-gioi.rss", "Thế giới"},
            {"https://vnexpress.net/rss/thoi-su.rss", "Thời sự"},
            {"https://vnexpress.net/rss/suc-khoe.rss", "Sức khỏe"}
    };

    String[][] thanhnien = {
            {"https://thanhnien.vn/rss/viet-nam/phap-luat.rss", "Pháp Luật"},
            {"https://thanhnien.vn/rss/chinh-tri-xa-hoi/phong-su.rss", "Phóng Sự / Điều Tra"},
            {"https://thanhnien.vn/rss/thoi-su/quoc-phong.rss", "Quốc Phòng"},
            {"https://thanhnien.vn/rss/doi-song/dan-sinh.rss", "Dân Sinh"},
            {"https://thanhnien.vn/rss/doi-song/quyen-duoc-biet.rss", "Quyền Được Biết"},
    };

    String[][] tuoitre = {
            {"https://tuoitre.vn/rss/tin-moi-nhat.rss", "Trang chủ"},
            {"https://tuoitre.vn/rss/the-gioi.rss", "Thế giới"},
            {"https://tuoitre.vn/rss/giai-tri.rss", "Giải trí"},
            {"https://tuoitre.vn/rss/suc-khoe.rss", "Sức khỏe"}
    };

    String[] captions ;
    String[] urlAddress ;
    int key = -1;

    public static String niceDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM d, yyyy",
                Locale.US);
        return sdf.format(new Date()); //Monday Apr 7, 2014
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView title = (TextView) findViewById(R.id.channelName);

        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            key = intent.getInt("key");
        }

        switch (key) {
            case IntKeys.THANH_NIEN: {
                captions = new String[thanhnien.length];
                urlAddress = new String[thanhnien.length];
                for (int i = 0; i < urlAddress.length; i++) {
                    urlAddress[i] = thanhnien[i][0];
                    captions[i] = thanhnien[i][1];
                }
                this.setTitle("Thanh Nien Headline News\n" + niceDate());
                title.setText("Thanh Nien");
                break;
            }
            case IntKeys.TUOI_TRE: {
                captions = new String[tuoitre.length];
                urlAddress = new String[tuoitre.length];
                for (int i = 0; i < urlAddress.length; i++) {
                    urlAddress[i] = tuoitre[i][0];
                    captions[i] = tuoitre[i][1];
                }
                this.setTitle("Tuoi Tre Headline News\n" + niceDate());
                title.setText("Tuoi Tre");
                break;
            }
            case IntKeys.VNEXPRESS: {
                captions = new String[vnexpress.length];
                urlAddress = new String[vnexpress.length];
                for (int i = 0; i < urlAddress.length; i++) {
                    urlAddress[i] = vnexpress[i][0];
                    captions[i] = vnexpress[i][1];
                }
                this.setTitle("VnExpress Headline News\n" + niceDate());
                title.setText("VnExpress");
                break;
            }
        }

        context = getApplicationContext();

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedUrl = urlAddress[position], caption = captions[position];
                Intent callShowHeadlines = new Intent(MainActivity.this, ShowHeadline.class);
                Bundle myData = new Bundle();
                myData.putString("address", selectedUrl);
                myData.putString("caption", caption);
                myData.putInt("type", key);

                callShowHeadlines.putExtras(myData);
                startActivity(callShowHeadlines);
            }
        });
        Log.v("TEST", String.valueOf(captions.length));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, captions);
        listView.setAdapter(adapter);
    }


}
