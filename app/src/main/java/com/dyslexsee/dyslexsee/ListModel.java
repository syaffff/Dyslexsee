package com.dyslexsee.dyslexsee;

/**
 * Created by Lonovo on 4/29/2018.
 */

public class ListModel {
    public String wordLearnt; //word
    public String data; //data

    public ListModel(String wordLearnt, String data)
    {
        this.wordLearnt = wordLearnt;
        this.data = data;
    }

    public String getWordLearnt() {
        return wordLearnt;
    }

    public String getData() {

        if (data.equals("n"))
        {
            data = "Not Learned";
        }else
        {
            data = "Learned";
        }
        return data;
    }
}


