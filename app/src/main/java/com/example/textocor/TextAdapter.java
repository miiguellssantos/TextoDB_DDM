package com.example.textocor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TextAdapter extends ArrayAdapter<Text> {
    private Context context;
    private ArrayList<Text> texts;

    public TextAdapter(Context context, ArrayList<Text> texts) {
        super(context, R.layout.item_texto, texts);
        this.context = context;
        this.texts = texts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View itemView = li.inflate(R.layout.item_texto, parent, false);
        TextView lblTexto = itemView.findViewById(R.id.txtlbl);
        lblTexto.setText(texts.get(position).getText());
        String cor = texts.get(position).getColor();
        if (cor != null) {
            if (cor.equals("vermelho") ){
                lblTexto.setTextColor(Color.RED);
            } else if (cor.equals("azul")){
                lblTexto.setTextColor(Color.BLUE);
            } else if (cor.equals("verde")){
                lblTexto.setTextColor(Color.GREEN);
            }
        }
        return itemView;
    }
}

