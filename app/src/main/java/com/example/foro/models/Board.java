package com.example.foro.models;

import java.io.Serializable;

public class Board implements Serializable {

    private int idBoard;
    private String title;

    public Board() {
    }

    public Board(int idBoard, String title) {
        this.idBoard = idBoard;
        this.title = title;
    }

    public int getIdBoard() {
        return idBoard;
    }

    public void setIdBoard(int idBoard) {
        this.idBoard = idBoard;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return title;
    }
}
