package com.example.foro.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foro.R;
import com.example.foro.SQLite.ConnectionDB;
import com.example.foro.activity.MainActivity;
import com.example.foro.constans.Constants;
import com.example.foro.models.User;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private static int idUserToken;
    private EditText editTextUser;
    private EditText editTextPassword;
    private Button buttonLogin;
    private Button buttonRegister;
    private ConnectionDB connectionDB;
    private ArrayList<User> listUser;
    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        connectionDB = new ConnectionDB(getApplicationContext(), Constants.DB_NAME, null, 1);

        editTextUser = (EditText) findViewById(R.id.editTextUser);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLoginSesion);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
    }

    public void onClickLogin(View view) {
        if (userCheck()) {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }
    }

    private boolean userCheck() {
        SQLiteDatabase db = connectionDB.getReadableDatabase();
        User user = null;
        listUser = new ArrayList<>();
        int idRecogido = 0;

        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_Name_User, null);
        while (cursor.moveToNext()) {
            user = new User();
            user.setIdUsuario(cursor.getInt(0));
            user.setNombreUsuario(cursor.getString(1));
            user.setApellidosUsuario(cursor.getString(2));
            user.setEmailUsuario(cursor.getString(3));
            user.setPasswordUsuario(cursor.getString(4));
            user.setLogedo(cursor.getInt(5));
            listUser.add(user);
        }

        for (int i = 0; i < listUser.size(); i++) {
            if (editTextUser.getText().toString().isEmpty()) {
                Toast.makeText(this, "Campo Email vacio", Toast.LENGTH_SHORT).show();
                return false;
            } else {
                if (editTextPassword.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Campo Password vacio", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    email = editTextUser.getText().toString();
                    password = editTextPassword.getText().toString();

                    if (listUser.get(i).getEmailUsuario().equalsIgnoreCase(email) && listUser.get(i).getPasswordUsuario().equals(password)) {
                        idRecogido = listUser.get(i).getIdUsuario();
                        SQLiteDatabase database = connectionDB.getWritableDatabase();
                        database.execSQL("UPDATE " + Constants.TABLE_Name_User + " SET " + Constants.CAMPO_USER_LOGIN + " = 1  WHERE " + Constants.CAMPO_ID_USER + " = " + String.valueOf(idRecogido));
                        sendID(idRecogido);
                        return true;
                    }
                }
            }
        }
        Toast.makeText(this, "Error en email o password", Toast.LENGTH_SHORT).show();
        return false;
    }

    public static void sendID(int idRecogido) {
        idUserToken = idRecogido;
    }

    ;

    public void onClickRegister(View view) {
        Intent intent = new Intent(Login.this, RegisterUser.class);
        startActivity(intent);
    }
}