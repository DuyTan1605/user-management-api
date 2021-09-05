/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.utils;

import com.vng.zing.common.HReqParam;
import com.vng.zing.exception.InvalidParamException;
import com.vng.zing.exception.NotExistException;
import com.vng.zing.userservice.thrift.Gender;
import com.vng.zing.userservice.thrift.User;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author tanhd
 */
public class RequestUtils {

    public static Object createUserParams(HttpServletRequest req) throws JSONException, NotExistException, InvalidParamException {
        JSONObject result = new JSONObject();
        result.put("name", HReqParam.getString(req, "name"));
        result.put("username", HReqParam.getString(req, "username"));
        result.put("birthday", HReqParam.getString(req, "birthday"));
        result.put("password", HReqParam.getString(req, "password"));
        result.put("gender", HReqParam.getInt(req, "gender"));
        return result;
    }

    public static Object updateUserParams(HttpServletRequest req) throws JSONException, NotExistException, InvalidParamException {
        JSONObject result = new JSONObject();
        result.put("id", HReqParam.getInt(req, "id"));
        result.put("name", HReqParam.getString(req, "name"));
        result.put("username", HReqParam.getString(req, "username"));
        result.put("birthday", HReqParam.getString(req, "birthday"));
        result.put("password", HReqParam.getString(req, "password"));
        result.put("gender", HReqParam.getInt(req, "gender"));
        return result;
    }

    public static User parseUpdateUserParams(Object newUser) throws JSONException, NotExistException, InvalidParamException {
        JSONObject jsonUser = (JSONObject) newUser;
        User user = new User();
        user.setId(Integer.parseInt(jsonUser.getString("id")));
        user.setName(jsonUser.getString("name"));
        user.setUsername(jsonUser.getString("username"));
        user.setPassword(jsonUser.getString("password"));
        user.setBirthday(DateTimeUtils.formatDateTime(jsonUser.getString("birthday")));
        user.setGender(Gender.findByValue(Integer.parseInt(jsonUser.getString("gender"))));
        return user;
    }

    public static User parseCreateUserParams(Object newUser) throws JSONException, NotExistException, InvalidParamException {
        JSONObject jsonUser = (JSONObject) newUser;
        User user = new User();
        user.setName(jsonUser.getString("name"));
        user.setUsername(jsonUser.getString("username"));
        user.setPassword(jsonUser.getString("password"));
        user.setBirthday(DateTimeUtils.formatDateTime(jsonUser.getString("birthday")));
        user.setGender(Gender.findByValue(Integer.parseInt(jsonUser.getString("gender"))));
        return user;
    }
}
