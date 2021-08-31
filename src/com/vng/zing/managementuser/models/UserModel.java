/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.models;

import com.vng.zing.common.ZErrorDef;
import com.vng.zing.logger.ZLogger;
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
public class UserModel {

    private static final Logger _Logger = ZLogger.getLogger(UserListModel.class);
    public static final UserModel Instance = new UserModel();

    private UserModel() {
    }

    public static long formatDateTime(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateFormat = LocalDateTime.parse(dateTime, formatter);
        return dateFormat.atZone(ZoneId.of("Asia/Ho_Chi_Minh")).toInstant().toEpochMilli();
    }

    public void getUser(HttpServletRequest req, HttpServletResponse resp) throws JSONException {
        ThreadProfiler profiler = Profiler.getThreadProfiler();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = null;

        try {
            out = resp.getWriter();
            JSONObject obj = new JSONObject();
            if (req.getParameter("id") == null) {
                obj.put("code", ZErrorDef.BAD_REQUEST);
                obj.put("data", new JSONObject().put("message", "Missing user ID"));
                out.println(obj);
            } else {
                UserMwClient client = new UserMwClient("Main");
                DetailUserResult result = client.getUser(new DetailUserParams(Integer.parseInt(req.getParameter("id"))));
                profiler.push(this.getClass(), "output");
                obj.put("code", result.getCode());
                JSONObject temp = new JSONObject();
                if (result.getData() != null) {
                    User user = result.getData();
                    temp.put("id", user.id);
                    temp.put("name", user.name);
                    temp.put("username", user.username);
                    temp.put("password", user.password);
                    temp.put("birthday", DateTimeUtils.getLocalDateTime(user.birthday));
                    temp.put("createtime", DateTimeUtils.getLocalDateTime(user.createtime));
                    temp.put("updatetime", DateTimeUtils.getLocalDateTime(user.updatetime));
                }
                obj.put("data", new JSONObject().put("user", temp));
                out.println(obj);
                profiler.pop(this.getClass(), "output");
            }

        } catch (Exception ex) {
            _Logger.error(null, ex);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public void updateUser(HttpServletRequest req, HttpServletResponse resp) throws JSONException {
        ThreadProfiler profiler = Profiler.getThreadProfiler();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = null;

        try {
            out = resp.getWriter();
            JSONObject obj = new JSONObject();
            if (req.getParameter("name") == null || req.getParameter("username") == null || req.getParameter("gender") == null || req.getParameter("birthday") == null) {
                obj.put("code", ZErrorDef.BAD_REQUEST);
                obj.put("data", new JSONObject().put("message", "Missing user infomation"));
                out.println(obj);
            } else {
                UserMwClient client = new UserMwClient("Main");
                UpdateUserParams params = new UpdateUserParams();
                User updatedUser = new User();
                updatedUser.setId(Integer.parseInt(req.getParameter("id")));
                updatedUser.setUsername(req.getParameter("username"));
                updatedUser.setName(req.getParameter("name"));
                updatedUser.setBirthday(formatDateTime(req.getParameter("birthday")));
                updatedUser.setGender(Gender.findByValue(Integer.parseInt(req.getParameter("gender"))));
                params.setUser(updatedUser);
                UpdateUserResult result = client.updateUser(params);
                profiler.push(this.getClass(), "output");
                obj.put("code", result.getCode());
                obj.put("data", new JSONObject().put("message", result.getMessage()));
                out.println(obj);
                profiler.pop(this.getClass(), "output");
            }

        } catch (Exception ex) {
            _Logger.error(null, ex);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public void createUser(HttpServletRequest req, HttpServletResponse resp) throws JSONException {
        ThreadProfiler profiler = Profiler.getThreadProfiler();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        JSONObject obj = new JSONObject();
        if (req.getParameter("name") == null || req.getParameter("username") == null || req.getParameter("password") == null || req.getParameter("gender") == null || req.getParameter("birthday") == null) {
            obj.put("code", ZErrorDef.BAD_REQUEST);
            obj.put("data", new JSONObject().put("message", "Missing new user infomation"));
            out.println(obj);
        } else {
            try {
                out = resp.getWriter();
                UserMwClient client = new UserMwClient("Main");
                CreateUserParams params = new CreateUserParams();
                User createdUser = new User();
                createdUser.setPassword(req.getParameter("password"));
                createdUser.setUsername(req.getParameter("username"));
                createdUser.setName(req.getParameter("name"));
                createdUser.setBirthday(formatDateTime(req.getParameter("birthday")));
                createdUser.setGender(Gender.findByValue(Integer.parseInt(req.getParameter("gender"))));
                params.setUser(createdUser);
                CreateUserResult result = client.createUser(params);
                profiler.push(this.getClass(), "output");
                obj.put("code", result.getCode());
                obj.put("data", new JSONObject().put("message", result.getMessage()));
                out.println(obj);
                profiler.pop(this.getClass(), "output");

            } catch (Exception ex) {
                _Logger.error(null, ex);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
    }

    public void deleteUser(HttpServletRequest req, HttpServletResponse resp) {
        ThreadProfiler profiler = Profiler.getThreadProfiler();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = null;

        try {
            out = resp.getWriter();
            JSONObject obj = new JSONObject();
            if (req.getParameter("id") == null) {
                obj.put("code", ZErrorDef.BAD_REQUEST);
                obj.put("data", new JSONObject().put("user", new JSONObject()));
                out.println(obj);
            } else {
                UserMwClient client = new UserMwClient("Main");
                DeleteUserResult result = client.deleteUser(new DeleteUserParams(Integer.parseInt(req.getParameter("id"))));
                profiler.push(this.getClass(), "output");
                obj.put("code", result.getCode());
                obj.put("data", new JSONObject().put("message", result.getMessage()));
                out.println(obj);
                profiler.pop(this.getClass(), "output");
            }

        } catch (Exception ex) {
            _Logger.error(null, ex);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
