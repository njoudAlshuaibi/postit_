package com.postit.postit_;

import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatActivity;

public class PopUpWindowAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupwindowsadmin);

        DisplayMetrics ma = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(ma);

        int width = ma.widthPixels;
        int height = ma.heightPixels;
        getWindow().setLayout((int)(width*.8),(int)(height*.5));
    }
}
