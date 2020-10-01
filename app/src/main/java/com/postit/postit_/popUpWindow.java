package com.postit.postit_;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;

public class popUpWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up_window);

        DisplayMetrics ma = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ma);

        int width = ma.widthPixels;
        int height = ma.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.5));
    }
}