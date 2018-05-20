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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;

public class LearnQ5 extends AppCompatActivity implements TextToSpeech.OnInitListener {

    //widget
    TextView ques5;
    EditText ans5;
    Button btnSubmit5;
    ImageView btnHelp;

    //object
    DbConnection dbConn;
    private TextToSpeech tts;

    //vars
    String sAns5, words5, realWord;
    boolean isSuccess = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn_q5);

        getSupportActionBar().hide();

        ques5 = (TextView)findViewById(R.id.Ques5);

        ans5 = (EditText)findViewById(R.id.ans5);

        btnSubmit5 = (Button)findViewById(R.id.btnsSubmit5);

        btnHelp = (ImageView)findViewById(R.id.btnHelp);

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fnBtnHelp();
            }
        });

        dbConn = new DbConnection();
        tts = new TextToSpeech(this,this);
    }

    public void fnQuestion5(View vw)
    {
        sAns5 = ans5.getText().toString().toLowerCase();
        Intent intent = getIntent();
        words5 = intent.getStringExtra("varWordQ5");
        //realWord = word.getText().toString();
        if (sAns5.equals(words5))
        {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            try{
                Connection con = dbConn.CONN();
                if (con == null)
                {
                    Toast.makeText(this, "Check your connection", Toast.LENGTH_SHORT).show();
                }else{
                    Statement stmt = con.createStatement();
                    /*String query = "select words from words where status = 'n'";
                    ResultSet resultSet = stmt.executeQuery(query);

                    while(resultSet.next())
                    {
                        words = resultSet.getString("words");

                        word.setText(words);
                    }
                    resultSet.close();
                    */
                    String query = " update words set status = 'y' where words = '"+words5+"' ";
                    stmt.executeUpdate(query);

                    intent = new Intent(this, Learn.class);
                    startActivity(intent);

                }
            }catch (Exception ex)
            {
                isSuccess = false;
                ex.printStackTrace();
                System.out.println("Error");
            }
            //Intent intent = new Intent(this, LearnQ2.class);
            //intent.putExtra("varWordQ2",realWord);
            //startActivityForResult(intent,0);
        }else
        {
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
        String sQues = ques5.getText().toString();
        tts.speak(sQues, TextToSpeech.QUEUE_FLUSH, null);
        //tts.speak(sMeaning1, TextToSpeech.QUEUE_FLUSH, null);
    }
}
