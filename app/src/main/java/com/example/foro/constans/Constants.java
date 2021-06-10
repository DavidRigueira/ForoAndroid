package com.example.foro.constans;

public class Constants {

    // Constates nombre db
    public static final String DB_NAME = "BoardBD";

    // Constantes campos tabla Board
    public static final String TABLE_Name_Board = "Board";
    public static final String CAMPO_ID_Board = "idBoard";
    public static final String CAMPO_TITLE_Board = "titleBoard";

    // Constantes campos tabla Category
    public static final String TABLE_Name_Category = "Category";
    public static final String CAMPO_ID_Category = "idCategory";
    public static final String CAMPO_TITLE_Category = "titleCategory";

    // Constantes campos tabla Topic
    public static final String TABLE_Name_Topic = "Topic";
    public static final String CAMPO_ID_Topic = "idTopic";
    public static final String CAMPO_SUBJECT = "subjectTopic";
    public static final String CAMPO_LAST_UPDATE = "lastUpdateTopic";

    // Constantes campos tabla Post
    public static final String TABLE_Name_Post = "Post";
    public static final String CAMPO_ID_Post = "idPost";
    public static final String CAMPO_MESSAGE = "messagePost";

    // Constantes campos tabla User
    public static final String TABLE_Name_User = "User";
    public static final String CAMPO_ID_USER = "idUser";
    public static final String CAMPO_NAME_USER = "nameUser";
    public static final String CAMPO_LAST_NAME_USER= "lastNameUser";
    public static final String CAMPO_EMAIL_USER = "emailUser";
    public static final String CAMPO_PASSWORD_USER = "passwordUser";
    public static final String CAMPO_USER_LOGIN = "loginUser";


    public static final String CREATE_TABLE_BOARD = "CREATE TABLE "+ TABLE_Name_Board +" ("+ CAMPO_ID_Board +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ CAMPO_TITLE_Board +" TEXT)";
    public static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_Name_Category + " (" + CAMPO_ID_Category + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CAMPO_TITLE_Category + " TEXT)";
    public static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_Name_User + " ("+ CAMPO_ID_USER + " INTEGER PRIMARY KEY AUTOINCREMENT, " + CAMPO_NAME_USER + " TEXT, " + CAMPO_LAST_NAME_USER + " TEXT, " + CAMPO_EMAIL_USER + " TEXT, " + CAMPO_PASSWORD_USER + " TEXT, " + CAMPO_USER_LOGIN + " INTEGER)";
    public static final String CREATE_TABLE_TOPIC = "CREATE TABLE "+ TABLE_Name_Topic +" ("+ CAMPO_ID_Topic +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ CAMPO_SUBJECT +" TEXT, " + CAMPO_LAST_UPDATE + " TEXT, " + CAMPO_ID_Board + " INTEGER, "+ CAMPO_ID_Category+" INTEGER, "+ CAMPO_ID_USER+" INTEGER)";
    public static final String CREATE_TABLE_POST = "CREATE TABLE "+ TABLE_Name_Post +" ("+ CAMPO_ID_Post +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ CAMPO_MESSAGE +" TEXT, " + CAMPO_ID_Topic+" INTEGER, "+CAMPO_ID_USER+" INTEGER)";

}
