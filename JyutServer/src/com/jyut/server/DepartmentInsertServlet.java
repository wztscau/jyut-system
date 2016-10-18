/**
 * 
 */
package com.jyut.server;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jyut.server.C.S;

/**
 * @date Sep 27, 2016
 * @author wztscau
 * @project 粤盟管理系统服务器
 */
@WebServlet("/dinsert")
public class DepartmentInsertServlet extends InsertServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -241292067783877444L;

	

	/* (non-Javadoc)
	 * @see com.jyut.server.InsertServlet#setDatabaseName()
	 */
	@Override
	protected String setDatabaseName() {
		return C.DEFAULT_DBNAME;
	}

	/* (non-Javadoc)
	 * @see com.jyut.server.InsertServlet#setTableName()
	 */
	@Override
	protected String setTableName() {
		return C.TABLE_DEPT;
	}

	/* (non-Javadoc)
	 * @see com.jyut.server.FastjsonServlet#onSuccessResponse(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected String setSuccessData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return "";
	}

	/* (non-Javadoc)
	 * @see com.jyut.server.FastjsonServlet#onFailResponse(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected String setFailData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		return "";
	}

	/* (non-Javadoc)
	 * @see com.jyut.server.FastjsonServlet#setFailMessage()
	 */
	@Override
	protected String setFailMessage(Exception e) {
		// TODO Auto-generated method stub
		return S.INSERT_FAIL;
	}

	/* (non-Javadoc)
	 * @see com.jyut.server.FastjsonServlet#setSuccessMessage()
	 */
	@Override
	protected String setSuccessMessage() {
		// TODO Auto-generated method stub
		return S.INSERT_SUCCESS;
	}


}
