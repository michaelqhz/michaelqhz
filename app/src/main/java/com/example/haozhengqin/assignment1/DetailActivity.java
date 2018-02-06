package com.example.haozhengqin.assignment1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
import static com.example.haozhengqin.assignment1.R.id.positionTextView;

/**
 * Created by haozhengqin on 2018/2/5.
 */

public class DetailActivity extends AppCompatActivity{
    private static final String FILENAME = "file.sav";
    private ListView sub_listView;

    private ArrayList<information> sub_arrayList = new ArrayList<information>();
    private ArrayAdapter<information> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        final int position = intent.getIntExtra("position", 0);
        loadFromFile();

        final information information = sub_arrayList.get(position);
        String name = information.getName();
        String comment = information.getComment();
        int currentValue = information.getValue();


        TextView counterName = (TextView) findViewById(R.id.nameTextView);
        TextView commentTextView = (TextView) findViewById(R.id.noComment_textView);
        final TextView currentValueTextView = (TextView) findViewById(R.id.monthlyChargeTextView);

        Button edit_button = (Button) findViewById(R.id.edit_button);
        Button delete_button = (Button) findViewById(R.id.delete_button);
        counterName.setText(name);
        commentTextView.setText(comment);
        TextView counterNameTextView = (TextView) findViewById(positionTextView);
        counterNameTextView.setText(String.valueOf(position));

        currentValueTextView.setText(String.valueOf(currentValue));

        delete_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_OK);
                //counters.set
                sub_arrayList.remove(position);
                saveInFile();
                finish();
            }
        });


        edit_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                setResult(RESULT_OK);
                Intent intent = new Intent(DetailActivity.this, EditActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
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
