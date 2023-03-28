package com.example.memestemplateapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class MemeActivity extends AppCompatActivity {

    ImageView backbtn, nextbtn, show_meme, downloadbtn;
    TextView counter;
    int pos;
    List<String> urlList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meme);

        backbtn = findViewById(R.id.backbtn);
        nextbtn = findViewById(R.id.nextbtn);
        show_meme = findViewById(R.id.show_meme);
        downloadbtn = findViewById(R.id.downloadbtn);
        counter = findViewById(R.id.counter);

        pos = getIntent().getIntExtra("pos", 0);
        urlList = getIntent().getStringArrayListExtra("memes");

        counter.setText((pos+1) + "/" + urlList.size());

        Picasso.get().load(urlList.get(pos)).placeholder(R.drawable.error).into(show_meme);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos != 0) {
                    pos--;
                    Picasso.get().load(urlList.get(pos)).placeholder(R.drawable.error).into(show_meme);
                    counter.setText((pos+1) + "/" + urlList.size());
                }
            }
        });

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pos != urlList.size() - 1) {
                    pos++;
                    Picasso.get().load(urlList.get(pos)).placeholder(R.drawable.error).into(show_meme);
                    counter.setText((pos+1)  + "/" + urlList.size());
                }
            }
        });

        downloadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        Toast.makeText(MemeActivity.this, "please Allow Permission", Toast.LENGTH_SHORT).show();
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    } else {
                        Toast.makeText(MemeActivity.this, "Downloading...", Toast.LENGTH_SHORT).show();
                        startdownload();
                    }
                } else {
                    Toast.makeText(MemeActivity.this, "Downloading...", Toast.LENGTH_SHORT).show();
                    startdownload();
                }
            }
        });
    }

    private void startdownload() {
        String url = urlList.get(pos);

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("Download");
        request.setDescription("Downloading...");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "" + System.currentTimeMillis() + ".jpg");

        DownloadManager manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
}