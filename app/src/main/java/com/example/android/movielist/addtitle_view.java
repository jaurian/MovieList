package com.example.android.movielist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

/**
 * Created by android on 4/3/18.
 */

public class addtitle_view extends AppCompatActivity {
private EditText ntitle;
private EditText ncode;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtitle_view);
        ntitle=findViewById(R.id.titlein);
        ncode=findViewById(R.id.codein);

    }
    public void onBackPressed(){
        Intent data=new Intent();
        data.putExtra("Title", ntitle.getText().toString());
        data.putExtra("Code", ncode.getText().toString());
        setResult(RESULT_OK, data);
        finish();
    }
}
