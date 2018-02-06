package com.example.haozhengqin.assignment1;

import android.content.Context;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
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
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    private static final String FILENAME = "file.sav";
    private ListView sub_listView;


    private ArrayList<information> sub_arrayList = new ArrayList<information>();
    private ArrayAdapter<information> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonAdd = (Button) findViewById(R.id.add_button);
        sub_listView = (ListView) findViewById(R.id.sub_listView);
        sub_listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    final View view, int position, long id) {

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);

            }
        });

    }
    public void addNew(View view) {
        Intent intent = new Intent(this, AddNew.class);


        startActivity(intent);
    }
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        loadFromFile();


        // reference: Java for Android  2nd Edition by Budi Kurniavan Chinese version Chapter 31.2
        adapter = new ArrayAdapter<information>(this,
                android.R.layout.simple_list_item_1, sub_arrayList);

        sub_listView.setAdapter(adapter);

        TextView countersTitle = (TextView) findViewById(R.id.items_textView);
        String numberOfCounters = "Number of Subscription:" + sub_listView.getAdapter().getCount();
        countersTitle.setText(numberOfCounters);
    }
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
