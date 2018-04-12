package com.example.android.movielist;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static ArrayList<String> movies;
    private static ArrayList<String> codes;
    private static final String[] moviel = {"JAWS", "Airplane!", "Raiders of the Lost Ark", "Ghostbusters", "Groundhog Day", "Dumb and Dumber"};
    private static final String[] mcodes = {"tt0073195", "tt0080339", "tt0082971", "tt0087332", "tt0107048", "tt0109686"};
    private ListView MovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        movies = new ArrayList<>();
        codes = new ArrayList<>();
        Map<String,?>m=getPreferences(this.MODE_PRIVATE).getAll();
        if(m.isEmpty()){
            for(int i=0;i<moviel.length;i++){
                movies.add(moviel[i]);
                codes.add(mcodes[i]);
            }}
        else{
            int size=(Integer)m.get("Size");
        for (int i = 0; i <size; i++) {
            String movie=(String) m.get("Movie"+i+"");
            String code= (String) m.get("Code"+i+"");
            movies.add(movie);
            codes.add(code);
        }}
        MovieList = findViewById(R.id.list);
         ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item_view, movies);
        MovieList.setAdapter(adapter);
        MovieList.setOnItemClickListener(
                new ListView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String url = "https://www.imdb.com/title/" + codes.get(i) + "/";
                        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(in);

                    }
                });
        MovieList.setOnItemLongClickListener(
                new ListView.OnItemLongClickListener() {

                    public boolean onItemLongClick(AdapterView<?> adapterView, View view,final int i, long l) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Remove Movie?");
                        builder.setMessage("Are you sure that you to delete this title?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int id){
                                movies.remove(i);
                                codes.remove(i);
                                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(MainActivity.this, R.layout.list_item_view,movies);

                                MovieList.setAdapter(adapter1);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                           public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                       AlertDialog alert= builder.create();
                       alert.show();
                        return true;
                    }}

        );

    }

    public void ButtonPressed(View v) {
        Intent x = new Intent(this, addtitle_view.class);
        startActivityForResult(x, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        movies.add(data.getStringExtra("Title"));
        codes.add(data.getStringExtra("Code"));
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, R.layout.list_item_view, movies);
        MovieList.setAdapter(adapter2);
    }
    @Override
        public void onStop(){
        super.onStop();

        SharedPreferences p= getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=p.edit();
        for(int i=0;i<movies.size();i++){
            ed.putString("Movie"+i+"", movies.get(i));
            ed.putString("Code"+i+"",codes.get(i));

        }ed.putInt("Size",movies.size());
        ed.apply();
    }




}
