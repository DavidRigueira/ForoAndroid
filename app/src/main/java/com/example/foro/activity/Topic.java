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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.foro.R;
import com.example.foro.SQLite.ConnectionDB;
import com.example.foro.constans.Constants;
import com.example.foro.login.Login;
import com.example.foro.login.PersonalInformation;
import com.example.foro.login.Points;
import com.example.foro.login.RegisterUser;
import com.example.foro.models.User;

import java.util.ArrayList;

public class Topic extends AppCompatActivity {

    private Button button;
    private int idBoard;
    private ListView listViewTopic;
    private ArrayList<String> listInfoTopic;
    private ArrayList<com.example.foro.models.Topic> listTopic;
    private ConnectionDB connectionDB;
    private User user;
    private ArrayList<User> listUser;
    private ArrayList<Integer> listInfoIdUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        connectionDB = new ConnectionDB(getApplicationContext(), Constants.DB_NAME, null, 1);

        button = (Button) findViewById(R.id.buttonNewTopic);
        listViewTopic = (ListView) findViewById(R.id.listViewTopic);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle idEnviadoBoard = getIntent().getExtras();
        idBoard = idEnviadoBoard.getInt("idBoard");
        consultListTopic();
            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listInfoTopic);

        listViewTopic.setAdapter(adapter);

        listViewTopic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idTopic = listTopic.get(position).getIdTopic();
                Toast.makeText(getApplicationContext(), "El id del Topic es: " + idTopic, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Topic.this, Post.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idTopic", idTopic);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        if (isLogin())
            button.setEnabled(true);
        else
            button.setEnabled(false);
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
                Intent intent = new Intent(Topic.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.mis_datos:
                Intent intent1 = new Intent(Topic.this, PersonalInformation.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idUser", getIdUser());
                intent1.putExtras(bundle);
                startActivity(intent1);
                return true;
            case R.id.mis_puntos:
                Intent intent2 = new Intent(Topic.this, Points.class);
                startActivity(intent2);
                return true;
            case R.id.login:
                Intent intent3 = new Intent(Topic.this, Login.class);
                startActivity(intent3);
                return true;
            case R.id.register:
                Intent intent4 = new Intent(Topic.this, RegisterUser.class);
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

    public void onClick(View view){
        Intent intent = new Intent(Topic.this, AddTopicList.class);
        Bundle bundle = new Bundle();
        bundle.putInt("idBoardTopic", idBoard);
        Toast.makeText(getApplicationContext(), "EL idBoard enviado a AddTopicList es: " + idBoard, Toast.LENGTH_SHORT).show();
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void consultListTopic(){
        SQLiteDatabase db = connectionDB.getReadableDatabase();
        com.example.foro.models.Topic topic = null;
        listTopic = new ArrayList<com.example.foro.models.Topic>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_Name_Topic + " WHERE " + Constants.CAMPO_ID_Board + " = " + String.valueOf(idBoard) , null);

        while (cursor.moveToNext()){
            topic = new com.example.foro.models.Topic();
            topic.setIdTopic(cursor.getInt(0));
            topic.setSubject(cursor.getString(1));
            topic.setLastUpdate(cursor.getString(2));
            topic.setIdBoard(cursor.getInt(3));
            topic.setIdCategory(cursor.getInt(4));
            topic.setIdUser(cursor.getInt(5));

            listTopic.add(topic);
        }
        getList();
    }

    private void getList(){
        listInfoTopic = new ArrayList<String>();
        for (int i = 0; i < listTopic.size(); i++){
            listInfoTopic.add(listTopic.get(i).getSubject());
        }
    }
}