/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.services;

import com.vng.zing.dmp.common.exception.ZInvalidParamException;
import com.vng.zing.exception.InvalidParamException;
import com.vng.zing.exception.NotExistException;
import com.vng.zing.logger.ZLogger;
import com.vng.zing.managementuser.entity.ApiResponse;
import com.vng.zing.managementuser.entity.UserDTO;
import com.vng.zing.stats.Profiler;
import com.vng.zing.stats.ThreadProfiler;
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
import com.vng.zing.managementuser.utils.RequestUtils;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

/**
 *
 * @author tanhd
 */
public class UserService {

    private static final Logger _Logger = ZLogger.getLogger(UserListService.class);
    public static final UserMwClient client = new UserMwClient("Main");
    public static final ValidateService validateService = new ValidateService();

    public UserService() {
    }

    public Object getUser(int id) throws JSONException, TException {
        ThreadProfiler profiler = Profiler.getThreadProfiler();
        ApiResponse apiResponse = new ApiResponse();
        JSONObject temp = new JSONObject();
        if (!validateService.validateId(id)) {
            throw new ZInvalidParamException("User ID is not valid");
        } else {
            DetailUserResult result = client.getUser(new DetailUserParams(id));
            profiler.push(this.getClass(), "output");
            apiResponse.setCode(result.getCode());
            if (result.getCode() == 0) {
                User user = result.getData();
                UserDTO myUserDTO = new UserDTO(user.id, user.name, user.username, user.gender, DateTimeUtils.getLocalDateTime(user.birthday), DateTimeUtils.getLocalDateTime(user.createtime), DateTimeUtils.getLocalDateTime(user.updatetime));
                temp = myUserDTO.getUserResponse();
                apiResponse.setData(new JSONObject().put("user", temp));
            } else {
                apiResponse.setData(new JSONObject().put("user", JSONObject.NULL));
            }
            profiler.pop(this.getClass(), "output");
            return apiResponse;
        }
    }

    public Object updateUser(Object newUser) throws JSONException, ParseException, NotExistException, InvalidParamException, TException {
        ThreadProfiler profiler = Profiler.getThreadProfiler();
        ApiResponse apiResponse = new ApiResponse();
        validateService.validateUpdateUserParams(newUser);
        UpdateUserResult result = client.updateUser(new UpdateUserParams(RequestUtils.parseUpdateUserParams(newUser)));
        profiler.pop(this.getClass(), "output");
        apiResponse.setCode(result.getCode());
        apiResponse.setData(new JSONObject().put("message", result.getMessage()));
        return apiResponse;
    }

    public Object createUser(Object newUser) throws JSONException, ParseException, TException, NotExistException, InvalidParamException {
        ThreadProfiler profiler = Profiler.getThreadProfiler();
        ApiResponse apiResponse = new ApiResponse();
        validateService.validateCreateUserParams(newUser);
        CreateUserResult result = client.createUser(new CreateUserParams(RequestUtils.parseCreateUserParams(newUser)));
        profiler.pop(this.getClass(), "output");
        apiResponse.setCode(result.getCode());
        apiResponse.setData(new JSONObject().put("message", result.getMessage()));
        return apiResponse;
    }

    public Object deleteUser(int id) throws JSONException, TException {
        ThreadProfiler profiler = Profiler.getThreadProfiler();
        ApiResponse apiResponse = new ApiResponse();
        JSONObject temp = new JSONObject();
        if (!validateService.validateId(id)) {
            throw new ZInvalidParamException("User ID is not valid");
        } else {
            DeleteUserResult result = client.deleteUser(new DeleteUserParams(id));
            profiler.push(this.getClass(), "output");
            apiResponse.setCode(result.getCode());
            apiResponse.setData(new JSONObject().put("message", result.getMessage()));
            profiler.pop(this.getClass(), "output");
            return apiResponse;
        }
    }
}
