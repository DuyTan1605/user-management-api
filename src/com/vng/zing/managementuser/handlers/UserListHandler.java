/*
 * Copyright (c) 2012-2016 by Zalo Group.
 * All Rights Reserved.
 */
package com.vng.zing.managementuser.handlers;

import com.google.inject.Inject;
import com.vng.zing.dmp.common.exception.ZInvalidParamException;
import com.vng.zing.dmp.common.exception.ZNotExistException;
import com.vng.zing.dmp.common.exception.ZRemoteFailureException;
import com.vng.zing.dmp.common.interceptor.ThreadProfiler;
import com.vng.zing.logger.ZLogger;
import com.vng.zing.managementuser.entity.ApiResponse;
import com.vng.zing.managementuser.entity.UserDTO;
import com.vng.zing.managementuser.services.UserListService;
import com.vng.zing.zcommon.thrift.ECode;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class UserListHandler extends HttpServlet {

    private static final Logger logger = ZLogger.getLogger(UserListHandler.class);

    private UserListService userListService;

    @Inject
    public UserListHandler(UserListService userListService) {
        this.userListService = userListService;
    }

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

    @ThreadProfiler
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        ApiResponse apiResponse = new ApiResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            out = resp.getWriter();
            List<UserDTO> result = userListService.getList();
            apiResponse.setCode(ECode.C_SUCCESS.getValue());
            apiResponse.setData(result);
        } catch (Exception ex) {
            logger.error(ex);
            apiResponse.setCode(handleErrorCode(ex));
        } finally {
            out.println(objectMapper.writeValueAsString(apiResponse));
            if (out != null) {
                out.close();
            }
        }

    }

}
