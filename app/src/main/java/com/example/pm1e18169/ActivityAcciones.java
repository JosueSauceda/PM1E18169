package com.example.pm1e18169;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pm1e18169.configuracion.SQLiteConexion;
import com.example.pm1e18169.tablas.Contactos;
import com.example.pm1e18169.tablas.Transacciones;

public class ActivityAcciones extends AppCompatActivity {
    SQLiteConexion conexion;
    Button btneliminar, btncomp, btnact, btnllamar;
    EditText txtnombre, txttel, txtnota;
    private Contactos obj;
    Integer id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acciones);

        conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
        obj = (Contactos) getIntent().getSerializableExtra("objeto");

        txtnombre = (EditText) findViewById(R.id.txtnombr);
        txttel = (EditText) findViewById(R.id.txtte);
        txtnota = (EditText) findViewById(R.id.txtnot);
        btnact = (Button) findViewById(R.id.btnact);
        btncomp = (Button) findViewById(R.id.btncomp);
        btneliminar = (Button) findViewById(R.id.btneliminar);
        btnllamar = (Button) findViewById(R.id.btnllamar);

        id = obj.getId();
        txtnombre.setText(obj.getNombre());
        txttel.setText(obj.getTelefono());
        txtnota.setText(obj.getNota());

        btnllamar.setOnClickListener(view -> {
            int permissionCheck = ContextCompat.checkSelfPermission(
                    this, Manifest.permission.CALL_PHONE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "No se tiene permiso para realizar llamadas", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 225);
            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);

                callIntent.setData(Uri.parse("tel:"+ obj.getTelefono()));
                startActivity(callIntent);
            }

        });

        btneliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Eliminar();
            }
        });
        btnact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Actualizar();
            }
        });
        btncomp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, obj.getNombre().toString());
            sendIntent.putExtra(Intent.EXTRA_TEXT,obj.getTelefono().toString());
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
    }
});

    }
    private void Eliminar() {
        SQLiteDatabase db = conexion.getWritableDatabase();
        String [] params = {
                id.toString()
        };

        String wherecond = Transacciones.id + "=?";
        db.delete(Transacciones.TbContactos, wherecond, params);

        Toast.makeText(getApplicationContext(), "Contacto Eliminado", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), ActivityList.class);
        startActivity(intent);
    }
    private void Actualizar() {
        SQLiteDatabase db = conexion.getWritableDatabase();
        String [] params = {
                id.toString()
        };

        ContentValues valores = new ContentValues();
        valores.put(Transacciones.nombre, txtnombre.getText().toString());
        valores.put(Transacciones.telefono, txttel.getText().toString());
        valores.put(Transacciones.nota, txtnota.getText().toString());

        db.update(Transacciones.TbContactos, valores, Transacciones.id + "=?", params);

        Toast.makeText(getApplicationContext(), "Contacto Actualizado", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(getApplicationContext(), ActivityList.class);
        startActivity(intent);
    }
}