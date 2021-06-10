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
import com.example.foro.models.Topic;
import com.example.foro.models.User;

import java.util.ArrayList;

public class Post extends AppCompatActivity {

    private Button button;
    private ListView listView;
    private int idTopic;
    private ArrayList<com.example.foro.models.Post> listPost;
    private ArrayList<String> listInfPost;
    private ConnectionDB connectionDB;
    private User user;
    private com.example.foro.models.Post post;
    private ArrayList<User> listUser;
    private ArrayList<Integer> listInfoIdUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        connectionDB = new ConnectionDB(this, Constants.DB_NAME, null, 1);

        button = (Button) findViewById(R.id.buttonNewPost);
        listView = (ListView) findViewById(R.id.listViewPost);
    }

    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        idTopic = bundle.getInt("idTopic");
        consultPostList();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listInfPost);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int idPost = listPost.get(position).getIdPost();
                String message = listPost.get(position).getMenssage();
                if (isCreate(idPost)) {
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("idTopic", idTopic);
                    bundle1.putInt("idPost", idPost);
                    bundle1.putString("messagePost", message);
                    Intent intent1 = new Intent(Post.this, ViewPost.class);
                    intent1.putExtras(bundle1);
                    startActivity(intent1);
                } else {
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("idTopic", idTopic);
                    bundle2.putInt("idPost", idPost);
                    bundle2.putString("messagePost", message);
                    Intent intent2 = new Intent(Post.this, EditPost.class);
                    intent2.putExtras(bundle2);
                    startActivity(intent2);
                }
            }
        });
        if (isLogin())
            button.setEnabled(true);
        else
            button.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        if (isLogin()) {
            inflater.inflate(R.menu.menu, menu);
            return true;
        } else {
            inflater.inflate(R.menu.menu_logout, menu);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.buttonLogout:
                SQLiteDatabase database = connectionDB.getWritableDatabase();
                database.execSQL("UPDATE " + Constants.TABLE_Name_User + " SET " + Constants.CAMPO_USER_LOGIN + " = 0  WHERE " + Constants.CAMPO_ID_USER + " = " + String.valueOf(getIdUser()));
                Intent intent = new Intent(Post.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.mis_datos:
                Intent intent1 = new Intent(Post.this, PersonalInformation.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idUser", getIdUser());
                intent1.putExtras(bundle);
                startActivity(intent1);
                return true;
            case R.id.mis_puntos:
                Intent intent2 = new Intent(Post.this, Points.class);
                startActivity(intent2);
                return true;
            case R.id.login:
                Intent intent3 = new Intent(Post.this, Login.class);
                startActivity(intent3);
                return true;
            case R.id.register:
                Intent intent4 = new Intent(Post.this, RegisterUser.class);
                startActivity(intent4);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean isLogin() {
        SQLiteDatabase db = connectionDB.getReadableDatabase();
        listUser = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_Name_User + " WHERE " + Constants.CAMPO_USER_LOGIN + " =  1", null);
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
        if (listUser.size() == 0)
            return false;
        else
            return true;
    }

    public boolean isCreate(int idPost){
        SQLiteDatabase db = connectionDB.getReadableDatabase();
        listPost = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_Name_Post + " WHERE " + Constants.CAMPO_ID_USER + " = " + String.valueOf(getIdUser()) + " AND " + Constants.CAMPO_ID_Post + " = " + String.valueOf(idPost), null);
        while (cursor.moveToNext()) {
            post = new com.example.foro.models.Post();
            post.setIdPost(cursor.getInt(0));
            post.setMenssage(cursor.getString(1));
            post.setIdTopic(cursor.getInt(2));
            post.setIdUser(cursor.getInt(3));
            listPost.add(post);
        }
        if (listPost.size() != 0 && isLogin() == true)
            return false;
        else
            return true;
    }

    public void onClick(View view) {
        Intent intent = new Intent(Post.this, AddPostList.class);
        Bundle bundle = new Bundle();
        bundle.putInt("idTopicPost", idTopic);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void consultPostList() {
        SQLiteDatabase db = connectionDB.getReadableDatabase();
        com.example.foro.models.Post post = null;
        listPost = new ArrayList<com.example.foro.models.Post>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_Name_Post + " WHERE " + Constants.CAMPO_ID_Topic + " = " + String.valueOf(idTopic), null);
        while (cursor.moveToNext()) {
            post = new com.example.foro.models.Post();
            post.setIdPost(cursor.getInt(0));
            post.setMenssage(cursor.getString(1));
            post.setIdTopic(cursor.getInt(2));
            post.setIdUser(cursor.getInt(3));

            listPost.add(post);
        }
        getList();
    }

    private void getList() {
        listInfPost = new ArrayList<String>();
        for (int i = 0; i < listPost.size(); i++) {
            listInfPost.add(listPost.get(i).getMenssage());
        }
    }

    public int getIdUser() {
        int idUser = 0;
        SQLiteDatabase db = connectionDB.getReadableDatabase();
        listUser = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_Name_User + " WHERE " + Constants.CAMPO_USER_LOGIN + " =  1", null);
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
        listInfoIdUser = new ArrayList<>();
        for (int i = 0; i < listUser.size(); i++) {
            listInfoIdUser.add(listUser.get(i).getIdUsuario());
        }
        for (int j = 0; j < listInfoIdUser.size(); j++) {
            idUser = listInfoIdUser.get(j);
        }
        return idUser;
    }
}