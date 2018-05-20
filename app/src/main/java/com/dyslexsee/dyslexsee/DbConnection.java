package com.dyslexsee.dyslexsee;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 * Created by Lonovo on 4/16/2018.
 */

public class DbConnection {
    String classs = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://192.168.0.119/dyslexsee";
    //String url = "jdbc:mysql://192.168.0.103/dyslexsee"; //rifhanupdated
    //String url = "jdbc:mysql://192.168.0.101/dyslexsee"; //rifhan kat utem
    String username = "user";
    String password = "123";

    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL = null;
        try {

            Class.forName(classs);

            conn = DriverManager.getConnection(url, username, password);

            //conn = DriverManager.getConnection(ConnURL);
        } catch (SQLException se) {
            Log.e("ERRO", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERRO", e.getMessage());
        } catch (Exception e) {
            Log.e("ERRO", e.getMessage());
        }
        return conn;
    }

}