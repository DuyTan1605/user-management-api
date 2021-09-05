/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.entity;

import com.vng.zing.userservice.thrift.Gender;
import org.json.JSONException;
import org.json.JSONObject;

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

    public UserDTO() {
    }

    public UserDTO(int id, String name, String userName, Gender gender, String birthday, String createTime, String updateTime) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.gender = gender;
        this.birthday = birthday;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public JSONObject getUserResponse() throws JSONException {
        JSONObject result = new JSONObject();
        result.put("id", this.id);
        result.put("name", this.name);
        result.put("username", this.userName);
        result.put("birthday", this.birthday);
        result.put("createtime", this.createTime);
        result.put("updatetime", this.updateTime);
        return result;
    }
}
