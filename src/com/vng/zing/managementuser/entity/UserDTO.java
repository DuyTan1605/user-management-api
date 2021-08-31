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
    private long birthday;
    private long createTime;
    private long updateTime;
    private String password;

    public UserDTO() {
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

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDTO(int id) {
        this.id = id;
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

    public long getBirthday() {
        return birthday;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

}
