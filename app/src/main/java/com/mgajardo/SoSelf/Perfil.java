package com.mgajardo.SoSelf;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Perfil extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        TextView txtUser = (TextView) findViewById(R.id.UserText);
        TextView txtEmail = (TextView) findViewById(R.id.EmailText);
        TextView txtPhone = (TextView) findViewById(R.id.PhoneText);
        TextView txtPass = (TextView) findViewById(R.id.PassText);
    }
    public void MandarDatosYCambiarActivity(Intent intent, Intent EsteIntent){
        EsteIntent.putExtra("name", intent.getStringExtra("nombre"));
        EsteIntent.putExtra("name", intent.getStringExtra("email"));
        EsteIntent.putExtra("name", intent.getStringExtra("phone"));
        EsteIntent.putExtra("name", intent.getStringExtra("pass"));
        startActivity(EsteIntent);
    }
}