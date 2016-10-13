/**
 * 
 */
package com.jyut.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jyut.server.C.S;

/**
 * @date Oct 11, 2016
 * @author wztscau
 * @project 粤盟管理系统服务器
 */
public class SchoolQueryServlet extends QueryServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8943251918667156516L;

	/* (non-Javadoc)
	 * @see com.jyut.server.FastjsonServlet#setFailData(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
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
		return S.QUERY_FAIL;
	}

	/* (non-Javadoc)
	 * @see com.jyut.server.FastjsonServlet#setSuccessMessage()
	 */
	@Override
	protected String setSuccessMessage() {
		// TODO Auto-generated method stub
		return S.QUERY_SUCCESS;
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
		return C.TABLE_SCHOOL;
	}

}
