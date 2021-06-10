package com.example.foro.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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

public class PersonalInformation extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextLastName;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordReply;
    private Button buttonChangeInformation;
    private ConnectionDB connectionDB;
    private ArrayList<User> listUser;

    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_information);

        connectionDB = new ConnectionDB(getApplicationContext(), Constants.DB_NAME, null, 1);

        editTextName = (EditText) findViewById(R.id.editTextNamePersonalInformation);
        editTextLastName = (EditText) findViewById(R.id.editTextLastNamePersonalInformation);
        editTextEmail = (EditText) findViewById(R.id.editTextEmailPersonalInformation);
        editTextPassword = (EditText) findViewById(R.id.editTextPasswordPersonalInformation);
        editTextPasswordReply = (EditText) findViewById(R.id.editTextPasswordReplyPersonalInformation);
        buttonChangeInformation = (Button) findViewById(R.id.buttonChangePersonalInformation);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        idUser = bundle.getInt("idUser");
        checkUser();
    }

    public void onClickChangeInformation(View view){
        if (changeInformation()){
            Intent intent = new Intent(PersonalInformation.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(this, "Error al cambiar los datos", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean changeInformation(){
        String nameChange = editTextName.getText().toString();
        String lastNameChange = editTextLastName.getText().toString();
        String emailChange = editTextEmail.getText().toString();
        String passwordChange = editTextPassword.getText().toString();
        String passwordReplyChange = editTextPasswordReply.getText().toString();
        if (passwordChange.equals(passwordReplyChange)) {
            SQLiteDatabase db = connectionDB.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Constants.CAMPO_LAST_NAME_USER, lastNameChange);
            values.put(Constants.CAMPO_PASSWORD_USER, passwordChange);
            values.put(Constants.CAMPO_NAME_USER, nameChange);
            values.put(Constants.CAMPO_EMAIL_USER, emailChange);
            db.update(Constants.TABLE_Name_User, values, Constants.CAMPO_ID_USER + " = " + idUser, null);
            return true;
        }else {
            Toast.makeText(this, "Error password no son identicos", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void checkUser(){
        User user = null;
        listUser = new ArrayList<>();
        SQLiteDatabase db = connectionDB.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_Name_User + " WHERE " + Constants.CAMPO_USER_LOGIN + " = 1", null);
        while (cursor.moveToNext()){
            user = new User();
            user.setIdUsuario(cursor.getInt(0));
            user.setNombreUsuario(cursor.getString(1));
            user.setApellidosUsuario(cursor.getString(2));
            user.setEmailUsuario(cursor.getString(3));
            user.setPasswordUsuario(cursor.getString(4));

            listUser.add(user);
        }
        for (int i = 0; i < listUser.size(); i++){
            editTextName.setText(listUser.get(i).getNombreUsuario());
            editTextLastName.setText(listUser.get(i).getApellidosUsuario());
            editTextEmail.setText(listUser.get(i).getEmailUsuario());
            editTextPassword.setText(listUser.get(i).getPasswordUsuario());
            editTextPasswordReply.setText(listUser.get(i).getPasswordUsuario());
        }
    }
}