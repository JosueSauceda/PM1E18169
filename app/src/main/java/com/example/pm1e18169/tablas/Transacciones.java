package com.example.pm1e18169.tablas;


public class Transacciones {

    public static final String NameDatabase = "PM1EDB";

    public static final String TbContactos = "contactos";

    public static final String id = "id";
    public static final String pais = "pais";
    public static final String nombre = "nombre";
    public static final String telefono = "telefono";
    public static final String nota = "nota";

    public static final String CTBcontactos = "CREATE TABLE contactos (id INTEGER PRIMARY KEY AUTOINCREMENT,"+
        "pais TEXT, nombre TEXT, telefono TEXT, nota TEXT)";

    public static final String Getcontactos = "SELECT * FROM " + Transacciones.TbContactos;

    public static final String DPcontactos = "DROP TABLE IF EXIST contactos";


}
