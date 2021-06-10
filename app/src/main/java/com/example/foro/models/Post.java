package com.example.foro.models;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {

    private int idPost;
    private String menssage;
    private String createAt;
    private String updateAt;
    private int idTopic;
    private int idUser;

    public Post() {
    }

    public Post(int idPost, String menssage, String createAt, String updateAt, int idTopic, int idUser) {
        this.idPost = idPost;
        this.menssage = menssage;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.idTopic = idTopic;
        this.idUser = idUser;
    }

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
        this.idPost = idPost;
    }

    public String getMenssage() {
        return menssage;
    }

    public void setMenssage(String menssage) {
        this.menssage = menssage;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public int getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(int idTopic) {
        this.idTopic = idTopic;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @Override
    public String toString() {
        return menssage;
    }
}
