package com.example.foro.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foro.R;
import com.example.foro.SQLite.ConnectionDB;
import com.example.foro.constans.Constants;

public class RegisterUser extends AppCompatActivity {

    private EditText editTextUserName;
    private EditText editTextUserLasName;
    private EditText editTextUserEmail;
    private EditText editTextPassword1;
    private EditText editTextPassword2;
    private ConnectionDB connectionDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        connectionDB = new ConnectionDB(this, Constants.DB_NAME, null, 1);

        editTextUserName = (EditText) findViewById(R.id.editTextTextNameUser);
        editTextUserLasName = (EditText) findViewById(R.id.editTextLastNameUser);
        editTextUserEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword1 = (EditText) findViewById(R.id.editTextPassword1);
        editTextPassword2 = (EditText) findViewById(R.id.editTextPassword2);

    }

    public void onClick(View view){
        if(registerUser(editTextUserName, editTextUserLasName, editTextUserEmail, editTextPassword1, editTextPassword2)) {
            Intent intent = new Intent(RegisterUser.this, Login.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean registerUser(EditText editTextUserName, EditText editTextUserLasName, EditText editTextUserEmail, EditText editTextPassword1, EditText editTextPassword2){
        SQLiteDatabase db = connectionDB.getWritableDatabase();

        if (editTextUserName.getText().toString().isEmpty()){
            Toast.makeText(this, "El campo nombre vacion", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            if (editTextUserLasName.getText().toString().isEmpty()){
                Toast.makeText(this, "El campo apellidos esta vacio", Toast.LENGTH_SHORT).show();
                return false;
            }else {
                if (editTextUserEmail.getText().toString().isEmpty()){
                    Toast.makeText(this, "El campo email esta vacio", Toast.LENGTH_SHORT).show();
                    return false;
                }else {
                    if (editTextPassword1.getText().toString().isEmpty()){
                        Toast.makeText(this, "El campo password esta vacio", Toast.LENGTH_SHORT).show();
                        return false;
                    }else {
                        if (editTextPassword2.getText().toString().isEmpty()){
                            Toast.makeText(this, "El campo password repetido esta vacio", Toast.LENGTH_SHORT).show();
                            return false;
                        }
                    }
                }
            }
        }

        ContentValues values = new ContentValues();
        values.put(Constants.CAMPO_NAME_USER, editTextUserName.getText().toString());
        values.put(Constants.CAMPO_LAST_NAME_USER, editTextUserLasName.getText().toString());
        values.put(Constants.CAMPO_EMAIL_USER, editTextUserEmail.getText().toString());
        values.put(Constants.CAMPO_PASSWORD_USER, editTextPassword1.getText().toString());
        values.put(Constants.CAMPO_USER_LOGIN, 0);

        if (editTextPassword1.getText().toString().equals(editTextPassword2.getText().toString())){
            db.insert(Constants.TABLE_Name_User, Constants.CAMPO_ID_USER, values);
            Toast.makeText(getApplicationContext(), "El usuario se registro correctamente", Toast.LENGTH_SHORT).show();
            return true;
        }else {
            Toast.makeText(this, "Error al registrar el user repite password no coiciden", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}