package com.example.foro.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foro.R;
import com.example.foro.SQLite.ConnectionDB;
import com.example.foro.constans.Constants;
import com.example.foro.models.Category;

import java.util.ArrayList;

public class AddCategory extends AppCompatActivity {

    private Button button;
    private EditText editTextTitleCategory;
    private ConnectionDB connectionDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        connectionDB = new ConnectionDB(this, Constants.DB_NAME, null, 1);

        button = (Button) findViewById(R.id.buttonAddCategory);
        editTextTitleCategory = (EditText) findViewById(R.id.editTextAddCategory);

    }

    public void onClickAddCategory(View view) {
        registerCategory();
        Intent intent = new Intent(AddCategory.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void registerCategory() {
        SQLiteDatabase db = connectionDB.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.CAMPO_TITLE_Category, editTextTitleCategory.getText().toString());

        Long idResult = db.insert(Constants.TABLE_Name_Category, Constants.CAMPO_ID_Category, values);

        Toast.makeText(getApplicationContext(), "Id Registro Categoria: " + idResult, Toast.LENGTH_SHORT).show();
    }
}