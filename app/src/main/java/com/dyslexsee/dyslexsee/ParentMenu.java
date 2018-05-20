package com.dyslexsee.dyslexsee;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ParentMenu extends AppCompatActivity {

    //widget
    Button btnAddWord , btnVwRecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_menu);

        getSupportActionBar().hide();

        btnAddWord = (Button)findViewById(R.id.btnAddWord);
        btnVwRecord = (Button)findViewById(R.id.btnVwRecord);
    }

    public void fnAddWord (View vw)
    {
        Intent intent = new Intent(this, AddWord.class);
        startActivity(intent);
    }

    public void fnVwRecord (View vw)
    {
        Intent intent = new Intent(this, Record.class);
        startActivity(intent);
    }
}
