package com.example.pm1e18169;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.pm1e18169.configuracion.SQLiteConexion;
import com.example.pm1e18169.tablas.Contactos;
import com.example.pm1e18169.tablas.Transacciones;

import java.util.ArrayList;

public class ActivityList extends AppCompatActivity {

    SQLiteConexion conexion;
    ListView listcontacts;
    ArrayList<Contactos> lista;
    ArrayList<String> listaconcatenada;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
        searchView = (SearchView) findViewById(R.id.buscar);
        listcontacts = (ListView) findViewById(R.id.listcontacts);

        GetListContacts();

        ArrayAdapter adc = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaconcatenada);
        listcontacts.setAdapter(adc);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adc.getFilter().filter(newText);

                return false;
            }
        });

        listcontacts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ActivityAcciones.class);
                intent.putExtra("objeto", lista.get(position));
                startActivity(intent);
            }
        });
    }
    private void GetListContacts(){
        SQLiteDatabase db = conexion.getReadableDatabase();
        Contactos listcontactos = null;
        lista = new ArrayList<Contactos>();
        Cursor cursor = db.rawQuery(Transacciones.Getcontactos, null);
        while (cursor.moveToNext()){
       listcontactos = new Contactos();
       listcontactos.setId(cursor.getInt(0));
       listcontactos.setPais(cursor.getString(1));
       listcontactos.setNombre(cursor.getString(2));
       listcontactos.setTelefono(cursor.getString(3));
       listcontactos.setNota(cursor.getString(4));
       lista.add(listcontactos);
        }

    cursor.close();

    LLenarLista();
    }
    private void LLenarLista()
    {
        listaconcatenada = new ArrayList<String>();

        for(int i =0;  i < lista.size(); i++)
        {
            listaconcatenada.add(lista.get(i).getNombre() + " | " +
                    lista.get(i).getTelefono());
        }
    }

}