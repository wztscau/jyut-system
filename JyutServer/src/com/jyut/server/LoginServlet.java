package com.jyut.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
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
public class LoginServlet extends FastjsonServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4136834482328834930L;
	/**
	 * 
	 */
	private String perm;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jyut.server.FastjsonServlet#onHandle(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.io.PrintWriter, java.sql.Connection)
	 */
	@Override
	protected void onHandle(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer, Connection connection)
			throws Exception {
		parseUserMessage(req);
		queryUserExists(req, resp, connection);
		// dispatch(req, resp);
		// writer.write("");

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
			if (resultSet.getString(3).equals(password)) {
				perm = resultSet.getString(4);
				onSuccessResponse(req, resp, writer);
			} else {
				throw new InvalidValueException(S.USERNAME_OR_PWD_WRONG);
			}
			statement.close();
			resultSet.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				onFailResponse(req, resp, writer, e);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	private User parseUserMessage(HttpServletRequest req) {
		JSONObject jsonObject = getJsonObjectFromClient(req);
		userName = jsonObject.getString("userName");
		password = jsonObject.getString("password");
		return new User(userName, password);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jyut.server.FastjsonServlet#onFailResponse(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.io.PrintWriter, java.lang.Exception)
	 */
	@Override
	protected void onFailResponse(HttpServletRequest request, HttpServletResponse response, PrintWriter printWriter,
			Exception e) throws Exception {
		// TODO Auto-generated method stub
		super.onFailResponse(request, response, printWriter, e);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jyut.server.FastjsonServlet#onSuccessResponse(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected String setSuccessData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<>();
		map.put(L.PERMISSION, perm);
		JSONObject jsonObject = (JSONObject) JSON.toJSON(map);
		return jsonObject.toJSONString();
		// return C.ACCESS_MYSQL_SUCCEED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jyut.server.FastjsonServlet#onFailResponse(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected String setFailData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return C.ACCESS_MYSQL_FAILED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jyut.server.FastjsonServlet#setFailMessage()
	 */
	@Override
	protected String setFailMessage() {
		// TODO Auto-generated method stub
		return C.ACCESS_MYSQL_FAILED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jyut.server.FastjsonServlet#setSuccessMessage()
	 */
	@Override
	protected String setSuccessMessage() {
		// TODO Auto-generated method stub
		return C.ACCESS_MYSQL_SUCCEED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jyut.server.MysqlServlet#setDatabaseName()
	 */
	@Override
	protected String setDatabaseName() {
		// TODO Auto-generated method stub
		return C.DEFAULT_DBNAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jyut.server.MysqlServlet#setTableName()
	 */
	@Override
	protected String setTableName() {
		// TODO Auto-generated method stub
		return C.TABLE_USERS;
	}

}
