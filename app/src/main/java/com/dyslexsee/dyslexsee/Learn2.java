package com.dyslexsee.dyslexsee;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Locale;

public class Learn2 extends AppCompatActivity implements TextToSpeech.OnInitListener {

    //widget
    TextView txtHomo1, txtHomo2;
    EditText edtHomoAns1 , edtHomoAns2;
    Button btnHomoSpeak1 , btnHomoSpeak2 , btnSubmitHomo;

    //vars
    String sHomo1, sHomo2 , sHomoAns1, sHomoAns2 ,sMeaning1 , sMeaning2 , sSay1, sSay2;
    boolean isSuccess = false;
    //int count = 0;

    //object
    DbConnection dbConn;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learn2);

        getSupportActionBar().hide();

        txtHomo1 = (TextView) findViewById(R.id.txtHomo1);
        txtHomo2 = (TextView) findViewById(R.id.txtHomo2);

        edtHomoAns1 = (EditText) findViewById(R.id.edtHomoAns1);
        edtHomoAns2 = (EditText) findViewById(R.id.edtHomoAns2);

        btnHomoSpeak1 = (Button) findViewById(R.id.btnHomoSpeak1);
        btnHomoSpeak2 = (Button) findViewById(R.id.btnHomoSpeak2);
        btnSubmitHomo = (Button) findViewById(R.id.btnSubmitHomo);

        btnHomoSpeak1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fnBtnHomoSpeak1();
            }
        });

        btnHomoSpeak2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fnBtnHomoSpeak2();
            }
        });

        btnSubmitHomo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fnBtnSubmitHomo();
            }
        });

        tts = new TextToSpeech(this, this);
        dbConn = new DbConnection();

        try{
            Connection con = dbConn.CONN();
            if (con == null)
            {
                Toast.makeText(this, "Check your connection", Toast.LENGTH_SHORT).show();
            }else{
                Statement stmt = con.createStatement();
                String query = "select homo1, homo2 , meaning1 from homonym where statusHomo = 'n' ";
                ResultSet resultSet = stmt.executeQuery(query);

                while(resultSet.next())
                {
                    sHomo1 = resultSet.getString("homo1");
                    sHomo2 = resultSet.getString("homo2");
                    txtHomo1.setText(sHomo1);
                    txtHomo2.setText(sHomo2);

                    sMeaning1 = resultSet.getString("meaning1");
                    sMeaning2 = resultSet.getString("meaning2");



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

    @Override
    public void onInit(int text)
    {
        if (text == TextToSpeech.SUCCESS)
        {
            int language = tts.setLanguage(Locale.ENGLISH);
            if (language == TextToSpeech.LANG_MISSING_DATA || language == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                btnHomoSpeak1.setEnabled(true);
                btnHomoSpeak2.setEnabled(true);
                fnBtnHomoSpeak1();
            }
        }
    }

    public void fnBtnHomoSpeak1()
    {
        StringBuilder str1 = new StringBuilder();
        str1.append(sHomo1);
        str1.append("...................................................................................................................................");
        str1.append(sMeaning1);
        tts.speak(sHomo1, TextToSpeech.QUEUE_FLUSH, null);
        //tts.speak(sMeaning1, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void fnBtnHomoSpeak2()
    {
        tts.speak(sHomo2, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void  fnBtnSubmitHomo()
    {
        sHomoAns1 = edtHomoAns1.getText().toString();
        sHomoAns2 = edtHomoAns2.getText().toString();

        if (sHomoAns1.equals(sHomo1) && sHomoAns2.equals(sHomo2))
        {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            edtHomoAns1.setText("");
            edtHomoAns2.setText("");

            try{
                Connection con = dbConn.CONN();
                if (con == null)
                {
                    Toast.makeText(this, "Check your connection", Toast.LENGTH_SHORT).show();
                }else{
                    Statement stmt = con.createStatement();

                    String query = " update homonym set statusHomo = 'y' where homo1 = '"+sHomo1+"' ";
                    stmt.executeUpdate(query);
                }
            }catch (Exception ex)
            {
                isSuccess = false;
                ex.printStackTrace();
                System.out.println("Error");
            }

            Intent intent = new Intent(this, Learn2.class);
            startActivity(intent);

        }else
        {
            if (!sHomoAns1.equals(sHomo1) && sHomoAns2.equals(sHomo2))
            {
                Toast.makeText(this, "Incorrect. Please try again", Toast.LENGTH_SHORT).show();
                edtHomoAns1.setText("");
            }else if(sHomoAns1.equals(sHomo1) && !sHomoAns2.equals(sHomo2))
            {
                Toast.makeText(this, "Incorrect. Please try again", Toast.LENGTH_SHORT).show();
                edtHomoAns2.setText("");
            }else
            {
                Toast.makeText(this, "Incorrect. Please try again", Toast.LENGTH_SHORT).show();
                edtHomoAns1.setText("");
                edtHomoAns2.setText("");
            }

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
}
