/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.services;

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
import com.vng.zing.utils.DateTimeUtils;
import com.vng.zing.zcommon.thrift.ECode;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;

/**
 *
 * @author tanhd
 */
public class UserService {

    private static final Logger _Logger = ZLogger.getLogger(UserListService.class);
    public static final UserService Instance = new UserService();
    public static final UserMwClient client = new UserMwClient("Main");

    private UserService() {
    }

    public JSONObject getUser(int id) throws JSONException {
        ThreadProfiler profiler = Profiler.getThreadProfiler();
        JSONArray arrayData = new JSONArray();
        ApiResponse apiResponse = new ApiResponse();
        try {
            DetailUserResult result = client.getUser(new DetailUserParams(id));
            profiler.push(this.getClass(), "output");
            apiResponse.setCode(result.getCode());
            JSONObject temp = new JSONObject();
            if (result.getData() != null) {
                User user = result.getData();
                UserDTO myUserDTO = new UserDTO(user.id, user.name, user.username, user.gender, DateTimeUtils.getLocalDateTime(user.birthday), DateTimeUtils.getLocalDateTime(user.createtime), DateTimeUtils.getLocalDateTime(user.updatetime));
                temp = myUserDTO.getUserResponse();
            }
            apiResponse.setData(new JSONObject().put("user", temp));
            profiler.pop(this.getClass(), "output");
        } catch (Exception ex) {
            _Logger.error(null, ex);
            apiResponse.setCode(ECode.C_FAIL.getValue());
            apiResponse.setData(new JSONObject());
        }
        return apiResponse.getFinalResponse();
    }

    public JSONObject updateUser(User newUser) throws JSONException {
        ThreadProfiler profiler = Profiler.getThreadProfiler();
        ApiResponse apiResponse = new ApiResponse();
        try {
            UpdateUserResult result = client.updateUser(new UpdateUserParams(newUser));
            apiResponse.setCode(result.getCode());
            apiResponse.setData(new JSONObject().put("message", result.getMessage()));
            profiler.pop(this.getClass(), "output");
        } catch (Exception ex) {
            _Logger.error(null, ex);
            apiResponse.setCode(ECode.C_FAIL.getValue());
            apiResponse.setData(new JSONObject());
        }
        return apiResponse.getFinalResponse();
    }

    public JSONObject createUser(User newUser) throws JSONException {
        ThreadProfiler profiler = Profiler.getThreadProfiler();
        ApiResponse apiResponse = new ApiResponse();
        try {
            CreateUserResult result = client.createUser(new CreateUserParams(newUser));
            apiResponse.setCode(result.getCode());
            apiResponse.setData(new JSONObject().put("message", result.getMessage()));
            profiler.pop(this.getClass(), "output");
        } catch (Exception ex) {
            _Logger.error(null, ex);
            apiResponse.setCode(ECode.C_FAIL.getValue());
            apiResponse.setData(new JSONObject());
        }
        return apiResponse.getFinalResponse();
    }

    public JSONObject deleteUser(int id) throws JSONException {
        ThreadProfiler profiler = Profiler.getThreadProfiler();
        ApiResponse apiResponse = new ApiResponse();
        try {
            DeleteUserResult result = client.deleteUser(new DeleteUserParams(id));
            apiResponse.setCode(result.getCode());
            apiResponse.setData(new JSONObject().put("message", result.getMessage()));
            profiler.pop(this.getClass(), "output");
        } catch (Exception ex) {
            _Logger.error(null, ex);
            apiResponse.setCode(ECode.C_FAIL.getValue());
            apiResponse.setData(new JSONObject());
        }
        return apiResponse.getFinalResponse();
    }
}
