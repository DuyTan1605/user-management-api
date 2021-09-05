/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.services;

import com.vng.zing.dmp.common.exception.ZInvalidParamException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

/**
 *
 * @author tanhd
 */
public class ValidateService {

    public boolean validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        } else {
            String regex = "^[A-Za-z]\\w{5,29}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(name);
            return m.matches();
        }
    }

    public boolean validateUserName(String userName) {
        if (userName == null || userName.trim().isEmpty()) {
            return false;
        } else {
            String regex = "^[A-Za-z0-9]\\w{5,29}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(userName);
            return m.matches();
        }
    }

    public boolean validatePassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            return false;
        } else {
            String regex = "^(?=.*[0-9])"
                    + "(?=.*[a-z])(?=.*[A-Z])"
                    + "(?=.*[@#$%^&+=])"
                    + "(?=\\S+$).{8,20}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(password);
            return m.matches();
        }
    }

    public boolean validateBirthday(String birthday) {
        if (birthday == null || birthday.trim().isEmpty()) {
            return false;
        } else {
            String regex = "^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(birthday);
            return m.matches();
        }
    }

    public boolean validateGender(int gender) {
        if (gender != 0 && gender != 1) {
            return false;
        }
        return true;
    }

    public boolean validateId(int id) {
        if (id == (int) id && id > 0) {
            return true;
        }
        return false;
    }

    public void validateCreateUserParams(Object user) throws ParseException, JSONException {
        JSONObject userObject = (JSONObject) user;

        String name = userObject.getString("name");
        String userName = userObject.getString("username");
        String password = userObject.getString("password");
        String birthday = userObject.getString("birthday");
        int gender = userObject.getInt("gender");
        if (!validateName(name)) {
            throw new ZInvalidParamException("Name is not valid");
        } else if (!validateName(userName)) {
            throw new ZInvalidParamException("User name is not valid");
        } else if (!validatePassword(password)) {
            throw new ZInvalidParamException("Password is not valid");
        } else if (!validateBirthday(birthday)) {
            throw new ZInvalidParamException("Birthday is not valid");
        } else if (!validateGender(gender)) {
            throw new ZInvalidParamException("Gender is not valid");
        }
    }

    public void validateUpdateUserParams(Object user) throws ParseException, JSONException {
        validateCreateUserParams(user);
        JSONObject userObject = (JSONObject) user;
        int id = userObject.getInt("id");

        if (!validateId(id)) {
            throw new ZInvalidParamException("ID is not valid");
        }
    }
}
