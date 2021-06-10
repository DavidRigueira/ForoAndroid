package com.example.foro.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.foro.R;
import com.example.foro.SQLite.ConnectionDB;
import com.example.foro.constans.Constants;
import com.example.foro.login.Login;
import com.example.foro.login.PersonalInformation;
import com.example.foro.login.Points;
import com.example.foro.login.RegisterUser;
import com.example.foro.models.User;

import java.util.ArrayList;

public class ViewPost extends AppCompatActivity {

    private TextView textViewPost;
    private ConnectionDB connectionDB;
    private String menssageEditPost;
    private int idEditPost;
    private int idTopic;
    private User user;
    private ArrayList<User> listUser;
    private ArrayList<Integer> listInfoIdUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        connectionDB = new ConnectionDB(getApplicationContext(), Constants.DB_NAME, null, 1);

        textViewPost = (TextView) findViewById(R.id.textViewPost);
    }

    @Override
    public void onResume(){
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        menssageEditPost = bundle.getString("messagePost");
        idEditPost = bundle.getInt("idPost");
        idTopic = bundle.getInt("idTopic");

        textViewPost.setText(menssageEditPost);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        if (isLogin()) {
            inflater.inflate(R.menu.menu, menu);
            return true;
        }else {
            inflater.inflate(R.menu.menu_logout, menu);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.buttonLogout:
                SQLiteDatabase database = connectionDB.getWritableDatabase();
                database.execSQL("UPDATE " + Constants.TABLE_Name_User + " SET " + Constants.CAMPO_USER_LOGIN + " = 0  WHERE " + Constants.CAMPO_ID_USER + " = " + String.valueOf(getIdUser()));
                Intent intent = new Intent(ViewPost.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.mis_datos:
                Intent intent1 = new Intent(ViewPost.this, PersonalInformation.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idUser", getIdUser());
                intent1.putExtras(bundle);
                startActivity(intent1);
                return true;
            case R.id.mis_puntos:
                Intent intent2 = new Intent(ViewPost.this, Points.class);
                startActivity(intent2);
                return true;
            case R.id.login:
                Intent intent3 = new Intent(ViewPost.this, Login.class);
                startActivity(intent3);
                return true;
            case R.id.register:
                Intent intent4 = new Intent(ViewPost.this, RegisterUser.class);
                startActivity(intent4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean isLogin(){
        SQLiteDatabase db = connectionDB.getReadableDatabase();
        listUser = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_Name_User + " WHERE " + Constants.CAMPO_USER_LOGIN + " =  1", null);
        while (cursor.moveToNext()){
            user = new User();
            user.setIdUsuario(cursor.getInt(0));
            user.setNombreUsuario(cursor.getString(1));
            user.setApellidosUsuario(cursor.getString(2));
            user.setEmailUsuario(cursor.getString(3));
            user.setPasswordUsuario(cursor.getString(4));
            user.setLogedo(cursor.getInt(5));
            listUser.add(user);
        }
        if (listUser.size() == 0)
            return false;
        else
            return true;
    }

    public int getIdUser(){
        int idUser = 0;
        SQLiteDatabase db = connectionDB.getReadableDatabase();
        listUser = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_Name_User + " WHERE " + Constants.CAMPO_USER_LOGIN + " =  1", null);
        while (cursor.moveToNext()){
            user = new User();
            user.setIdUsuario(cursor.getInt(0));
            user.setNombreUsuario(cursor.getString(1));
            user.setApellidosUsuario(cursor.getString(2));
            user.setEmailUsuario(cursor.getString(3));
            user.setPasswordUsuario(cursor.getString(4));
            user.setLogedo(cursor.getInt(5));
            listUser.add(user);
        }
        listInfoIdUser = new ArrayList<>();
        for (int i = 0; i < listUser.size(); i++){
            listInfoIdUser.add(listUser.get(i).getIdUsuario());
        }
        for (int j = 0; j < listInfoIdUser.size(); j++){
            idUser = listInfoIdUser.get(j);
        }
        return idUser;
    }
}
