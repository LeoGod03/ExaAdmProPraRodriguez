package com.example.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    // variables para mapear los controles del .xml
    private EditText etMatricula, etNombre, etCarrera, etCal1, etCal2;
    private TextView tvPromedio;
    private Button btnAlta, btnConsultar, btnLimpiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etMatricula = findViewById(R.id.etMatricula);
        etNombre = findViewById(R.id.etNombre);
        etCarrera = findViewById(R.id.etCarrera);
        etCal1 = findViewById(R.id.etCal1);
        etCal2 = findViewById(R.id.etCal2);
        tvPromedio = findViewById(R.id.tvPromedio);
        btnAlta = findViewById(R.id.btnAlta);
        btnConsultar = findViewById(R.id.btnConsultar);
        btnLimpiar = findViewById(R.id.btnLimpiar);
    }

    public void darAlta(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();//objetos de base de datos se reescribible

        //para guardar valor de variables del formulario
        int matricula = Integer.parseInt(etMatricula.getText().toString());
        String nombre = etNombre.getText().toString();
        String carrera = etCarrera.getText().toString();
        double cal1 = Double.parseDouble(etCal1.getText().toString());
        double cal2 = Double.parseDouble(etCal2.getText().toString());
        double promedio = calcularPromedio(cal1, cal2);
        ContentValues registro = new ContentValues();
        tvPromedio.setText("Promedio: " + promedio);

        //se crea contenedor para almacenar los valores
        //se integran variables de java con valores y campos de la tabla Alumnos
        registro.put("matricula", matricula);
        registro.put("nombre", nombre);
        registro.put("carrera", carrera);
        registro.put("cal1", cal1);
        registro.put("cal2", cal2);
        registro.put("promedio", promedio);
        //se inserta registro en tabla articulo
        bd.insert("Alumnos", null, registro);
        // Se cierra BD
        bd.close();

        //Imprimir datos de registro exitoso en ventana emergente tipo TOAST
        Toast.makeText(this, "Exito al ingresar el registro\n\nMatricula:"+matricula+"\nNombre:"+nombre+"\nCarrera:"+carrera+
                      "Cal1:"+cal1 + "\nCal2:" + cal2+ "\nPromedio:" + promedio,Toast.LENGTH_LONG).show();
    }

    public void hacerConsulta(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();//objetos de base de datos se reescribible

        //se asigna una variable para busqueda y consulta por campo distintivo
        int matriculaConsulta = Integer.parseInt(etMatricula.getText().toString());
        //Cursor recorre los campos d euna tabla hasta encontralo por campo distintivo
        Cursor fila = bd.rawQuery("SELECT nombre, carrera, cal1, cal2, promedio from Alumnos where matricula="+matriculaConsulta,null);

        if(fila.moveToFirst()){//si condicion es verdadera, es decir, encontro un campo y sus datos
            etNombre.setText(fila.getString(0));
            etCarrera.setText(fila.getString(1));
            etCal1.setText(fila.getString(2));
            etCal2.setText(fila.getString(3));
            tvPromedio.setText("Promedio: " + fila.getString(4));
            Toast.makeText(this,"Registro encontrado de forma EXITOSA",Toast.LENGTH_LONG).show();
        }else{//condicion falsa si no encontro un registro
            Toast.makeText(this,"No existe alumno con esa matricula\nVerifica",Toast.LENGTH_LONG).show();
            bd.close();
        }
    }
    private double calcularPromedio(double cal1, double cal2)  {
        return (cal1 + cal2) / 2;
    }

    public void limpiar(View view){
        etMatricula.setText(null);
        etNombre.setText(null);
        etCarrera.setText(null);
        etCal1.setText(null);
        etCal2.setText(null);
        tvPromedio.setText("Promedio: ");
    }
}