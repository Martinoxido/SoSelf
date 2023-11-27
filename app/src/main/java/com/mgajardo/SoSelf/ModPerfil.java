package com.mgajardo.SoSelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mgajardo.SoSelf.Model.User;

import java.util.HashMap;
import java.util.Map;

public class ModPerfil extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    Intent intent;
    DatabaseReference databaseReference;
    EditText txtUser;
    TextView txtEmail;
    EditText txtPhone;
    EditText txtPass;
    Button btn;
    Button borrar;
    Button volver;
    String email;
    String name;
    String telefono;
    String Contraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mod_perfil);
        intent = getIntent();
        email = intent.getStringExtra("email");
        InitFireBase();
        txtUser = (EditText) findViewById(R.id.UserText);
        txtEmail = (TextView) findViewById(R.id.EmailText);
        txtPhone = (EditText) findViewById(R.id.PhoneText);
        txtPass = (EditText) findViewById(R.id.PassText);
        btn = (Button) findViewById(R.id.si);
        volver = (Button) findViewById(R.id.no);
        borrar = (Button) findViewById(R.id.btnDelBase);
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MandarDatosYCambiarActivity(new Intent(ModPerfil.this,ConfBorrar.class));
            }
        });
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MandarDatosYCambiarActivity(new Intent(ModPerfil.this,Perfil.class));
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = txtUser.getText().toString();
                telefono = txtPhone.getText().toString();
                Contraseña = txtPass.getText().toString();
                actualizarDatos(encodeEmailAddress(email), name, telefono, Contraseña);
            }
        });
        consultarEmailEnFirebase(encodeEmailAddress(email));

    }
    private void actualizarDatos(String key, String nuevoNombre, String nuevoPhone,String nuevaPass) {
        Map<String, Object> actualizacion = new HashMap<>();
        actualizacion.put("name", nuevoNombre);
        actualizacion.put("phone", nuevoPhone);
        actualizacion.put("pass", nuevaPass);

        databaseReference.child("User").child(key).updateChildren(actualizacion)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ModPerfil.this,"Actualizado", Toast.LENGTH_LONG);
                        Log.d("TAG", "onSuccess: SELOGRO ");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ModPerfil.this,"Error al actualizar", Toast.LENGTH_LONG);
                    }
                });
        MandarDatosYCambiarActivity(new Intent(ModPerfil.this,Perfil.class));
        //databaseReference.child("elementos").child(key).updateChildren(actualizacion);
    }
    public void InitFireBase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();
        databaseReference.keepSynced(true); // Desactiva estrictamente las reglas de Firebase (solo para desarrollo)

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
                    Toast.makeText(ModPerfil.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
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
    public void MandarDatosYCambiarActivity(Intent EsteIntent){
        Log.d("DATOS","EMAIL: "+ email);
        EsteIntent.putExtra("email", email);

        startActivity(EsteIntent);
    }
}