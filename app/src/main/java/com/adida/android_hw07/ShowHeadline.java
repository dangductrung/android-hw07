package com.adida.android_hw07;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ShowHeadline extends AppCompatActivity {

    ArrayList<SingleItem> newsList = new ArrayList<SingleItem>();
    ListView myListView;
    String urlAddress = "", urlCaption = "";
    int type = 0;
    SingleItem selectedNewsItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myListView = (ListView) this.findViewById(R.id.listView);
        TextView title = (TextView) findViewById(R.id.channelName) ;
        Intent callingIntent = getIntent();
        Bundle myBundle = callingIntent.getExtras();
        urlAddress = myBundle.getString("address");
        urlCaption = myBundle.getString("caption");
        type = myBundle.getInt("type");
        String content = "";

        switch (type) {
            case IntKeys.THANH_NIEN: {
                content = "Thanh Nien";
                break;
            }
            case IntKeys.TUOI_TRE: {
                content = "Tuoi Tre";
                break;
            }
            case IntKeys.VNEXPRESS: {
                content = "VnExpress";
                break;
            }
        }
        title.setText(content);




        this.setTitle(content + " – " + urlCaption + " \t" + MainActivity.niceDate());

        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedNewsItem = newsList.get(position);
                showNiceDialogBox(selectedNewsItem, getApplicationContext());
            }
        });
        DownloadRssFeed downloader = new DownloadRssFeed(ShowHeadline.this);
        downloader.execute(urlAddress, urlCaption);
    }

    public void showNiceDialogBox(SingleItem selectedStoryItem, Context context) {
        String title = selectedStoryItem.getTitle();
        String description = selectedStoryItem.getDescription();
        if (title.toLowerCase().equals(description.toLowerCase())) {
            description = "";
        }
        try {
            final Uri storyLink = Uri.parse(selectedStoryItem.getLink());
            AlertDialog.Builder myBuilder = new AlertDialog.Builder(this);
            myBuilder
                    .setTitle(Html.fromHtml(urlCaption))
                    .setMessage(title + "\n\n" + Html.fromHtml(description) + "\n")
                    .setPositiveButton("Close", null)
                    .setNegativeButton("More", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichOne) {
                            Intent browser = new Intent(Intent.ACTION_VIEW, storyLink);
                            startActivity(browser);
                        }
                    }) //setNegativeButton
                    .show();
        } catch (Exception e) {
            Log.e("Error DialogBox", e.getMessage());
        }
    }//showNiceDialogBox
}//ShowHeadlin

