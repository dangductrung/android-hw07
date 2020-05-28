package com.adida.android_hw07;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class NewsApp extends AppCompatActivity {
    GridView gridView;
    String[] data = {
      "Thanh Niên",
      "VnExpress",
            "Tuổi trẻ",
    };
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_app);

        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                Bundle b = new Bundle();
                b.putInt("key",position);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
}
