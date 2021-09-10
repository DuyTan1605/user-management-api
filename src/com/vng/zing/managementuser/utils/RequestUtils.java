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

/**
 *
 * @author tanhd
 */
public class RequestUtils {

    public static User createUserParams(HttpServletRequest req) throws JSONException, NotExistException, InvalidParamException {
        User user = new User();
        user.setName(HReqParam.getString(req, "name"));
        user.setUsername(HReqParam.getString(req, "username"));
        user.setBirthday(DateTimeUtils.formatDateTime(HReqParam.getString(req, "birthday")));
        user.setPassword(HReqParam.getString(req, "password"));
        user.setGender(Gender.findByValue(HReqParam.getInt(req, "gender")));
        return user;
    }

    public static User updateUserParams(HttpServletRequest req) throws JSONException, NotExistException, InvalidParamException {
        User user = new User();
        user.setId(HReqParam.getInt(req, "id"));
        user.setName(HReqParam.getString(req, "name"));
        user.setUsername(HReqParam.getString(req, "username"));
        user.setBirthday(DateTimeUtils.formatDateTime(HReqParam.getString(req, "birthday")));
        user.setGender(Gender.findByValue(HReqParam.getInt(req, "gender")));
        return user;
    }
}
