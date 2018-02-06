package com.example.haozhengqin.assignment1;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by haozhengqin on 2018/2/5.
 */

public class EditActivity extends AppCompatActivity{
    private static final String FILENAME = "file.sav";
    private ListView sub_listView;


    private ArrayList<information> sub_arrayList = new ArrayList<information>();
    private ArrayAdapter<information> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnew);

        /**
         * reference https://stackoverflow.com/questions/5644543/passing-listview-row-positions-through-intents-to-another-class
         */
        Intent intent = getIntent();
        final int position = intent.getIntExtra("position", 0);
        loadFromFile();


        final EditText newName_textView = (EditText) findViewById(R.id.newName_textView);               //counter name
        final EditText positiveInteger_textView = (EditText) findViewById(R.id.positiveInteger_textView);             //current value
        final EditText newComment_textView = (EditText) findViewById(R.id.newComment_textView);                       // comment
        final Button newSave_button = (Button) findViewById(R.id.newSave_button);


        final information counter = sub_arrayList.get(position);
        String name = counter.getName();
        String comment = counter.getComment();
        int currentValue = counter.getValue();


        newSave_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_OK);

                counter.setName(newName_textView.getText().toString());
                counter.setComment(newComment_textView.getText().toString());
                counter.setDate();
                counter.setValue(Integer.parseInt(positiveInteger_textView.getText().toString()));

                sub_arrayList.set(position, counter);

                saveInFile();
                finish();
            }
        });


    }

    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<information>>() {
            }.getType();
            sub_arrayList = gson.fromJson(in, listType);

            //google/gson/blob/master

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            sub_arrayList = new ArrayList<information>();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }

    }

    /**
     * save file after adding new counter,code from CMPUT301 Lab lonelytwitter source codes
     */
    private void saveInFile() {
        try {
            FileOutputStream fos = openFileOutput(FILENAME,
                    Context.MODE_PRIVATE);

            OutputStreamWriter writer = new OutputStreamWriter(fos);
            Gson gson = new Gson();
            gson.toJson(sub_arrayList, writer);
            writer.flush();


            fos.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }
    }

}
