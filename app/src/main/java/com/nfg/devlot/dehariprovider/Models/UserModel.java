package com.nfg.devlot.dehariprovider.Models;

/**
 * Created by hassan on 3/26/18.
 */

public class UserModel {

    private String uid;

    private String ss_id;
    private String name;
    private String email;
    private String phone_number;
    private String imagePath;


    public UserModel() {}

    public UserModel(String uid, String ss_id, String name, String email, String phone_number, String imagePath) {
        this.name = name;
        this.uid = uid;
        this.ss_id = ss_id;
        this.email = email;
        this.phone_number = phone_number;
        this.imagePath = imagePath;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSs_id() {
        return ss_id;
    }

    public void setSs_id(String ss_id) {
        this.ss_id = ss_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
