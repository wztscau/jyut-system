/**
 * 
 */
package com.jyut.server;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.jyut.server.C.L;
import com.jyut.server.C.S;
import com.jyut.server.util.Encryption;

/**
 * @date Sep 29, 2016
 * @author wztscau
 * @project 粤盟管理系统服务器
 */
public class ClubtableInsertServlet extends InsertServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7335873468347536750L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jyut.server.InsertServlet#onHandle(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * java.io.PrintWriter, java.sql.Connection)
	 */
	@Override
	protected void onHandle(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer, Connection connection)
			throws Exception {
		// TODO Auto-generated method stub
		super.onHandle(req, resp, writer, connection);
		String sql = "insert into school(locale,school)values(?,?)";
		System.out.println("---------insert into school---------");
		System.out.println("sql---" + sql);
		PreparedStatement pStatement = connection.prepareStatement(sql);
		pStatement.setString(1, getJsonObjectFromClient(req).getString(L.LOCALE));
		pStatement.setString(2, getJsonObjectFromClient(req).getString(L.SCHOOL));
		pStatement.close();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jyut.server.InsertServlet#setDatabaseName()
	 */
	@Override
	protected String setDatabaseName() {
		// TODO Auto-generated method stub
		return C.DEFAULT_DBNAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jyut.server.InsertServlet#setTableName()
	 */
	@Override
	protected String setTableName() {
		// TODO Auto-generated method stub
		return C.TABLE_CLUB;
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
		// TODO Auto-generated method stub
		return "";
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
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jyut.server.FastjsonServlet#setFailMessage()
	 */
	@Override
	protected String setFailMessage() {
		// TODO Auto-generated method stub
		return S.INSERT_FAIL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jyut.server.FastjsonServlet#setSuccessMessage()
	 */
	@Override
	protected String setSuccessMessage() {
		// TODO Auto-generated method stub
		return S.INSERT_SUCCESS;
	}

}
