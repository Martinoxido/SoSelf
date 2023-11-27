package com.mgajardo.SoSelf;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mgajardo.SoSelf.Model.User;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView txtEmail;
    TextView txtPass;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitFireBase();
        txtEmail = (TextView) findViewById(R.id.editTextTextEmailAddress);
        txtPass = (TextView) findViewById(R.id.editTextTextPassAddress);
        txtPass.setText("");
        txtEmail.setText("");
        Button saltarbtn = (Button) findViewById(R.id.button2);
        Button btnReg = (Button) findViewById(R.id.button3);
        Button btn = (Button) findViewById(R.id.button);
        intent = new Intent(MainActivity.this, Inicio.class);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((txtEmail.getText().toString().equals(""))){
                    txtEmail.setError("Ingresa un email");
                } else if ((txtPass.getText().toString().equals(""))) {
                    txtPass.setError("Ingresa una password");
                }else{
                    consultarEmailEnFirebase(encodeEmailAddress(txtEmail.getText().toString()));
                }

                //
            }
        });
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });
        saltarbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FormFeel.class));
            }
        });
    }
    private void consultarEmailEnFirebase(String emailConsultar) {
        DatabaseReference userRef = firebaseDatabase.getReference("User").child(emailConsultar);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    User user = dataSnapshot.getValue(User.class);


                        if(txtPass.getText().toString().equals(user.getPass().toString())){
                            Log.d("INICIOSesion","email "+ user.getEmail());
                            intent.putExtra("email", user.getEmail().toString());
                            startActivity(intent);
                        }else{
                            Log.d("TAG", "Contra MALA ");
                        }


                } else {
                    // El usuario no existe en la base de datos
                    Log.d("TAG", "Usuario no encontrado");
                    Toast.makeText(MainActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
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

    private String decodeEmailAddress(String encodedEmail) {
        // Invierte la transformación
        return encodedEmail.replace("_-=+'PUNTO'+=-_\"", ".");
    }
}
