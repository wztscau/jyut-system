/**
 * 
 */
package com.jyut.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jyut.server.C.S;

/**
 * @date Sep 29, 2016
 * @author wztscau
 * @project 粤盟管理系统服务器
 */
public class DepartmentUpdateServlet extends UpdateServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4928681067953135254L;


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
	protected String setFailMessage() {
		// TODO Auto-generated method stub
		return S.UPDATE_FAIL;
	}

	/* (non-Javadoc)
	 * @see com.jyut.server.FastjsonServlet#setSuccessMessage()
	 */
	@Override
	protected String setSuccessMessage() {
		// TODO Auto-generated method stub
		return S.UPDATE_SUCCESS;
	}

	/* (non-Javadoc)
	 * @see com.jyut.server.MysqlServlet#setDatabaseName()
	 */
	@Override
	protected String setDatabaseName() {
		// TODO Auto-generated method stub
		return C.DEFAULT_DBNAME;
	}

	/* (non-Javadoc)
	 * @see com.jyut.server.MysqlServlet#setTableName()
	 */
	@Override
	protected String setTableName() {
		// TODO Auto-generated method stub
		return C.TABLE_DEPT;
	}

	/* (non-Javadoc)
	 * @see com.jyut.server.UpdateServlet#setKeyName()
	 */
	@Override
	protected String setKeyName() {
		// TODO Auto-generated method stub
		return C.TABLE_DEPT_KEY;
	}

	

}
