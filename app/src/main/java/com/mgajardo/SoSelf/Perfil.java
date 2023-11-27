package com.mgajardo.SoSelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Perfil extends AppCompatActivity {
    Intent intent;
    String email;
    String name;
    String telefono;
    String Contraseña;
    TextView txtUser;
    TextView txtEmail;
    TextView txtPhone;
    TextView txtPass;
    Button btnModi;
    Button volver;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        intent = getIntent();
        email = intent.getStringExtra("email");
        InitFireBase();
        consultarEmailEnFirebase(encodeEmailAddress(email));
         txtUser = (TextView) findViewById(R.id.UserText);
         txtEmail = (TextView) findViewById(R.id.EmailText);
         txtPhone = (TextView) findViewById(R.id.PhoneText);
         txtPass = (TextView) findViewById(R.id.PassText);
         btnModi = (Button) findViewById(R.id.si);
         volver = (Button) findViewById(R.id.no);
         volver.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 MandarDatosYCambiarActivity(new Intent(Perfil.this,Inicio.class));
             }
         });
         btnModi.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 MandarDatosYCambiarActivity(new Intent(Perfil.this,ModPerfil.class));
             }
         });
    }
    public void MandarDatosYCambiarActivity(Intent intent, Intent EsteIntent){

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
                    telefono=user.getPhone();
                    Contraseña=user.getPass();
                    txtUser.setText(name);
                    txtEmail.setText(email);
                    txtPhone.setText(telefono);
                    txtPass.setText(Contraseña);
                    Log.d("TAG",name);




                } else {
                    // El usuario no existe en la base de datos
                    Log.d("TAG", "Usuario no encontrado");
                    Toast.makeText(Perfil.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores
                Log.e("TAG", "Error al buscar usuario por correo: " + databaseError.getMessage());
            }
        });
    }
    public void InitFireBase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();
        databaseReference.keepSynced(true); // Desactiva estrictamente las reglas de Firebase (solo para desarrollo)

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
}