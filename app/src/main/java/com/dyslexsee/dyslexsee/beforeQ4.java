package com.dyslexsee.dyslexsee;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class beforeQ4 extends AppCompatActivity implements TextToSpeech.OnInitListener {

    //widget
    TextView wordd, instruction;
    Button btnGo;
    ImageView btnHelp;

    //vars
    String word;

    //object
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_before_q4);

        wordd = (TextView)findViewById(R.id.wordd);
        instruction = (TextView)findViewById(R.id.instruction);
        btnGo = (Button)findViewById(R.id.btnGo);
        btnHelp = (ImageView)findViewById(R.id.btnHelp);

        Intent intent = getIntent();
        word = intent.getStringExtra("varWordQ4");

        tts = new TextToSpeech(this, this);

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fnBtnHelp();
            }
        });

        wordd.setText(word);

    }

    public void fnGo(View vw)
    {

            Intent intent = new Intent(this, LearnQ4.class);
            intent.putExtra("varWordQ4",word);
            startActivityForResult(intent,0);

    }

    @Override
    public void onBackPressed() {
        if (!shouldAllowBack()) {
            Intent intent = new Intent(this,BeforeLearn.class);
            startActivity(intent);
        } else {
            super.onBackPressed();
        }
    }

    private boolean shouldAllowBack() {
        return false;
    }

    @Override
    public void onInit(int text) {

        if (text == TextToSpeech.SUCCESS)
        {
            int language = tts.setLanguage(Locale.ENGLISH);
            if (language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                btnHelp.setEnabled(true);
                fnBtnHelp();
            }
        }

    }

    public void fnBtnHelp()
    {
        String sInstruction = instruction.getText().toString();
        tts.speak(sInstruction, TextToSpeech.QUEUE_FLUSH, null);
        //tts.speak(sMeaning1, TextToSpeech.QUEUE_FLUSH, null);
    }
}
