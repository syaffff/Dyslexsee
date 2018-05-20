package com.dyslexsee.dyslexsee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BeforeRevise extends AppCompatActivity {

    //widget
    Button btnReviseRead , btnReviseWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_revise);

        getSupportActionBar().hide();

        btnReviseRead = (Button)findViewById(R.id.btnReviseRead);
        btnReviseWrite = (Button)findViewById(R.id.btnReviseWrite);
    }

    public void fnReviseRead (View vw)
    {
        Intent intent = new Intent(this, Revise.class);
        startActivity(intent);
    }

    public void fnReviseWrite (View vw)
    {
        Intent intent = new Intent(this, ReviseWrite.class);
        startActivity(intent);
    }
}
