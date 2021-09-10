/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.handlers;

import com.vng.zing.common.HReqParam;
import com.vng.zing.dmp.common.exception.ZInvalidParamException;
import com.vng.zing.dmp.common.exception.ZNotExistException;
import com.vng.zing.dmp.common.exception.ZRemoteFailureException;
import com.vng.zing.logger.ZLogger;
import com.vng.zing.managementuser.entity.ApiResponse;
import com.vng.zing.managementuser.entity.UserDTO;
import com.vng.zing.managementuser.services.UserService;
import com.vng.zing.managementuser.utils.RequestUtils;
import com.vng.zing.userservice.thrift.User;
import com.vng.zing.zcommon.thrift.ECode;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author tanhd
 */
public class UserHandler extends HttpServlet {

    private static final Logger logger = ZLogger.getLogger(UserHandler.class);
    private UserService userService = new UserService();

    private int handleErrorCode(Exception ex) {
        int errorCode = -ECode.EXCEPTION.getValue();
        if (ex instanceof ZInvalidParamException) {
            errorCode = -ECode.INVALID_PARAM.getValue();
        }
        if (ex instanceof ZRemoteFailureException) {
            errorCode = -ECode.C_FAIL.getValue();
        }
        if (ex instanceof ZNotExistException) {
            errorCode = -ECode.NOT_EXIST.getValue();
        }
        return errorCode;
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = null;
        setAccessControlHeaders(resp);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ApiResponse apiResponse = new ApiResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            out = resp.getWriter();
            User putUserParams = RequestUtils.updateUserParams(req);
            String result = userService.updateUser(putUserParams);
            apiResponse.setCode(ECode.C_SUCCESS.getValue());
            apiResponse.setData(result);
        } catch (Exception ex) {
            logger.error(ex);
            apiResponse.setCode(handleErrorCode(ex));
            apiResponse.setData(ex.getMessage());
        } finally {
            out.println(objectMapper.writeValueAsString(apiResponse));
            if (out != null) {
                out.close();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        ApiResponse apiResponse = new ApiResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            out = resp.getWriter();
            UserDTO result = userService.getUser(HReqParam.getInt(req, "id"));
            apiResponse.setCode(ECode.C_SUCCESS.getValue());
            apiResponse.setData(result);
        } catch (Exception ex) {
            logger.error(ex);
            apiResponse.setCode(handleErrorCode(ex));
            apiResponse.setData(ex.getMessage());
        } finally {
            out.println(objectMapper.writeValueAsString(apiResponse));
            if (out != null) {
                out.close();
            }
        }
    }

    private void setAccessControlHeaders(HttpServletResponse resp) {
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST,PUT,DELETE");
        resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        setAccessControlHeaders(resp);
        super.doOptions(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setAccessControlHeaders(resp);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ApiResponse apiResponse = new ApiResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        PrintWriter out = null;
        try {
            out = resp.getWriter();
            User newUserParams = RequestUtils.createUserParams(req);
            String result = userService.createUser(newUserParams);
            apiResponse.setCode(ECode.C_SUCCESS.getValue());
            apiResponse.setData(result);
        } catch (Exception ex) {
            logger.error(ex);
            apiResponse.setCode(handleErrorCode(ex));
            apiResponse.setData(ex.getMessage());
        } finally {
            out.println(objectMapper.writeValueAsString(apiResponse));
            if (out != null) {
                out.close();
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        ApiResponse apiResponse = new ApiResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            out = resp.getWriter();
            int id = HReqParam.getInt(req, "id");
            String result = userService.deleteUser(id);
            apiResponse.setCode(ECode.C_SUCCESS.getValue());
            apiResponse.setData(result);
        } catch (Exception ex) {
            logger.error(ex);
            apiResponse.setCode(handleErrorCode(ex));
            apiResponse.setData(ex.getMessage());
        } finally {
            out.println(objectMapper.writeValueAsString(apiResponse));
            if (out != null) {
                out.close();
            }
        }
    }
}
