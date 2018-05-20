package com.dyslexsee.dyslexsee;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Locale;

import static android.speech.RecognizerIntent.EXTRA_PROMPT;

public class Revise extends AppCompatActivity {

    //widgets
    private Button btnSpeech;
    private TextView txtSpeech, txtResult, txtWordSpeak;

    //vars
    private final int REC_CODE_SPEECH_OUTPUT = 143;
    private String word, speech;
    private static final String TAG = "Revise";
    boolean isSuccess = false;
    int count = 0;


    //object
    DbConnection dbConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise);

        getSupportActionBar().hide();

        btnSpeech = (Button) findViewById(R.id.btnSpeech);
        txtSpeech = (TextView) findViewById(R.id.txtSpeech);
        txtResult = (TextView) findViewById(R.id.txtResult);
        txtWordSpeak = (TextView) findViewById(R.id.txtWordSpeak);

        btnSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSpeech();
            }
        });

        dbConn = new DbConnection();

        try {
            Connection con = dbConn.CONN();
            if (con == null) {
                Toast.makeText(this, "Check your connection", Toast.LENGTH_SHORT).show();
            } else {
                Statement stmt = con.createStatement();
                String query = "select words from words where status = 'y' and statusRevise = 'n'";
                ResultSet resultSet = stmt.executeQuery(query);

                while (resultSet.next()) {
                    word = resultSet.getString("words");
                    txtWordSpeak.setText(word);
                }
                resultSet.close();

                //query = " update words set status = 'y' where words = '"+words+"' ";
                //stmt.executeUpdate(query);
            }
        } catch (Exception ex) {
            isSuccess = false;
            ex.printStackTrace();
            System.out.println("Error");
        }
    }

    private void btnSpeech() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please speak now");

        try {
            startActivityForResult(intent, REC_CODE_SPEECH_OUTPUT);
        } catch (ActivityNotFoundException e) {

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REC_CODE_SPEECH_OUTPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    txtSpeech.setText(result.get(0));

                    speech = txtSpeech.getText().toString();
                    Log.d(TAG, "speech: " + speech);
                    if (speech.equals(word)) {
                        txtResult.setText("Correct");
                        txtResult.setTextColor(Color.GREEN);

                        try {
                            Connection con = dbConn.CONN();
                            if (con == null) {
                                Toast.makeText(this, "Check your connection", Toast.LENGTH_SHORT).show();
                            } else {
                                Statement stmt = con.createStatement();

                                String query = " update words set statusRevise = 'y' where words = '" + word + "' ";
                                stmt.executeUpdate(query);
                            }
                        } catch (Exception ex) {
                            isSuccess = false;
                            ex.printStackTrace();
                            System.out.println("Error");
                        }
                    } else {
                        txtResult.setText("Incorect");
                        txtResult.setTextColor(Color.RED);

                        count++;

                    }

                    if (count >= 3) {
                        try {
                            Connection con = dbConn.CONN();
                            if (con == null) {
                                Toast.makeText(this, "Check your connection", Toast.LENGTH_SHORT).show();
                            } else {
                                Statement stmt = con.createStatement();

                                String query = " update words set status = 'n' where words = '" + word + "' ";
                                stmt.executeUpdate(query);

                                Intent intent = new Intent(this, Revise.class);
                                startActivity(intent);
                            }
                        } catch (Exception ex) {
                            isSuccess = false;
                            ex.printStackTrace();
                            System.out.println("Error");
                        }

                    }
                    break;

                }
            }
        }

    }
}
