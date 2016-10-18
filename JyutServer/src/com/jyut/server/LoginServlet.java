package com.jyut.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jyut.server.C.L;
import com.jyut.server.C.S;
import com.jyut.server.bean.User;
import com.jyut.server.util.Encryption;
import com.jyut.server.util.JsonDealer;
import com.oracle.jrockit.jfr.InvalidValueException;

/**
 * 
 * @date Sep 24, 2016
 * @author wztscau
 * @project 粤盟管理系统服务器
 */
@WebServlet("/login")
public class LoginServlet extends FastjsonServlet {

	private static final long serialVersionUID = -4136834482328834930L;
	private String perm;
	private static String ERROR = "username or password incorrect";

	@Override
	protected void onHandle(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer, Connection connection)
			throws Exception {
		parseUserMessage(req);
		queryUserExists(req, resp, connection);
		System.out.println("Connect success!!!");
	}

	/**
	 * @param resp
	 * @param req
	 * @param conn2
	 * @throws IOException
	 */
	private void queryUserExists(HttpServletRequest req, HttpServletResponse resp, Connection conn) throws IOException {
		PrintWriter writer = resp.getWriter();
		try {
			Statement statement = conn.createStatement();
			String sql = "select * from " + setTableName() + " where username=\'" + userName + "\'";
			// System.out.println(sql);
			ResultSet resultSet = statement.executeQuery(sql);
			resultSet.next();
			if (resultSet.getString(resultSet.findColumn(L.PASSWORD)).equals(password)) {
				perm = resultSet.getString(resultSet.findColumn(L.PERMISSION));
//				saveCookies(resp);
				onSuccessResponse(req, resp, writer);
			} else {
				throw new SQLException(S.USERNAME_OR_PWD_WRONG,"error");
			}
			statement.close();
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				onFailResponse(req, resp, writer, e);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	private void saveCookies(HttpServletResponse resp) {
		Cookie cookie = new Cookie(L.USERNAME, userName);
		resp.addCookie(cookie);
		Cookie cookie2 = new Cookie(L.PASSWORD, password);
		resp.addCookie(cookie2);
	}

	private void parseUserMessage(HttpServletRequest req) {
		JSONObject jsonObject = getJsonObjectFromClient(req);
		userName = jsonObject.getString("userName");
		password = jsonObject.getString("password");
	}

	@Override
	protected String setSuccessData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put(L.PERMISSION, perm);
		JSONObject jsonObject = (JSONObject) JSON.toJSON(map);
		return jsonObject.toJSONString();
	}

	@Override
	protected String setFailData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return C.ACCESS_MYSQL_FAILED;
	}

	@Override
	protected String setFailMessage(Exception e) {
		if(e instanceof SQLException){
			if(e.getMessage().contains("operation on empty result set")){
				return ERROR;
			}
		}
		return C.ACCESS_MYSQL_FAILED;
	}

	@Override
	protected String setSuccessMessage() {
		return C.ACCESS_MYSQL_SUCCEED;
	}

	@Override
	protected String setDatabaseName() {
		return C.DEFAULT_DBNAME;
	}

	@Override
	protected String setTableName() {
		return C.TABLE_USERS;
	}

}
