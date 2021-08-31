/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.handlers;

import com.vng.zing.logger.ZLogger;
import com.vng.zing.managementuser.entity.ApiResponse;
import com.vng.zing.managementuser.entity.UserDTO;
import com.vng.zing.managementuser.services.UserListService;
import com.vng.zing.managementuser.services.UserService;
import com.vng.zing.stats.Profiler;
import com.vng.zing.stats.ThreadProfiler;
import com.vng.zing.userservice.thrift.Gender;
import com.vng.zing.utils.DateTimeUtils;
import com.vng.zing.zcommon.thrift.ECode;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author tanhd
 */
public class UserHandler extends HttpServlet {

    private static final Logger _Logger = ZLogger.getLogger(UserHandler.class);

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiResponse apiResponse = new ApiResponse();
        PrintWriter out = null;
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out = resp.getWriter();
            List<String> parameterNames = new ArrayList<String>(req.getParameterMap().keySet());
            List<String> requiredParams = new ArrayList<String>();
            requiredParams.add("id");
            requiredParams.add("name");
            requiredParams.add("username");
            requiredParams.add("password");
            requiredParams.add("gender");
            requiredParams.add("birthday");
            if (!parameterNames.containsAll(requiredParams)) {
                JSONObject invalidRequest = new JSONObject();
                invalidRequest.put("message", "Missing user infomation");
                apiResponse.setCode(ECode.INVALID_PARAM.getValue());
                apiResponse.setData(invalidRequest);
                out.println(apiResponse.getFinalResponse());
            } else {
                UserDTO updateUserParams = new UserDTO();
                updateUserParams.setId(Integer.parseInt(req.getParameter("id")));
                updateUserParams.setName(req.getParameter("name"));
                updateUserParams.setUserName(req.getParameter("username"));
                updateUserParams.setBirthday(DateTimeUtils.formatDateTime(req.getParameter("birthday")));
                updateUserParams.setGender(Gender.findByValue(Integer.parseInt(req.getParameter("gender"))));
                JSONObject result = UserService.Instance.updateUser(updateUserParams);
                out.println(result);
            }
        } catch (Exception ex) {
            _Logger.error(null, ex);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        ApiResponse apiResponse = new ApiResponse();
        PrintWriter out = null;
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out = resp.getWriter();
            if (req.getParameter("id") == null) {
                JSONObject invalidRequest = new JSONObject();
                invalidRequest.put("message", "Missing user ID");
                apiResponse.setCode(ECode.INVALID_PARAM.getValue());
                apiResponse.setData(invalidRequest);
                out.println(apiResponse.getFinalResponse());
            } else {
                UserDTO getUserParams = new UserDTO(Integer.parseInt(req.getParameter("id")));
                JSONObject result = UserService.Instance.getUser(getUserParams);
                out.println(result);
            }
        } catch (Exception ex) {
            _Logger.error(null, ex);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiResponse apiResponse = new ApiResponse();
        PrintWriter out = null;
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out = resp.getWriter();
            List<String> parameterNames = new ArrayList<String>(req.getParameterMap().keySet());
            List<String> requiredParams = new ArrayList<String>();
            requiredParams.add("name");
            requiredParams.add("username");
            requiredParams.add("password");
            requiredParams.add("gender");
            requiredParams.add("birthday");
            if (!parameterNames.containsAll(requiredParams)) {
                JSONObject invalidRequest = new JSONObject();
                invalidRequest.put("message", "Missing user infomation");
                apiResponse.setCode(ECode.INVALID_PARAM.getValue());
                apiResponse.setData(invalidRequest);
                out.println(apiResponse.getFinalResponse());
            } else {
                UserDTO createUserParams = new UserDTO();
                createUserParams.setName(req.getParameter("name"));
                createUserParams.setUserName(req.getParameter("username"));
                createUserParams.setPassword(req.getParameter("password"));
                createUserParams.setBirthday(DateTimeUtils.formatDateTime(req.getParameter("birthday")));
                createUserParams.setGender(Gender.findByValue(Integer.parseInt(req.getParameter("gender"))));
                JSONObject result = UserService.Instance.createUser(createUserParams);
                out.println(result);
            }
        } catch (Exception ex) {
            _Logger.error(null, ex);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ApiResponse apiResponse = new ApiResponse();
        PrintWriter out = null;
        try {
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            out = resp.getWriter();
            List<String> parameterNames = new ArrayList<String>(req.getParameterMap().keySet());
            List<String> requiredParams = new ArrayList<String>();
            requiredParams.add("id");
            if (!parameterNames.containsAll(requiredParams)) {
                JSONObject invalidRequest = new JSONObject();
                invalidRequest.put("message", "Missing user infomation");
                apiResponse.setCode(ECode.INVALID_PARAM.getValue());
                apiResponse.setData(invalidRequest);
                out.println(apiResponse.getFinalResponse());
            } else {
                UserDTO deleteUserParams = new UserDTO();
                deleteUserParams.setId(Integer.parseInt(req.getParameter("id")));
                JSONObject result = UserService.Instance.deleteUser(deleteUserParams);
                out.println(result);
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
