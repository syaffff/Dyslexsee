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

public class LearnQ2 extends AppCompatActivity implements TextToSpeech.OnInitListener {

    //widget
    TextView wordQ2 , ques2;
    EditText ans2;
    Button btnSubmit2;
    ImageView btnHelp;

    //vars
    String words2,sAns2;
    int count = 0;
    int iAns2;

    //object
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_q2);

        getSupportActionBar().hide();

        wordQ2 = (TextView)findViewById(R.id.wordQ2);
        ques2 = (TextView)findViewById(R.id.Ques2);
        ans2 = (EditText)findViewById(R.id.ans2);
        btnSubmit2 = (Button)findViewById(R.id.btnsSubmit2);
        btnHelp = (ImageView)findViewById(R.id.btnHelp);

        Intent intent = getIntent();
        words2 = intent.getStringExtra("varWordQ2");

        tts = new TextToSpeech(this, this);

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fnBtnHelp();
            }
        });

        wordQ2.setText(words2);

    }

    public void fnQuestionQ2(View vw)
    {
        Runnable run = new Runnable() {
            @Override
            public void run() {
                sAns2 = ans2.getText().toString().toLowerCase();
                iAns2 = Integer.parseInt(sAns2);
                words2 = wordQ2.getText().toString().toLowerCase();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for( int i=0; i<words2.length(); i++ ) {
                            count++;
                            //Toast.makeText(this, count, Toast.LENGTH_SHORT).show();

                        }
                        if (iAns2 == count)
                        {
                            Toast.makeText(LearnQ2.this, "Correct", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LearnQ2.this, LearnQ3.class);
                            intent.putExtra("varWordQ3",words2);
                            startActivityForResult(intent,0);
                        }else
                        {
                            Toast.makeText(LearnQ2.this, "Incorrect", Toast.LENGTH_SHORT).show();
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
        String sQues = ques2.getText().toString();
        tts.speak(sQues, TextToSpeech.QUEUE_FLUSH, null);
        //tts.speak(sMeaning1, TextToSpeech.QUEUE_FLUSH, null);
    }
}
