package com.example.xabot.basesdatos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private EditText etDni, etTelefono , etNombre , etCiudad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDni=(EditText)findViewById(R.id.editTextDni);
        etTelefono=(EditText)findViewById(R.id.editTextTelefono);
        etNombre=(EditText)findViewById(R.id.editTextNombre);
        etCiudad=(EditText)findViewById(R.id.editTextCiudad);
    }
    public void crear(View view){
        AdminHelper admin = new AdminHelper(this,"administracion",null,1);
        SQLiteDatabase db = admin.getWritableDatabase();
        //Guardar los valores que leemos en los EditText en un registro.
        //Para ello creamos el registro
        ContentValues registro= new ContentValues();
        registro.put("dni",etDni.getText().toString());
        registro.put("numero",etTelefono.getText().toString());
        registro.put("nombre",etNombre.getText().toString());
        registro.put("ciudad",etCiudad.getText().toString());
        //Insertar a la base de datos
        db.insert("usuario",null,registro);

        //cerrar la base de datos
        db.close();

        //Poner los campos a vacio para insertar el siguiente usuario
        etDni.setText("");
        etTelefono.setText("");
        etNombre.setText("");
        etCiudad.setText("");

        Toast.makeText(this,"usuario insertado en la base de datos",Toast.LENGTH_LONG).show();
    }

    public void borrar(View view) {
        AdminHelper admin = new AdminHelper(this, "administracion", null, 1);
        SQLiteDatabase db = admin.getWritableDatabase();

        String dni = etDni.getText().toString();
        int count = db.delete("usuario", "dni=" +"'" +dni+"'", null);
        db.close();

        //Comprobaciones
        if (count == 1) {
            //Se ha borrado
            Toast.makeText(this, "Se ha eliminado un usuario", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "No se a podido borrar", Toast.LENGTH_LONG).show();
        }
        etDni.setText("");
        etTelefono.setText("");
        etNombre.setText("");
        etCiudad.setText("");
    }
        public void consultar(View view){
            AdminHelper admin = new AdminHelper(this, "administracion", null, 1);
            SQLiteDatabase db = admin.getReadableDatabase();
            String dni = etDni.getText().toString();
            Cursor c = db.rawQuery("SELECT  numero, nombre, ciudad FROM usuario WHERE dni='"+dni+"'", null);
            Log.d("consultar", "antes de mover el cursor");
            if(c.moveToFirst()){
                do{
                    String column1 = c.getString(0);
                    String column2 = c.getString(1);
                    String column3 = c.getString(2);
                    etNombre.setText(column1);
                    etCiudad.setText(column2);
                    etTelefono.setText((column3));
                }while (c.moveToNext());
            }
            c.close();
            db.close();

        }
}
