package com.example.foro.models;

import java.io.Serializable;
import java.util.Date;

public class Topic implements Serializable {

    private int idTopic;
    private String subject;
    private String lastUpdate;
    private int views;
    private int idBoard;
    private int idCategory;
    private int idUser;

    public Topic() {
    }

    public Topic(int idTopic, String subject, String lastUpdate, int views, int idBoard, int idCategory, int idUser) {
        this.idTopic = idTopic;
        this.subject = subject;
        this.lastUpdate = lastUpdate;
        this.views = views;
        this.idBoard = idBoard;
        this.idCategory = idCategory;
        this.idUser = idUser;
    }

    public int getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(int idTopic) {
        this.idTopic = idTopic;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getIdBoard() {
        return idBoard;
    }

    public void setIdBoard(int idBoard) {
        this.idBoard = idBoard;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return subject;
    }
}
