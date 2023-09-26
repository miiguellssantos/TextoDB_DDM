package com.example.textocor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

// Miguel Santos e Giovanna Caroline

public class MainActivity extends AppCompatActivity {

    private ListView textsList;
    private ArrayList<Text> texts = new ArrayList<>();
    private Button btnAddText;
    private TextAdapter adapter;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textsList = findViewById(R.id.textsList);
        btnAddText = findViewById(R.id.btnAddText);

        ListListener ll = new ListListener();
        textsList.setOnItemClickListener( ll );
        textsList.setOnItemLongClickListener( ll );

        db = openOrCreateDatabase("db", MODE_PRIVATE, null);
        String cmd = "CREATE TABLE IF NOT EXISTS textTable (id INTEGER PRIMARY KEY AUTOINCREMENT, texto VARCHAR(50), color VARCHAR(50))";
        db.execSQL(cmd);

        btnAddText.setOnClickListener(new BtnListener());

        adapter = new TextAdapter(this, texts);

        textsList.setAdapter(adapter);

        cmd = "SELECT * FROM textTable";
        Cursor cursor = db.rawQuery(cmd, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String texto = cursor.getString(1);
            String cor = cursor.getString(2);
            Text text = new Text(id, texto, cor);
            texts.add(text);
            cursor.moveToNext();
        }


    }

    private class ListListener implements AdapterView.OnItemClickListener,
            AdapterView.OnItemLongClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Text text = texts.get(i);
            String msg = "Text: " + text.getText() + "\nCor: " + text.getColor();
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            Text text = texts.get(i);
            texts.remove(i);
            String cmd = "DELETE FROM textTable WHERE id = " + text.getId();
            db.execSQL(cmd);
            adapter.notifyDataSetChanged();
            return true;
        }
    }

    private class BtnListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(MainActivity.this, TextActivity.class);
            startActivityForResult(i, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 1) && (resultCode == RESULT_OK)) {
            String texto = data.getStringExtra("texto");
            String cor = data.getStringExtra("cor");
            Text text = new Text(texto, cor);

            String cmd;
            cmd = "INSERT INTO textTable (texto, color) VALUES ('" + text.getText() + "', '" + text.getColor() + "')";
            db.execSQL(cmd);

            texts.add(text);
            adapter.notifyDataSetChanged();
        }
    }


}