package com.example.foro.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foro.R;
import com.example.foro.SQLite.ConnectionDB;
import com.example.foro.constans.Constants;
import com.example.foro.models.User;

import java.util.ArrayList;

public class AddPostList extends AppCompatActivity {

    private Button button;
    private EditText editTextPost;
    private ConnectionDB connectionDB;
    private int idTopic;
    private ArrayList<User> listUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post_list);

        connectionDB = new ConnectionDB(this, Constants.DB_NAME, null, 1);

        button = (Button) findViewById(R.id.buttonPublicPost);
        editTextPost = (EditText) findViewById(R.id.editTextMultiLinePost);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        idTopic = bundle.getInt("idTopicPost");
    }

    public void onClickAddPost(View view) {
        if (registerPost()) {
            Intent intent = new Intent(AddPostList.this, Post.class);
            Bundle bundle = new Bundle();
            bundle.putInt("idTopic", idTopic);
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }

    private boolean registerPost() {
        SQLiteDatabase db = connectionDB.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constants.CAMPO_MESSAGE, editTextPost.getText().toString());
        values.put(Constants.CAMPO_ID_Topic, idTopic);
        values.put(Constants.CAMPO_ID_USER, getUser());

        Long idResult = db.insert(Constants.TABLE_Name_Post, Constants.CAMPO_ID_Post, values);

        if (idResult != null) {
            return true;
        } else {
            return false;
        }
    }

    public int getUser() {
        SQLiteDatabase db = connectionDB.getReadableDatabase();
        com.example.foro.models.Post post = null;
        listUser = new ArrayList<>();
        User user = null;


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
        int loginCheck = consultInfLogin();
        return loginCheck;
    }

    public int consultInfLogin() {
        ;
        int logincheck = 0;
        for (int i = 0; i < listUser.size(); i++) {
            if (listUser.get(i).getLogeado() == 1) {
                logincheck = listUser.get(i).getIdUsuario();
            }
        }
        return logincheck;
    }
}