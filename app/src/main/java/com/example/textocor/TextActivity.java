package com.example.textocor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TextActivity extends AppCompatActivity {

    private TextView txtNome;
    private Button cancelButton;
    private Button okButton;
    private RadioGroup radioGroup;
    private String color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_texto);

        txtNome = findViewById(R.id.TextoText);
        okButton = findViewById(R.id.button);
        cancelButton = findViewById(R.id.button2);
        radioGroup = findViewById(R.id.radioGroup);

        cancelButton.setOnClickListener(new cancelButtonListener());
        okButton.setOnClickListener(new okButtonListener());
    }

    private class cancelButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            txtNome.setText("");
            radioGroup.setSelected(false);
            Intent intent = new Intent();
            setResult(RESULT_CANCELED, intent);
            finish();
        }
    }

    private class okButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String text = txtNome.getText().toString();

            String msg;

            if (txtNome.getText().toString().isEmpty()){
                msg = "Digite um texto";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                return;
            }

            if(radioGroup.getCheckedRadioButtonId() == R.id.radDisc1){
                color = "vermelho";
            } else if(radioGroup.getCheckedRadioButtonId() == R.id.radDisc2){
                color = "verde";
            } else if(radioGroup.getCheckedRadioButtonId() == R.id.radDisc3){
                color = "azul";
            } else {
                msg = "Selecione uma cor";
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                return;
            }



            txtNome.setText("");
            Intent intent = getIntent();
            intent.putExtra("texto", text);
            intent.putExtra("cor", color);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}