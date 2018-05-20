package com.dyslexsee.dyslexsee;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Record extends AppCompatActivity {

    //widget
    private ListView listView;

    //object
    private ArrayList<ListModel> itemArrayList;
    private MyAppAdapter myAppAdapter;
    DbConnection dbConn;

    //vars
    private boolean success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        getSupportActionBar().hide();

        listView = (ListView) findViewById(R.id.listView); //ListView Declaration
        itemArrayList = new ArrayList<ListModel>(); // Arraylist Initialization

        dbConn = new DbConnection();

        // Calling Async Task
        SyncData orderData = new SyncData();
        orderData.execute("");


    }
    private class SyncData extends AsyncTask<String, String, String> {
        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error, See Android Monitor in the bottom For details!";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            progress = ProgressDialog.show(Record.this, "Synchronising",
                    "ListView Loading! Please Wait...", true);
        }

        @Override
        protected String doInBackground(String... strings)  // Connect to the database, write query and add items to array list
        {
            try {
                Connection con = dbConn.CONN();
                if (con == null) {
                    //Toast.makeText(Record.this, "Check your connection", Toast.LENGTH_SHORT).show();
                } else {
                    Statement stmt = con.createStatement();
                    // Change below query according to your own database.
                    String query = "select words, status from words order by status asc, words asc";
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs != null) // if resultset not null, I add items to itemArraylist using class created
                    {
                        while (rs.next()) {
                            try {
                                itemArrayList.add(new ListModel(rs.getString("words"), rs.getString("status")));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        msg = "Found";
                        success = true;
                    } else {
                        msg = "No Data found!";
                        success = false;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Writer writer = new StringWriter();
                e.printStackTrace(new PrintWriter(writer));
                msg = writer.toString();
                success = false;
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) // disimissing progress dialoge, showing error and setting up my ListView
        {
            progress.dismiss();
            Toast.makeText(Record.this, msg + "", Toast.LENGTH_LONG).show();
            if (success == false) {
            } else {
                try {
                    myAppAdapter = new MyAppAdapter(itemArrayList, Record.this);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setAdapter(myAppAdapter);
                } catch (Exception ex) {

                }

            }
        }
    }

    public class MyAppAdapter extends BaseAdapter         //has a class viewholder which holds
    {
        public class ViewHolder {
            TextView txtLearntWord;
            TextView txtData;
        }

        public List<ListModel> wordList;

        public Context context;
        ArrayList<ListModel> arraylist;

        private MyAppAdapter(List<ListModel> apps, Context context) {
            this.wordList = apps;
            this.context = context;
            arraylist = new ArrayList<ListModel>();
            arraylist.addAll(wordList);
        }

        @Override
        public int getCount() {
            return wordList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) // inflating the layout and initializing widgets
        {

            View rowView = convertView;
            ViewHolder viewHolder = null;
            if (rowView == null) {
                LayoutInflater inflater = getLayoutInflater();
                rowView = inflater.inflate(R.layout.list_content, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.txtLearntWord = (TextView) rowView.findViewById(R.id.txtLearntWord);
                viewHolder.txtData = (TextView) rowView.findViewById(R.id.txtData);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            // here setting up names and images
            viewHolder.txtLearntWord.setText(wordList.get(position).getWordLearnt() + "");
            viewHolder.txtData.setText(wordList.get(position).getData() + "");
            //Picasso.with(context).load(wordList.get(position).getImg()).into(viewHolder.imageView);

            return rowView;
        }
    }
}

