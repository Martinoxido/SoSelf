package com.mgajardo.SoSelf;

import android.content.Intent;
import android.os.Bundle;
import android.security.identity.PersonalizationData;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.mgajardo.SoSelf.Model.User;

import java.util.UUID;

public class Register extends AppCompatActivity {

    TextView txtNombre;
    TextView txtTelefono;
    TextView txtEmail;
    TextView txtPass;
    TextView txtComPass;
    TextView txtverif;
    //BD
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button btnvolver = (Button) findViewById(R.id.button5);
        Button btnCrear = (Button) findViewById(R.id.button4);

        txtNombre = (TextView) findViewById(R.id.editTextNombre);
        txtEmail = (TextView) findViewById(R.id.editTextTextEmailAddressReg);
        txtTelefono = (TextView) findViewById(R.id.editTextNumber);
        txtPass = (TextView) findViewById(R.id.editTextTextPassword);
        txtComPass = (TextView) findViewById(R.id.editTextTextPassword2);
        txtverif = (TextView) findViewById(R.id.verif);
        txtverif.setVisibility(View.INVISIBLE);
        InitFireBase();

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsValid()){
                    User user = new User();
                    user.setName(txtNombre.getText().toString());
                    user.setEmail(txtEmail.getText().toString());
                    user.setPhone(txtTelefono.getText().toString());
                    user.setPass(txtPass.getText().toString());
                    databaseReference.child("User").child(encodeEmailAddress(user.getEmail())).setValue(user);
                    txtverif.setText("Usuario Creado Exitosamente");
                    txtverif.setVisibility(View.VISIBLE);
                    Limpiar();
                }
            }
        });
        btnvolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, MainActivity.class));
            }
        });
    }
    public void InitFireBase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference= firebaseDatabase.getReference();

    }
    public void Limpiar(){
        txtComPass.setText("");
        txtPass.setText("");
        txtTelefono.setText("");
        txtNombre.setText("");
        txtEmail.setText("");
    }
    public Boolean IsValid(){
        String p1,p2;
        p1=txtPass.getText().toString();
        p2=txtComPass.getText().toString();
        if (txtNombre.getText().toString().equals("")){
            txtNombre.setError("Obligatorio");
            return false;
        }
        else if (txtEmail.getText().toString().equals("")){
            txtEmail.setError("Obligatorio");
            return false;
        }
        else if (txtTelefono.getText().toString().equals("")){
            txtTelefono.setError("Obligatorio");
            return false;
        }
        else if (txtPass.getText().toString().equals("")){
            txtPass.setError("Obligatorio");
            return false;
        }
        else if (txtComPass.getText().toString().equals("")){
            txtComPass.setError("Obligatorio");
            return false;
        }

        else if(!(p1.equals(p2))){
            txtComPass.setError("Las passwords no coinciden");
            return false;
        }else{
            return true;
        }

    }

    private String encodeEmailAddress(String email) {
        // Reemplaza el punto con el código interno
        return email.replace(".", "_-=+'PUNTO'+=-_");
    }

    private String decodeEmailAddress(String encodedEmail) {
        // Invierte la transformación
        return encodedEmail.replace("_-=+'PUNTO'+=-_", ".");
    }

}