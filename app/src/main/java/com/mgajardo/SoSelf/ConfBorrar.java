package com.mgajardo.SoSelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfBorrar extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    Intent intent;
    Intent adios;
    DatabaseReference databaseReference;
    String email;
    Button si;
    Button no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conf_borrar);
        intent = getIntent();
        email = intent.getStringExtra("email");
        InitFireBase();
        si = (Button) findViewById(R.id.si);
        no = (Button) findViewById(R.id.no);

        si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarDatos(encodeEmailAddress(email));
                adios = new Intent(ConfBorrar.this, MainActivity.class);
                startActivity(adios);
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MandarDatosYCambiarActivity(new Intent(ConfBorrar.this,ModPerfil.class));
            }
        });
    }

    private String encodeEmailAddress(String email) {
        // Reemplaza el punto con el código interno
        return email.replace(".", "_-=+'PUNTO'+=-_");
    }
    public void MandarDatosYCambiarActivity(Intent EsteIntent){
        Log.d("DATOS","EMAIL: "+ email);
        EsteIntent.putExtra("email", email);

        startActivity(EsteIntent);
    }
    public void InitFireBase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();
        databaseReference.keepSynced(true); // Desactiva estrictamente las reglas de Firebase (solo para desarrollo)

    }
    private void eliminarDatos(String key) {
        databaseReference.child("User").child(key).removeValue();
    }

}