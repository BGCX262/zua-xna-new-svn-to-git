package com.m2team.xna.model;

/**
 * Created by Hoang Minh on 2/6/2015.
 */
public class Author {
    private String username;
    private String fullName;
    private int singlePhotoCount;
    private int choosePhotoCount;
    private String dateRegister;
    private int commentCount;

    public Author(String username, String fullName, int singlePhotoCount, int choosePhotoCount, String dateRegister, int commentCount) {
        this.username = username;
        this.fullName = fullName;
        this.singlePhotoCount = singlePhotoCount;
        this.choosePhotoCount = choosePhotoCount;
        this.dateRegister = dateRegister;
        this.commentCount = commentCount;
    }

    public Author() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getSinglePhotoCount() {
        return singlePhotoCount;
    }

    public void setSinglePhotoCount(int singlePhotoCount) {
        this.singlePhotoCount = singlePhotoCount;
    }

    public int getChoosePhotoCount() {
        return choosePhotoCount;
    }

    public void setChoosePhotoCount(int choosePhotoCount) {
        this.choosePhotoCount = choosePhotoCount;
    }

    public String getDateRegister() {
        return dateRegister;
    }

    public void setDateRegister(String dateRegister) {
        this.dateRegister = dateRegister;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
