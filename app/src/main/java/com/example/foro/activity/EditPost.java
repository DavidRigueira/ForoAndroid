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

public class EditPost extends AppCompatActivity {

    private EditText editTextPostMultiline;
    private Button buttonEditPost;
    private String menssageEditPost;
    private int idEditPost;
    private int idTopic;
    private ConnectionDB connectionDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);

        connectionDB = new ConnectionDB(this, Constants.DB_NAME, null, 1);

        editTextPostMultiline = (EditText) findViewById(R.id.editTextMultiLineEditPost);
        buttonEditPost = (Button) findViewById(R.id.buttonEditPost);

    }

    public void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        menssageEditPost = bundle.getString("messagePost");
        idEditPost = bundle.getInt("idPost");
        idTopic = bundle.getInt("idTopic");

        editTextPostMultiline.setText(menssageEditPost);
    }

    private void upgradePost() {
        SQLiteDatabase db = connectionDB.getWritableDatabase();
        String mensaje = editTextPostMultiline.getText().toString();
        ContentValues values = new ContentValues();
        values.put(Constants.CAMPO_MESSAGE, mensaje);
        db.update(Constants.TABLE_Name_Post, values, Constants.CAMPO_ID_Post + " = " + idEditPost, null);
    }

    public void onClick(View view) {
        upgradePost();
        Intent intent = new Intent(EditPost.this, Post.class);
        Bundle bundle = new Bundle();
        bundle.putInt("idTopic", idTopic);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }
}