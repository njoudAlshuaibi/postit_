package com.postit.postit_;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

public class creatnotepopup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createnotepopup);

        DisplayMetrics ja = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ja);

        int width = ja.widthPixels;
        int height = ja.heightPixels;
        getWindow().setLayout((int) (width * .9), (int) (height * .9));
    }
}
