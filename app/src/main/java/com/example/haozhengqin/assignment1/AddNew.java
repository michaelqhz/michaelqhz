package com.example.haozhengqin.assignment1;

import android.content.Context;
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

public class AddNew extends AppCompatActivity{
    private static final String FILENAME = "file.sav";
    private ListView sub_listView;


    private ArrayList<information> sub_arrayList = new ArrayList<information>();
    private ArrayAdapter<information> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnew);
        loadFromFile();

        final EditText NameEditText = (EditText) findViewById(R.id.nameEdit);
        final EditText setInitialValueEditText = (EditText) findViewById(R.id.monthlyCharge_edit);
        final EditText commentEditText = (EditText) findViewById(R.id.commentEdit);
        final Button button2 = (Button) findViewById(R.id.save);

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                setResult(RESULT_OK);

                String name = NameEditText.getText().toString();

                // reference https://stackoverflow.com/questions/15037465/converting-edittext-to-int-android answer by Harshid
                int initialValue = Integer.parseInt(setInitialValueEditText.getText().toString());

                String comment = commentEditText.getText().toString();
                sub_arrayList.add(new information(name, comment, initialValue));
                saveInFile();

                finish();
            }
        });}


    private void loadFromFile() {

        try {
            FileInputStream fis = openFileInput(FILENAME);
            BufferedReader in = new BufferedReader(new InputStreamReader(fis));
            Gson gson = new Gson();

            Type listType = new TypeToken<ArrayList<information>>() {
            }.getType();
            sub_arrayList = gson.fromJson(in, listType);


        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            sub_arrayList = new ArrayList<information>();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new RuntimeException();
        }

    }



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
