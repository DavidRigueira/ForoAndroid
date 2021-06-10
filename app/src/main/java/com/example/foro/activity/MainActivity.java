package com.example.foro.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
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
import com.example.foro.login.Login;
import com.example.foro.login.PersonalInformation;
import com.example.foro.login.Points;
import com.example.foro.login.RegisterUser;
import com.example.foro.models.Board;
import com.example.foro.constans.Constants;
import com.example.foro.models.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listViewBoard;
    private ArrayList<String> listInfo;
    private ArrayList<Board> listBoard;
    private ArrayList<User> listUser;
    private ArrayList<Integer> listInfoIdUser;
    private Button mButtonNewBoard;
    private Button mButtonNewCategory;
    private ConnectionDB connectionDB;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectionDB = new ConnectionDB(getApplicationContext(), Constants.DB_NAME, null, 1);

        mButtonNewBoard = (Button) findViewById(R.id.buttonNewBoard);
        mButtonNewCategory = (Button) findViewById(R.id.buttonNewCategory);
        listViewBoard = (ListView) findViewById(R.id.listViewBoard);
    }

    @Override
    protected void onStart(){
        super.onStart();
        consultListBoard();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listInfo);

        listViewBoard.setAdapter(adapter);

        listViewBoard.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idBoard = listBoard.get(position).getIdBoard();
                Bundle idUserBundle = getIntent().getExtras();
                Toast.makeText(getApplicationContext(),"ID BOARD: " + idBoard, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Topic.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idBoard", idBoard);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        if (isLogin()) {
            mButtonNewBoard.setEnabled(true);
            mButtonNewCategory.setEnabled(true);
        }else {
            mButtonNewBoard.setEnabled(false);
            mButtonNewCategory.setEnabled(false);
        }
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
                finish();
                startActivity(getIntent());
                return true;
            case R.id.mis_datos:
                Intent intent1 = new Intent(MainActivity.this, PersonalInformation.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idUser", getIdUser());
                intent1.putExtras(bundle);
                startActivity(intent1);
                return true;
            case R.id.mis_puntos:
                Intent intent2 = new Intent(MainActivity.this, Points.class);
                startActivity(intent2);
                return true;
            case R.id.login:
                Intent intent3 = new Intent(MainActivity.this, Login.class);
                startActivity(intent3);
                return true;
            case R.id.register:
                Intent intent4 = new Intent(MainActivity.this, RegisterUser.class);
                startActivity(intent4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onClick(View view){
        Intent intent = new Intent(MainActivity.this, AddBoardList.class);
        startActivity(intent);
    }

    public void addCategory(View view){
        Intent intent = new Intent(MainActivity.this, AddCategory.class);
        startActivity(intent);
    }

    private void consultListBoard(){
        SQLiteDatabase db = connectionDB.getReadableDatabase();
        Board board = null;
        listBoard = new ArrayList<Board>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_Name_Board, null);

        while (cursor.moveToNext()){
            board = new Board();
            board.setIdBoard(cursor.getInt(0));
            board.setTitle(cursor.getString(1));

            listBoard.add(board);
        }

        getList();
    }

    private void getList(){
        listInfo = new ArrayList<String>();

        for (int i = 0; i < listBoard.size(); i++){
            listInfo.add(listBoard.get(i).getTitle());
        }
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
}