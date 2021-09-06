/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.entity;

import com.vng.zing.userservice.thrift.Gender;

/**
 *
 * @author tanhd
 */
public class UserDTO {

    private int id;
    private String name;
    private String userName;
    private Gender gender;
    private String birthday;
    private String createTime;
    private String updateTime;
    private String password;

    public UserDTO() {
    }

    public UserDTO(int id, String name, String userName, Gender gender, String birthday, String createTime, String updateTime, String password) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.gender = gender;
        this.birthday = birthday;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public Gender getGender() {
        return gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
