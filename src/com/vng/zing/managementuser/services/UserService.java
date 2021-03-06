/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.services;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.vng.zing.dmp.common.exception.ZInvalidParamException;
import com.vng.zing.dmp.common.exception.ZUnknownException;
import com.vng.zing.dmp.common.interceptor.ApiProfiler;
import com.vng.zing.exception.InvalidParamException;
import com.vng.zing.exception.NotExistException;
import com.vng.zing.managementuser.entity.ApiResponse;
import com.vng.zing.managementuser.entity.UserDTO;
import com.vng.zing.managementuser.modules.UserMwModule;
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
import org.apache.thrift.TException;
import org.json.JSONException;
import org.json.simple.parser.ParseException;

/**
 *
 * @author tanhd
 */
public class UserService {

    private UserMwClient client;
    private ValidateService validateService;

    @Inject
    public UserService(ValidateService validateService, UserMwClient client) {
        this.validateService = validateService;
        this.client = client;
    }

    @ApiProfiler
    public UserDTO getUser(int id) throws JSONException, TException {

        UserDTO myUserDTO = null;

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

        return myUserDTO;
    }

    @ApiProfiler
    public String updateUser(User newUser) throws JSONException, ParseException, NotExistException, InvalidParamException, TException {

        UpdateUserResult result = new UpdateUserResult();

        validateService.validateUpdateUserParams(newUser);
        result = client.updateUser(new UpdateUserParams(newUser));
        if (result.getCode() != 0) {
            throw new ZUnknownException(result.getMessage());
        }

        return result.getMessage();
    }

    @ApiProfiler
    public String createUser(User newUser) throws JSONException, ParseException, TException, NotExistException, InvalidParamException {

        CreateUserResult result = new CreateUserResult();

        ApiResponse apiResponse = new ApiResponse();
        validateService.validateCreateUserParams(newUser);
        result = client.createUser(new CreateUserParams(newUser));

        if (result.getCode() != 0) {
            throw new ZUnknownException(result.getMessage());
        }
        return result.getMessage();
    }

    @ApiProfiler
    public String deleteUser(int id) throws JSONException, TException {
        DeleteUserResult result = new DeleteUserResult();

        if (id <= 0) {
            throw new ZInvalidParamException("User ID is not valid");
        }
        result = client.deleteUser(new DeleteUserParams(id));

        if (result.getCode() != 0) {
            throw new ZUnknownException(result.getCode(), result.getMessage());
        }
        return result.getMessage();
    }
}
