package com.mgajardo.SoSelf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Ayuda extends AppCompatActivity {
    int total;
    int Completadas=0;
    int progreso;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
        Button btnprueba = (Button) findViewById(R.id.prueba);
        TextView numero = (TextView) findViewById(R.id.numero);
        ProgressBar prg = (ProgressBar) findViewById(R.id.progressBar2);


        String[] opciones = null;
        Intent intent = getIntent();
        if (intent != null) {
            String[] datoRecibido = intent.getStringArrayExtra("clave");

            if (datoRecibido != null) {
                opciones=datoRecibido;
                total=datoRecibido.length;
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        numero.setText(String.valueOf(total));
        btnprueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Completadas<total){
                    Completadas++;
                    progreso=(Completadas * 100) / total;
                    prg.setProgress(progreso);
                }
            }
        });



    }

}