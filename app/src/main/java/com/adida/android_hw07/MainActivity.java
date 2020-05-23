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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {

    ListView listView;
    Context context;
    SingleItem selectedNewsItem;
    ArrayAdapter<String> adapter;

    String [][] myUrlCaptionMenu = {
    {"https://vnexpress.net/rss/tin-moi-nhat.rss", "Trang chủ"},
    {"https://vnexpress.net/rss/the-gioi.rss", "Thế giới"},
    {"https://vnexpress.net/rss/thoi-su.rss", "Thời sự"},
    {"https://vnexpress.net/rss/suc-khoe.rss", "Sức khỏe"}
    };


    String[] captions = new String[myUrlCaptionMenu.length];
    String[] urlAddress = new String[myUrlCaptionMenu.length];

    public static String niceDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM d, yyyy",
                Locale.US);
        return sdf.format(new Date()); //Monday Apr 7, 2014
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i=0; i<urlAddress.length; i++) {
            urlAddress[i] = myUrlCaptionMenu[i][0];
            captions[i] = myUrlCaptionMenu[i][1];
        }
        context = getApplicationContext();
        this.setTitle("Vnexpress Headline News\n" + niceDate() );

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedUrl = urlAddress[position], caption = captions[position];
                Intent callShowHeadlines = new Intent(MainActivity.this, ShowHeadline.class);
                Bundle myData = new Bundle();
                myData.putString("address", selectedUrl);
                myData.putString("caption", caption);
                callShowHeadlines.putExtras(myData);
                startActivity(callShowHeadlines);
            }
        });
        Log.v("TEST", String.valueOf(captions.length));
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, captions);
        listView.setAdapter(adapter);
    }


}
