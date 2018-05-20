package com.dyslexsee.dyslexsee;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnLearn , btnRevise , btnRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        btnLearn = (Button) findViewById(R.id.btnLearn);
        btnRevise = (Button) findViewById(R.id.btnRevise);
        btnRecord = (Button) findViewById(R.id.btnRecord);

        //btnLearn = (ImageView) findViewById(R.id.btnLearn);
        //btnRevise = (ImageView) findViewById(R.id.btnRevise);
        //btnRecord = (ImageView) findViewById(R.id.btnRecord);
    }
    public void fnRevise(View vw)
    {
        Intent intent = new Intent(this, BeforeRevise.class);
        startActivity(intent);
    }

    public void fnLearn(View vw)
    {
        Intent intent = new Intent(this, BeforeLearn.class);
        startActivity(intent);
    }

    public void fnRecord(View vw)
    {
        Intent intent = new Intent(this, ParentMenu.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }

}
