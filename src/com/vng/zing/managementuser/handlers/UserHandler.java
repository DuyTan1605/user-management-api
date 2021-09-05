/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vng.zing.managementuser.handlers;

import com.vng.zing.common.HReqParam;
import com.vng.zing.dmp.common.exception.ZInvalidParamException;
import com.vng.zing.exception.NotExistException;
import com.vng.zing.logger.ZLogger;
import com.vng.zing.managementuser.entity.ApiResponse;
import com.vng.zing.managementuser.services.UserService;
import com.vng.zing.managementuser.utils.RequestUtils;
import com.vng.zing.zcommon.thrift.ECode;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.JSONException;

/**
 *
 * @author tanhd
 */
public class UserHandler extends HttpServlet {

    private static final Logger _Logger = ZLogger.getLogger(UserHandler.class);
    private final UserService userService = new UserService();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = null;
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ApiResponse apiResponse = new ApiResponse();
        try {
            out = resp.getWriter();
            Object putUserParams = RequestUtils.updateUserParams(req);
            Object result = userService.updateUser(putUserParams);
            apiResponse = (ApiResponse) result;
        } catch (NotExistException ex) {
            _Logger.error(null, ex);
            apiResponse.setCode(-ECode.INVALID_PARAM.getValue());
            try {
                apiResponse.setData(apiResponse.createMessageError("Missing credentials"));
            } catch (JSONException ex1) {
                _Logger.error(null, ex1);
            }
        } catch (ZInvalidParamException ex) {
            _Logger.error(null, ex);
            apiResponse.setCode(ex.getError());
            try {
                apiResponse.setData(apiResponse.createMessageError(ex.getMessage()));
            } catch (JSONException ex1) {
                _Logger.error(null, ex1);
            }
        } catch (Exception ex) {
            _Logger.error(null, ex);
            apiResponse.setCode(-ECode.C_FAIL.getValue());
            try {
                apiResponse.setData(apiResponse.createMessageError(ex.getMessage()));
            } catch (JSONException ex1) {
                _Logger.error(null, ex1);
            }
        } finally {
            try {
                out.println(apiResponse.getFinalResponse());
                if (out != null) {
                    out.close();
                }
            } catch (JSONException ex) {
                _Logger.error(null, ex);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        ApiResponse apiResponse = new ApiResponse();
        try {
            out = resp.getWriter();
            int id = HReqParam.getInt(req, "id");
            Object result = userService.getUser(id);
            apiResponse = (ApiResponse) result;
        } catch (NotExistException ex) {
            _Logger.error(null, ex);
            apiResponse.setCode(-ECode.INVALID_PARAM.getValue());
            try {
                apiResponse.setData(apiResponse.createMessageError("Missing user ID"));
            } catch (JSONException ex1) {
                _Logger.error(null, ex1);
            }
        } catch (ZInvalidParamException ex) {
            _Logger.error(null, ex);
            apiResponse.setCode(ex.getError());
            try {
                apiResponse.setData(apiResponse.createMessageError(ex.getMessage()));
            } catch (JSONException ex1) {
                _Logger.error(null, ex1);
            }
        } catch (Exception ex) {
            _Logger.error(null, ex);
            apiResponse.setCode(-ECode.C_FAIL.getValue());
            try {
                apiResponse.setData(apiResponse.createMessageError(ex.getMessage()));
            } catch (JSONException ex1) {
                _Logger.error(null, ex1);
            }
        } finally {
            try {
                out.println(apiResponse.getFinalResponse());
                if (out != null) {
                    out.close();
                }
            } catch (JSONException ex) {
                _Logger.error(null, ex);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = null;
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        ApiResponse apiResponse = new ApiResponse();
        try {
            out = resp.getWriter();
            Object newUserParams = RequestUtils.createUserParams(req);
            Object result = userService.createUser(newUserParams);
            apiResponse = (ApiResponse) result;
        } catch (NotExistException ex) {
            _Logger.error(null, ex);
            apiResponse.setCode(-ECode.INVALID_PARAM.getValue());
            try {
                apiResponse.setData(apiResponse.createMessageError("Missing credentials"));
            } catch (JSONException ex1) {
                _Logger.error(null, ex1);
            }
        } catch (ZInvalidParamException ex) {
            _Logger.error(null, ex);
            apiResponse.setCode(ex.getError());
            try {
                apiResponse.setData(apiResponse.createMessageError(ex.getMessage()));
            } catch (JSONException ex1) {
                _Logger.error(null, ex1);
            }
        } catch (Exception ex) {
            _Logger.error(null, ex);
            apiResponse.setCode(-ECode.C_FAIL.getValue());
            try {
                apiResponse.setData(apiResponse.createMessageError(ex.getMessage()));
            } catch (JSONException ex1) {
                _Logger.error(null, ex1);
            }
        } finally {
            try {
                out.println(apiResponse.getFinalResponse());
                if (out != null) {
                    out.close();
                }
            } catch (JSONException ex) {
                _Logger.error(null, ex);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = null;
        ApiResponse apiResponse = new ApiResponse();
        try {
            out = resp.getWriter();
            int id = HReqParam.getInt(req, "id");
            Object result = userService.deleteUser(id);
            apiResponse = (ApiResponse) result;
        } catch (NotExistException ex) {
            _Logger.error(null, ex);
            apiResponse.setCode(-ECode.INVALID_PARAM.getValue());
            try {
                apiResponse.setData(apiResponse.createMessageError("Missing user ID"));
            } catch (JSONException ex1) {
                _Logger.error(null, ex1);
            }
        } catch (ZInvalidParamException ex) {
            _Logger.error(null, ex);
            apiResponse.setCode(ex.getError());
            try {
                apiResponse.setData(apiResponse.createMessageError(ex.getMessage()));
            } catch (JSONException ex1) {
                _Logger.error(null, ex1);
            }
        } catch (Exception ex) {
            _Logger.error(null, ex);
            apiResponse.setCode(-ECode.C_FAIL.getValue());
            try {
                apiResponse.setData(apiResponse.createMessageError(ex.getMessage()));
            } catch (JSONException ex1) {
                _Logger.error(null, ex1);
            }
        } finally {
            try {
                out.println(apiResponse.getFinalResponse());
                if (out != null) {
                    out.close();
                }
            } catch (JSONException ex) {
                _Logger.error(null, ex);
            }
        }
    }
}
