/**
 * 
 */
package com.jyut.server;

import java.io.PrintWriter;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jyut.server.mysql.MysqlOpenHelper;

/**
 * @date Sep 29, 2016
 * @author wztscau
 * @project 粤盟管理系统服务器
 */
public abstract class UpdateServlet extends FastjsonServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5974510838370568034L;

	/* (non-Javadoc)
	 * @see com.jyut.server.FastjsonServlet#onHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.io.PrintWriter, java.sql.Connection)
	 */
	@Override
	protected void onHandle(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer, Connection connection)
			throws Exception {
		System.out.println("-----update start-----");
		MysqlOpenHelper.updateByJsonObject(connection, setTableName(), getJsonObjectFromClient(req), setKeyName());
		onSuccessResponse(req, resp, writer);
		System.out.println("-----update success-----");
	}
	
	protected abstract String setKeyName();
	
}
