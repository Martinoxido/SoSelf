package com.mgajardo.SoSelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Inicio extends AppCompatActivity {
    TextView txtnombreUsuario;
    Intent  intent ;
    String nombre ;
    String email ;
    String phone ;
    String pass ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Button btnMapa = (Button) findViewById(R.id.btnMap);
        Button btnForm = (Button) findViewById(R.id.btnForm);
        intent = getIntent();
        nombre = intent.getStringExtra("name");
        nombre = intent.getStringExtra("email");
        nombre = intent.getStringExtra("phone");
        nombre = intent.getStringExtra("pass");

        verificarDatos();


        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Inicio.this, Maps.class));
            }
        });
        btnForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MandarDatosYCambiarActivity(new Intent(Inicio.this,FormFeel.class));
                finish();

            }
        });

        txtnombreUsuario = (TextView) findViewById(R.id.nombre);


        txtnombreUsuario.setText(intent.getStringExtra("name"));
    }
    public void MandarDatosYCambiarActivity(Intent EsteIntent){
        EsteIntent.putExtra("name", nombre);
        EsteIntent.putExtra("email", email);
        EsteIntent.putExtra("phone", phone);
        EsteIntent.putExtra("pass", pass);
        startActivity(EsteIntent);
    }
    public void verificarDatos(){
        Log.d("TAG", "dato" + nombre);
        Log.d("TAG2", "dato" + email);
        Log.d("TAG3", "dato" + phone);
        Log.d("TAG4", "dato" + pass);
    }

}