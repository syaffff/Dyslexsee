package com.dyslexsee.dyslexsee;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddWord extends AppCompatActivity {

    //widget
    EditText edtAddWord;
    Button btnAdd;

    //vars
    String newWord;
    boolean isSuccess = false;

    //object
    DbConnection dbConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        getSupportActionBar().hide();

        edtAddWord = (EditText) findViewById(R.id.edtAddWord);
        btnAdd = (Button)findViewById(R.id.btnAdd);

        dbConn = new DbConnection();

    }

    public void fnAdd (View vw)
    {
        newWord = edtAddWord.getText().toString();

        try{
            Connection con = dbConn.CONN();
            if (con == null)
            {
                Toast.makeText(this, "Check your connection", Toast.LENGTH_SHORT).show();
            }else{
                Statement stmt = con.createStatement();
                String query = "select * from words where words = '"+newWord+"'";
                ResultSet resultSet = stmt.executeQuery(query);

                if (resultSet.next())
                {
                    Toast.makeText(this, "Word Existed", Toast.LENGTH_SHORT).show();
                    //query = " update words set status = 'y' where words = '"+words+"' ";
                    //stmt.executeUpdate(query);
                }else
                {
                    query = "insert into words values(null,'"+newWord+"','n','n')";
                    stmt = con.createStatement();
                    stmt.executeUpdate(query);

                    edtAddWord.setText("");

                    Toast.makeText(this, "Word Added", Toast.LENGTH_SHORT).show();
                }
                resultSet.close();

                //query = " update words set status = 'y' where words = '"+words+"' ";
                //
            }
        }catch (Exception ex)
        {
            isSuccess = false;
            ex.printStackTrace();
            System.out.println("Error");
        }

    }
}
