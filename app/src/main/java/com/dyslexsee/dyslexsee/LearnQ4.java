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

import org.w3c.dom.Text;

import java.util.Locale;

public class LearnQ4 extends AppCompatActivity implements TextToSpeech.OnInitListener {

    //widget
    TextView ques4;
    EditText ans4;
    Button btnSubmit4;
    ImageView btnHelp;

    //vars
    String words4,sAns4,lLetter;

    //Object
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_q4);

        getSupportActionBar().hide();

        //wordQ4 = (TextView)findViewById(R.id.wordQ4);
        ans4 = (EditText)findViewById(R.id.ans4);
        btnSubmit4 = (Button)findViewById(R.id.btnsSubmit4);
        ques4 = (TextView) findViewById(R.id.Ques4);
        btnHelp = (ImageView) findViewById(R.id.btnHelp);

        tts = new TextToSpeech(this, this);

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fnBtnHelp();
            }
        });
        //wordQ4.setText(words4);

    }

    public void fnQuestionQ4(View vw) {
        sAns4 = ans4.getText().toString().toLowerCase();
        Intent intent = getIntent();
        words4 = intent.getStringExtra("varWordQ4");
        //words4 = wordQ4.getText().toString();
        lLetter = words4.substring(words4.length() -1);

        if (sAns4.equals(lLetter)) {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            intent = new Intent(this, LearnQ5.class);
            intent.putExtra("varWordQ5",words4);
            startActivityForResult(intent,0);
        } else {
            Toast.makeText(this, "Incorrect", Toast.LENGTH_SHORT).show();
        }
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
        String sQues = ques4.getText().toString();
        tts.speak(sQues, TextToSpeech.QUEUE_FLUSH, null);
        //tts.speak(sMeaning1, TextToSpeech.QUEUE_FLUSH, null);
    }
}
