package com.example.pm1e18169;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pm1e18169.configuracion.SQLiteConexion;
import com.example.pm1e18169.tablas.Transacciones;

public class MainActivity extends AppCompatActivity {
//Josué Sauceda 202010060281 y Luis Romero 201730040069
   Spinner sppais;
   EditText txtnombre, txttel, txtnota;
   Button btnsalvar, btnlista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sppais = (Spinner) findViewById(R.id.sppais);
        txtnombre = (EditText) findViewById(R.id.txtnombre);
        txttel = (EditText) findViewById(R.id.txttel);
        txtnota = (EditText) findViewById(R.id.txtnota);
        btnsalvar = (Button) findViewById(R.id.btnsalvar);
        btnlista = (Button) findViewById(R.id.btnlista);

if (txtnombre.getText().toString().trim().equalsIgnoreCase(""))
    txtnombre.setError("Debe ingresar un nombre");
if (txttel.getText().toString().trim().equalsIgnoreCase(""))
    txttel.setError("Debe ingresar un telefono");
if (txtnota.getText().toString().trim().equalsIgnoreCase(""))
    txtnota.setError("Debe ingresar una nota");
btnsalvar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AgregarContacto();
        }
    });

btnlista.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), ActivityList.class);
        startActivity(intent);
    }
});
    }

    private void AgregarContacto() {
        SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put(Transacciones.pais, sppais.getSelectedItem().toString());
        valores.put(Transacciones.nombre, txtnombre.getText().toString());
        valores.put(Transacciones.telefono, txttel.getText().toString());
        valores.put(Transacciones.nota, txtnota.getText().toString());

        Long resultado  = db.insert(Transacciones.TbContactos, Transacciones.id, valores);

        Toast.makeText(getApplicationContext(), "Registro Ingresado " + resultado.toString()
                , Toast.LENGTH_SHORT).show();

        db.close();

        ClearScreen();
    }

    private void ClearScreen()
    {
        txtnombre.setText("");
        txttel.setText("");
        txtnota.setText("");
    }
    }


