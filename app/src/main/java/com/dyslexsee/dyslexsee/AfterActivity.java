package com.dyslexsee.dyslexsee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AfterActivity extends AppCompatActivity {

    //widget
    Button btnNxtWord, btnHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after);

        getSupportActionBar().hide();

        btnNxtWord = (Button)findViewById(R.id.btnNxtWord);
        btnHome = (Button)findViewById(R.id.btnHome);
    }

    public void fnNxtWord (View vw)
    {
        Intent intent = new Intent(this, Learn.class);
        startActivity(intent);
    }

    public void fnHome (View vw)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
