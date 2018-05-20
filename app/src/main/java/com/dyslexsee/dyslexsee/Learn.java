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

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Locale;
import java.util.Objects;

public class Learn extends AppCompatActivity implements TextToSpeech.OnInitListener {

    //widget
    TextView word, ques1;
    EditText ans1;
    Button btnSubmit;
    ImageView btnHelp;

    //object
    DbConnection dbConn;

    //vars
    String sAns1, words, realWord;
    boolean isSuccess = false;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn);

        getSupportActionBar().hide();

        word = (TextView)findViewById(R.id.word);
        ques1 = (TextView)findViewById(R.id.ques1);

        ans1 = (EditText)findViewById(R.id.ans1);

        btnSubmit = (Button)findViewById(R.id.btnsSubmit);

        btnHelp = (ImageView) findViewById(R.id.btnHelp);

        dbConn = new DbConnection();
        tts = new TextToSpeech(this, this);

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fnBtnHelp();
            }
        });

        try{
            Connection con = dbConn.CONN();
            if (con == null)
            {
                Toast.makeText(this, "Check your connection", Toast.LENGTH_SHORT).show();
            }else{
                Statement stmt = con.createStatement();
                String query = "select words from words where status = 'n' and statusRevise = 'n' ";
                ResultSet resultSet = stmt.executeQuery(query);

                while(resultSet.next())
                {
                    words = resultSet.getString("words");

                    word.setText(words);
                }
                resultSet.close();

                //query = " update words set status = 'y' where words = '"+words+"' ";
                //stmt.executeUpdate(query);
            }
        }catch (Exception ex)
        {
            isSuccess = false;
            ex.printStackTrace();
            System.out.println("Error");
        }

    }

    public void fnQuestion(View vw)
    {
         Runnable run = new Runnable() {
             @Override
             public void run() {
                 sAns1 = ans1.getText().toString().toLowerCase();
                 realWord = word.getText().toString().toLowerCase();

                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         if (sAns1.equals(realWord))
                         {
                             Toast.makeText(Learn.this, "Correct", Toast.LENGTH_SHORT).show();
                             Intent intent = new Intent(Learn.this, LearnQ2.class);
                             intent.putExtra("varWordQ2",realWord);
                             startActivityForResult(intent,0);
                         }else
                         {
                             Toast.makeText(Learn.this, "Incorrect", Toast.LENGTH_SHORT).show();
                         }
                     }
                 });

             }
         };

         Thread thr = new Thread(run);
         thr.start();
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
        String sQues = ques1.getText().toString();
        tts.speak(sQues, TextToSpeech.QUEUE_FLUSH, null);
        //tts.speak(sMeaning1, TextToSpeech.QUEUE_FLUSH, null);
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


}
