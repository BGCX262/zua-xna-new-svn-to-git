package com.m2team.xna.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.TextView;

import com.m2team.xna.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

public class AboutActivity extends Activity {
    private static final String OPEN_SOURCE_NOTICE = "NOTICE" ;
    private GestureDetector mLongPressDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        final TextView txt_license = (TextView) findViewById(R.id.txt_license);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(0xff3e5385);
        }

        try {
            PackageInfo i = getPackageManager().getPackageInfo(getPackageName(), 0);
            TextView tv = (TextView) findViewById(R.id.version);
            String currentVersion = getString(R.string.current_version);
            String version = String.format(currentVersion, i.versionName);
            if (tv != null) {
                tv.setText(version);
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        View iconView = findViewById(R.id.logo_img);
        iconView.setLongClickable(true);
        iconView.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mLongPressDetector != null && mLongPressDetector.onTouchEvent(event);
            }
        });

        ActionBar actionbar = getActionBar();
        if( actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setDisplayShowHomeEnabled(false);
            actionbar.setHomeButtonEnabled(true);
            actionbar.setTitle(R.string.about);
        }

        new Thread(new Runnable() {

            @Override
            public void run() {
                InputStream is = null;
                try {
                    is = getResources().getAssets().open(OPEN_SOURCE_NOTICE);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if (is == null)
                    return;
                try {
                    final String text = convertStreamToString(is);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            txt_license.setText(text);
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private String convertStreamToString(InputStream is)
            throws IOException {
        Writer writer = new StringWriter();

        char[] buffer = new char[2048];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is,
                    "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }
        String text = writer.toString();
        return text;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
