/**
 * 
 */
package com.jyut.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jyut.server.C.S;
import com.jyut.server.mysql.MysqlOpenHelper;

/**
 * @date Sep 29, 2016
 * @author wztscau
 * @project 粤盟管理系统服务器
 */
public abstract class QueryServlet extends FastjsonServlet {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5181210612311268974L;
	private JSONArray jsonArray;

	/* (non-Javadoc)
	 * @see com.jyut.server.FastjsonServlet#onHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.io.PrintWriter, java.sql.Connection)
	 */
	@Override
	protected void onHandle(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer, Connection connection)
			throws Exception {
		System.out.println("-----start query-----");
		jsonArray = MysqlOpenHelper.queryObscureByJsonObject(connection, setTableName(), getJsonObjectFromClient(req));
		
		System.out.println("jsonArray---->"+jsonArray.toJSONString());
		onSuccessResponse(req, resp, writer);
		System.out.println("-----query finish-----");
	}
	
	/* (non-Javadoc)
	 * @see com.jyut.server.FastjsonServlet#setSuccessData(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected String setSuccessData(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		return jsonObject.toJSONString();
//		return jsonObject.toJSONString();
		String jsonString = jsonArray.toJSONString();
		System.out.println("length"+jsonString.length());
		return jsonString;
	}
	
}
