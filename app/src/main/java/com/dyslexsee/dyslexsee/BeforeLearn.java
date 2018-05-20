package com.dyslexsee.dyslexsee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BeforeLearn extends AppCompatActivity {

    //widget
    Button btnLearnWord , btnLearnHomo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_learn);

        getSupportActionBar().hide();

        btnLearnWord = (Button)findViewById(R.id.btnLearnWord);
        btnLearnHomo = (Button)findViewById(R.id.btnLearnHomo);
    }

    public void fnLearnWord (View vw)
    {
        Intent intent = new Intent(this, Learn.class);
        startActivity(intent);
    }

    public void fnLearnHomo (View vw)
    {
        Intent intent = new Intent(this, Learn2.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        if (!shouldAllowBack()) {
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        } else {
            super.onBackPressed();
        }
    }

    private boolean shouldAllowBack() {
        return false;
    }
}
