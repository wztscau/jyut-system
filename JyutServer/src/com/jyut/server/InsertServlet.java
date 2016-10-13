/**
 * 
 */
package com.jyut.server;

import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jyut.server.C.L;
import com.jyut.server.mysql.MysqlOpenHelper;

/**
 * @date Sep 24, 2016
 * @author wztscau
 * @project 粤盟管理系统服务器
 */
public abstract class InsertServlet extends FastjsonServlet {

	private static final long serialVersionUID = -5965225950632764612L;

	
	/* (non-Javadoc)
	 * @see com.jyut.server.FastjsonServlet#onHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.io.PrintWriter, java.sql.Connection)
	 */
	@Override
	protected void onHandle(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer, Connection connection)
			throws Exception {
		System.out.println("-------insert start--------");
		MysqlOpenHelper.insertByJsonObject(connection, setTableName(), getJsonObjectFromClient(req));
		onSuccessResponse(req, resp, writer);
		System.out.println("-----insert success!!!-----");		
	}
	
	/* (non-Javadoc)
	 * @see com.jyut.server.FastjsonServlet#onFailResponse(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.io.PrintWriter)
	 */
	@Override
	protected void onFailResponse(HttpServletRequest request, HttpServletResponse response, PrintWriter printWriter,Exception e)
			throws Exception {
		boolean b = e.getMessage().contains("for key 'PRIMARY'");
		if(b){
			String tableName = setTableName();
			String servlet = "./"+tableName.substring(0,1)+"update";
			request.getRequestDispatcher(servlet).forward(request, response);
		}else{
			super.onFailResponse(request, response, printWriter,e);
		}
		
	}


}
