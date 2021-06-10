package com.example.foro.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.foro.R;
import com.example.foro.SQLite.ConnectionDB;
import com.example.foro.constans.Constants;
import com.example.foro.models.Board;
import com.example.foro.models.Category;
import com.example.foro.models.User;

import java.util.ArrayList;
import java.util.Calendar;

public class AddTopicList extends AppCompatActivity {

    private Button button;
    private EditText editTextSubject;
    private EditText editTextDateAt;
    private Spinner spinnerCategory;
    private ConnectionDB connectionDB;
    private ArrayList<String> listInfCategory;
    private ArrayList<Category> listCategory;
    private ArrayList<User> listUser;
    private int idBoard;
    private int idCategoy;
    private int idUser = 0;
    private boolean escogio = false;
    private int mYearInit;
    private int mMonthInit;
    private int mDayInit;
    private int sDayInit;
    private int sMonthInit;
    private int sYearInit;
    private Calendar calendar = Calendar.getInstance();
    private static final int DATE_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_topic_list);

        connectionDB = new ConnectionDB(this, Constants.DB_NAME, null, 1);

        button = (Button) findViewById(R.id.buttonSendTopic);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextDateAt = (EditText) findViewById(R.id.editTextDateCreateAt);
        spinnerCategory = (Spinner) findViewById(R.id.spinnerCategory);
        sDayInit = calendar.get(Calendar.DAY_OF_MONTH);
        sMonthInit = calendar.get(Calendar.MONTH);
        sYearInit = calendar.get(Calendar.YEAR);

        editTextDateAt.setOnClickListener(view -> {
            showDialog(DATE_ID);
        });
    }

    public void onResume() {
        super.onResume();
        Bundle bundle = getIntent().getExtras();
        idBoard = bundle.getInt("idBoardTopic");

        consultCategory();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, listInfCategory);

        spinnerCategory.setAdapter(adapter);

        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    idCategoy = listCategory.get(position - 1).getIdCategory();
                    escogio = true;
                } else {
                    escogio = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onClickAddTopic(View view) {
        if (escogio == true) {
            if (registrarTopic()) {
                Intent intent = new Intent(AddTopicList.this, Topic.class);
                Bundle bundle = new Bundle();
                bundle.putInt("idBoard", idBoard);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Debe seleccionar una categoria", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean registrarTopic() {
        SQLiteDatabase db = connectionDB.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Constants.CAMPO_SUBJECT, editTextSubject.getText().toString());
        values.put(Constants.CAMPO_LAST_UPDATE, editTextDateAt.getText().toString());
        values.put(Constants.CAMPO_ID_Board, idBoard);
        values.put(Constants.CAMPO_ID_Category, idCategoy);
        values.put(Constants.CAMPO_ID_USER, getUser());

        Long idResult = db.insert(Constants.TABLE_Name_Topic, Constants.CAMPO_ID_Topic, values);

        if (idResult != null) {
            return true;
        } else {
            return false;
        }
    }

    private void consultCategory() {
        SQLiteDatabase db = connectionDB.getReadableDatabase();
        Category category = null;
        listCategory = new ArrayList<Category>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Constants.TABLE_Name_Category, null);
        while (cursor.moveToNext()) {
            category = new Category();
            category.setIdCategory(cursor.getInt(0));
            category.setCategory(cursor.getString(1));

            listCategory.add(category);
        }
        getListCategory();
    }

    private void getListCategory() {
        listInfCategory = new ArrayList<String>();
        listInfCategory.add("Seleccione");
        for (int i = 0; i < listCategory.size(); i++) {
            listInfCategory.add(listCategory.get(i).getCategory());
        }
    }

    private void colocarFecha() {
        editTextDateAt.setText(mDayInit + "-" + (mMonthInit + 1) + "-" + mYearInit + " ");
    }

    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYearInit = year;
            mMonthInit = month;
            mDayInit = dayOfMonth;
            colocarFecha();
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, mDateSetListener, sYearInit, sMonthInit, sDayInit);
        }
        return null;
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
        int logincheck = 0;
        for (int i = 0; i < listUser.size(); i++) {
            if (listUser.get(i).getLogeado() == 1) {
                logincheck = listUser.get(i).getIdUsuario();
            }
        }
        return logincheck;
    }
}