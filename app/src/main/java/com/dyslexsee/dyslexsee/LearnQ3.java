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

public class LearnQ3 extends AppCompatActivity implements TextToSpeech.OnInitListener {

    //widget
    TextView wordQ3, ques3;
    EditText ans3;
    Button btnSubmit3;
    ImageView btnHelp;

    //vars
    String words3,sAns3,fLetter;

    //object
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_q3);

        getSupportActionBar().hide();

        wordQ3 = (TextView)findViewById(R.id.wordQ3);
        ques3 = (TextView)findViewById(R.id.Ques3);
        ans3 = (EditText)findViewById(R.id.ans3);
        btnSubmit3 = (Button)findViewById(R.id.btnsSubmit3);
        btnHelp = (ImageView) findViewById(R.id.btnHelp);

        Intent intent = getIntent();
        words3 = intent.getStringExtra("varWordQ3");

        tts = new TextToSpeech(this, this);

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fnBtnHelp();
            }
        });

        wordQ3.setText(words3);
    }

    public void fnQuestionQ3(View vw) {
       Runnable run = new Runnable() {
           @Override
           public void run() {
               sAns3 = ans3.getText().toString().toLowerCase();
               words3 = wordQ3.getText().toString().toLowerCase();
               fLetter = words3.substring(0, 1);

               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       if (sAns3.equals(fLetter)) {
                           Toast.makeText(LearnQ3.this, "Correct", Toast.LENGTH_SHORT).show();
                           Intent intent = new Intent(LearnQ3.this, beforeQ4.class);
                           intent.putExtra("varWordQ4",words3);
                           startActivityForResult(intent,0);
                       } else {
                           Toast.makeText(LearnQ3.this, "Incorrect", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
           }
       };
       Thread thr = new Thread(run);
       thr.start();
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
        String sQues = ques3.getText().toString();
        tts.speak(sQues, TextToSpeech.QUEUE_FLUSH, null);
        //tts.speak(sMeaning1, TextToSpeech.QUEUE_FLUSH, null);
    }
}
