package com.mllweb.xcache;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mllweb.cache.XCache;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XCache xCache=XCache.getInstance(this);
        xCache.put("string","String");
    }
}
