/**
 * 
 */
package com.jyut.server;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jyut.server.C.S;

/**
 * @date Sep 29, 2016
 * @author wztscau
 * @project 粤盟管理系统服务器
 */
@WebServlet("/cupdate")
public class ClubtableUpdateServlet extends UpdateServlet {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3098866844273028990L;

	/* (non-Javadoc)
	 * @see com.jyut.server.FastjsonServlet#onSuccessResponse(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected String setSuccessData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "";
	}

	/* (non-Javadoc)
	 * @see com.jyut.server.FastjsonServlet#onFailResponse(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected String setFailData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "";
	}

	/* (non-Javadoc)
	 * @see com.jyut.server.FastjsonServlet#setFailMessage()
	 */
	@Override
	protected String setFailMessage(Exception e) {
		return S.UPDATE_FAIL;
	}

	/* (non-Javadoc)
	 * @see com.jyut.server.FastjsonServlet#setSuccessMessage()
	 */
	@Override
	protected String setSuccessMessage() {
		return S.UPDATE_SUCCESS;
	}


	/* (non-Javadoc)
	 * @see com.jyut.server.MysqlServlet#setDatabaseName()
	 */
	@Override
	protected String setDatabaseName() {
		return C.DEFAULT_DBNAME;
	}

	/* (non-Javadoc)
	 * @see com.jyut.server.MysqlServlet#setTableName()
	 */
	@Override
	protected String setTableName() {
		return C.TABLE_CLUB;
	}

	/* (non-Javadoc)
	 * @see com.jyut.server.UpdateServlet#setKeyName()
	 */
	@Override
	protected String setKeyName() {
		// TODO Auto-generated method stub
		return C.TABLE_CLUB_KEY;
	}


}
