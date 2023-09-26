package com.example.textocor;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TextoAdapter extends ArrayAdapter<Texto> {
    private Context context;
    private ArrayList<Texto> textos;

    public TextoAdapter(Context context, ArrayList<Texto> textos) {
        super(context, R.layout.item_texto, textos);
        this.context = context;
        this.textos = textos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View itemView = li.inflate(R.layout.item_texto, parent, false);
        TextView lblTexto = itemView.findViewById(R.id.txtlbl);
        lblTexto.setText(textos.get(position).getText());
        String cor = textos.get(position).getColor();
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

