/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.services;

import com.vng.zing.dmp.common.exception.ZInvalidParamException;
import com.vng.zing.dmp.common.exception.ZUnknownException;
import com.vng.zing.exception.InvalidParamException;
import com.vng.zing.exception.NotExistException;
import com.vng.zing.managementuser.entity.ApiResponse;
import com.vng.zing.managementuser.entity.UserDTO;
import com.vng.zing.userservice.thrift.CreateUserParams;
import com.vng.zing.userservice.thrift.CreateUserResult;
import com.vng.zing.userservice.thrift.DeleteUserParams;
import com.vng.zing.userservice.thrift.DeleteUserResult;
import com.vng.zing.userservice.thrift.DetailUserParams;
import com.vng.zing.userservice.thrift.DetailUserResult;
import com.vng.zing.userservice.thrift.UpdateUserParams;
import com.vng.zing.userservice.thrift.UpdateUserResult;
import com.vng.zing.userservice.thrift.User;
import com.vng.zing.userservice.thrift.wrapper.UserMwClient;
import com.vng.zing.managementuser.utils.DateTimeUtils;
import com.vng.zing.stats.Profiler;
import org.apache.thrift.TException;
import org.json.JSONException;
import org.json.simple.parser.ParseException;

/**
 *
 * @author tanhd
 */
public class UserService {

    private UserMwClient client = new UserMwClient("Main");
    private ValidateService validateService = new ValidateService();

    public UserService() {
    }

    public UserDTO getUser(int id) throws JSONException, TException {
        Profiler.getThreadProfiler().push(this.getClass(), "getUser");
        UserDTO myUserDTO = null;
        try {
            if (id <= 0) {
                throw new ZInvalidParamException("User ID is not valid");
            }
            DetailUserResult result = client.getUser(new DetailUserParams(id));
            if (result.getCode() != 0) {
                throw new ZUnknownException(result.getCode());
            }
            if (result.getCode() == 0 && result.getData() != null) {
                User user = result.getData();
                myUserDTO = new UserDTO();
                myUserDTO.setId(user.id);
                myUserDTO.setName(user.name);
                myUserDTO.setUserName(user.username);
                myUserDTO.setGender(user.gender);
                myUserDTO.setBirthday(DateTimeUtils.getLocalDateTime(user.birthday));
                myUserDTO.setUpdateTime(DateTimeUtils.getLocalDateTime(user.updatetime));
                myUserDTO.setCreateTime(DateTimeUtils.getLocalDateTime(user.createtime));
            }
        } finally {
            Profiler.getThreadProfiler().pop(this.getClass(), "getUser");
        }
        return myUserDTO;
    }

    public String updateUser(User newUser) throws JSONException, ParseException, NotExistException, InvalidParamException, TException {
        Profiler.getThreadProfiler().push(this.getClass(), "updateUser");
        UpdateUserResult result = new UpdateUserResult();
        try {
            validateService.validateUpdateUserParams(newUser);
            result = client.updateUser(new UpdateUserParams(newUser));
            if (result.getCode() != 0) {
                throw new ZUnknownException(result.getMessage());
            }
        } finally {
            Profiler.getThreadProfiler().pop(this.getClass(), "updateUser");
        }
        return result.getMessage();
    }

    public String createUser(User newUser) throws JSONException, ParseException, TException, NotExistException, InvalidParamException {
        Profiler.getThreadProfiler().push(this.getClass(), "createUser");
        CreateUserResult result = new CreateUserResult();
        try {
            ApiResponse apiResponse = new ApiResponse();
            validateService.validateCreateUserParams(newUser);
            result = client.createUser(new CreateUserParams(newUser));
        } finally {
            Profiler.getThreadProfiler().pop(this.getClass(), "createUser");
        }
        if (result.getCode() != 0) {
            throw new ZUnknownException(result.getMessage());
        }
        return result.getMessage();
    }

    public String deleteUser(int id) throws JSONException, TException {
        Profiler.getThreadProfiler().push(this.getClass(), "deleteUser");
        DeleteUserResult result = new DeleteUserResult();
        try {
            if (id <= 0) {
                throw new ZInvalidParamException("User ID is not valid");
            }
            result = client.deleteUser(new DeleteUserParams(id));
        } finally {
            Profiler.getThreadProfiler().pop(this.getClass(), "deleteUser");
        }
        if (result.getCode() != 0) {
            throw new ZUnknownException(result.getCode(), result.getMessage());
        }
        return result.getMessage();
    }
}
