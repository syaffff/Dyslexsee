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

public class ReviseWrite extends AppCompatActivity implements TextToSpeech.OnInitListener {

    //widget
    TextView txtWordWrite;
    Button btnSpeak , btnGo;
    EditText edtAnsWrite;

    //vars
    String sWordWrite, sAnsWrite;
    boolean isSuccess = false;
    int count = 0;

    //object
    DbConnection dbConn;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise_write);

        getSupportActionBar().hide();

        tts = new TextToSpeech(this, this);

        txtWordWrite = (TextView)findViewById(R.id.txtWordWrite);

        edtAnsWrite = (EditText)findViewById(R.id.edtAnsWrite);

        btnSpeak = (Button)findViewById(R.id.btnSpeak);
        btnGo = (Button)findViewById(R.id.btnGo);

        btnSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                speakOutNow();
            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fnGo();
            }
        });

        dbConn = new DbConnection();

        try{
            Connection con = dbConn.CONN();
            if (con == null)
            {
                Toast.makeText(this, "Check your connection", Toast.LENGTH_SHORT).show();
            }else{
                Statement stmt = con.createStatement();
                String query = "select words from words where status = 'y' and statusRevise = 'n'";
                ResultSet resultSet = stmt.executeQuery(query);

                while(resultSet.next())
                {
                    sWordWrite = resultSet.getString("words");

                    txtWordWrite.setText(sWordWrite);
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
                btnSpeak.setEnabled(true);
                speakOutNow();
            }
        }
    }
    public void speakOutNow ()
    {
        sWordWrite = txtWordWrite.getText().toString();
        tts.speak(sWordWrite, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void fnGo()
    {
        sAnsWrite = edtAnsWrite.getText().toString();

        if (sAnsWrite.equals(sWordWrite))
        {
            Toast.makeText(this, "Correct", Toast.LENGTH_SHORT).show();
            edtAnsWrite.setText("");

            try{
                Connection con = dbConn.CONN();
                if (con == null)
                {
                    Toast.makeText(this, "Check your connection", Toast.LENGTH_SHORT).show();
                }else{
                    Statement stmt = con.createStatement();

                    String query = " update words set statusRevise = 'y' where words = '"+sWordWrite+"' ";
                    stmt.executeUpdate(query);
                }
            }catch (Exception ex)
            {
                isSuccess = false;
                ex.printStackTrace();
                System.out.println("Error");
            }

        }else
        {
            Toast.makeText(this, "Incorrect. Please try again", Toast.LENGTH_SHORT).show();
            edtAnsWrite.setText("");
            count++;
        }

        if (count >=3)
        {
            try{
                Connection con = dbConn.CONN();
                if (con == null)
                {
                    Toast.makeText(this, "Check your connection", Toast.LENGTH_SHORT).show();
                }else{
                    Statement stmt = con.createStatement();

                    String query = " update words set status = 'n' where words = '"+sWordWrite+"' ";
                    stmt.executeUpdate(query);

                    Intent intent = new Intent(this, ReviseWrite.class);
                    startActivity(intent);
                }
            }catch (Exception ex)
            {
                isSuccess = false;
                ex.printStackTrace();
                System.out.println("Error");
            }
        }
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
