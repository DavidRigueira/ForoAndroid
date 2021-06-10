package com.example.foro.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
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

public class AddBoardList extends AppCompatActivity {

    private EditText editTextTitle;
    private Button button;
    private ConnectionDB connectionDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_board_list);

        connectionDB = new ConnectionDB(this, Constants.DB_NAME, null, 1);

        button = (Button) findViewById(R.id.buttonNewBoard);
        editTextTitle = (EditText) findViewById(R.id.editTextTitleBoard);

    }

    public void onClickAddBoard(View view) {
        registrarBoard();
        Intent intent = new Intent(AddBoardList.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void registrarBoard() {
        SQLiteDatabase db = connectionDB.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.CAMPO_TITLE_Board, editTextTitle.getText().toString());

        Long idResul = db.insert(Constants.TABLE_Name_Board, Constants.CAMPO_ID_Board, values);

        Toast.makeText(getApplicationContext(), "Id Registro Board: " + idResul, Toast.LENGTH_SHORT).show();
    }
}