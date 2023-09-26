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

// Gustavo Trizotti e Arthur Mascaro

public class MainActivity extends AppCompatActivity {

    private ListView listaTextos;
    private ArrayList<Texto> textos = new ArrayList<>();
    private Button btnAddText;
    private TextoAdapter adaptador;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaTextos = findViewById(R.id.listView);
        btnAddText = findViewById(R.id.btnAddText);

        EscutadorLista el = new EscutadorLista();
        listaTextos.setOnItemClickListener( el );
        listaTextos.setOnItemLongClickListener( el );

        db = openOrCreateDatabase("db", MODE_PRIVATE, null);
        String cmd = "CREATE TABLE IF NOT EXISTS textTable (id INTEGER PRIMARY KEY AUTOINCREMENT, texto VARCHAR(50), color VARCHAR(50))";
        db.execSQL(cmd);

        btnAddText.setOnClickListener(new EscutadorBotao());

        adaptador = new TextoAdapter(this, textos);

        listaTextos.setAdapter(adaptador);

        cmd = "SELECT * FROM textTable";
        Cursor cursor = db.rawQuery(cmd, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            int id = cursor.getInt(0);
            String texto = cursor.getString(1);
            String cor = cursor.getString(2);
            Texto text = new Texto(id, texto, cor);
            textos.add(text);
            cursor.moveToNext();
        }


    }

    private class EscutadorBotao implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(MainActivity.this, TextoActivity.class);
            startActivityForResult(i, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 1) && (resultCode == RESULT_OK)) {
            String texto = data.getStringExtra("texto");
            String cor = data.getStringExtra("cor");
            Texto text = new Texto(texto, cor);

            String cmd;
            cmd = "INSERT INTO textTable (texto, color) VALUES ('" + text.getText() + "', '" + text.getColor() + "')";
            db.execSQL(cmd);

            textos.add(text);
            adaptador.notifyDataSetChanged();
        }
    }

    private class EscutadorLista implements AdapterView.OnItemClickListener,
            AdapterView.OnItemLongClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Texto texto = textos.get(i);
            String msg = "Texto: " + texto.getText() + "\nCor: " + texto.getColor();
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
        }

        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            Texto texto = textos.get(i);
            textos.remove(i);
            String cmd = "DELETE FROM textTable WHERE id = " + texto.getId();
            db.execSQL(cmd);
            adaptador.notifyDataSetChanged();
            return true;
        }
    }
}