/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.services;

import com.vng.zing.common.ZErrorDef;
import com.vng.zing.logger.ZLogger;
import com.vng.zing.managementuser.entity.ApiResponse;
import com.vng.zing.managementuser.entity.UserDTO;
import com.vng.zing.managementuser.entity.UserResponseDTO;
import static com.vng.zing.managementuser.services.UserListService.client;
import com.vng.zing.stats.Profiler;
import com.vng.zing.stats.ThreadProfiler;
import com.vng.zing.userservice.thrift.CreateUserParams;
import com.vng.zing.userservice.thrift.CreateUserResult;
import com.vng.zing.userservice.thrift.DeleteUserParams;
import com.vng.zing.userservice.thrift.DeleteUserResult;
import com.vng.zing.userservice.thrift.DetailUserParams;
import com.vng.zing.userservice.thrift.DetailUserResult;
import com.vng.zing.userservice.thrift.Gender;
import com.vng.zing.userservice.thrift.ListUserParams;
import com.vng.zing.userservice.thrift.ListUserResult;
import com.vng.zing.userservice.thrift.UpdateUserParams;
import com.vng.zing.userservice.thrift.UpdateUserResult;
import com.vng.zing.userservice.thrift.User;
import com.vng.zing.userservice.thrift.wrapper.UserMwClient;
import com.vng.zing.utils.DateTimeUtils;
import com.vng.zing.zcommon.thrift.ECode;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    public JSONObject getUser(UserDTO getUserParams) throws JSONException {
        ThreadProfiler profiler = Profiler.getThreadProfiler();
        JSONArray arrayData = new JSONArray();
        ApiResponse apiResponse = new ApiResponse();
        try {
            DetailUserResult result = client.getUser(new DetailUserParams(getUserParams.getId()));
            profiler.push(this.getClass(), "output");
            apiResponse.setCode(result.getCode());
            JSONObject temp = new JSONObject();
            if (result.getData() != null) {
                User user = result.getData();
                UserResponseDTO myUserDTO = new UserResponseDTO(user.id, user.name, user.username, user.gender, DateTimeUtils.getLocalDateTime(user.birthday), DateTimeUtils.getLocalDateTime(user.createtime), DateTimeUtils.getLocalDateTime(user.updatetime));
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

    public JSONObject updateUser(UserDTO updateUserParams) throws JSONException {
        ThreadProfiler profiler = Profiler.getThreadProfiler();
        ApiResponse apiResponse = new ApiResponse();
        try {
            User newUser = new User();
            newUser.setId(updateUserParams.getId());
            newUser.setName(updateUserParams.getName());
            newUser.setUsername(updateUserParams.getUserName());
            newUser.setBirthday(updateUserParams.getBirthday());
            newUser.setGender(updateUserParams.getGender());
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

    public JSONObject createUser(UserDTO createUserParams) throws JSONException {
        ThreadProfiler profiler = Profiler.getThreadProfiler();
        ApiResponse apiResponse = new ApiResponse();
        try {
            User newUser = new User();
            newUser.setName(createUserParams.getName());
            newUser.setUsername(createUserParams.getUserName());
            newUser.setBirthday(createUserParams.getBirthday());
            newUser.setGender(createUserParams.getGender());
            newUser.setPassword(createUserParams.getPassword());
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

    public JSONObject deleteUser(UserDTO deleteUserParams) throws JSONException {
        ThreadProfiler profiler = Profiler.getThreadProfiler();
        ApiResponse apiResponse = new ApiResponse();
        try {
            DeleteUserResult result = client.deleteUser(new DeleteUserParams(deleteUserParams.getId()));
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
