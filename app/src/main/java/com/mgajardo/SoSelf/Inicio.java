package com.mgajardo.SoSelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mgajardo.SoSelf.Model.User;


public class Inicio extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView txtnombreUsuario;
    Intent  intent ;
    String email;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        InitFireBase();
        Button btnMapa = (Button) findViewById(R.id.btnMap);
        Button btnForm = (Button) findViewById(R.id.btnForm);
        Button btnPerfil= (Button) findViewById(R.id.button6) ;
        txtnombreUsuario = (TextView) findViewById(R.id.nombre);
        intent = getIntent();
        email = intent.getStringExtra("email");
        Log.d("tag","aa"+ email);
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
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MandarDatosYCambiarActivity(new Intent(Inicio.this,Perfil.class));
            }
        });
        consultarEmailEnFirebase(encodeEmailAddress(email));


        txtnombreUsuario.setText(name);
    }
    public void InitFireBase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();
        databaseReference.keepSynced(true); // Desactiva estrictamente las reglas de Firebase (solo para desarrollo)

    }
    public void MandarDatosYCambiarActivity(Intent EsteIntent){
        Log.d("DATOS","EMAIL: "+ email);
        EsteIntent.putExtra("email", email);

        startActivity(EsteIntent);
    }
    private void consultarEmailEnFirebase(String emailConsultar) {
        DatabaseReference userRef = firebaseDatabase.getReference("User").child(emailConsultar);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);
                    name=user.getName();
                    txtnombreUsuario.setText(name);
                    Log.d("TAG",name);




                } else {
                    // El usuario no existe en la base de datos
                    Log.d("TAG", "Usuario no encontrado");
                    Toast.makeText(Inicio.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores
                Log.e("TAG", "Error al buscar usuario por correo: " + databaseError.getMessage());
            }
        });
    }
    private String encodeEmailAddress(String email) {
        // Reemplaza el punto con el código interno
        return email.replace(".", "_-=+'PUNTO'+=-_");
    }
}