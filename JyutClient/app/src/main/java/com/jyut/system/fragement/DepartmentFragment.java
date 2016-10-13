
package com.jyut.system.fragement;

import com.jyut.system.C;
import com.jyut.system.bean.DepartmentMember;

/**
 * 
 * @author wztscau
 * @date Sep 22, 2016
 * @project 粤盟管理系统客户端
 */
public class DepartmentFragment extends BaseListFragment   {


	@Override
	protected void init() {

	}

	@Override
	protected String setPath() {
		return C.PATH_SERVER_DEPT_QUERY;
	}

	@Override
	protected String setType() {
		return C.TYPE_DEPT;
	}

	@Override
	protected Class setEntityClass() {
		return DepartmentMember.class;
	}

	@Override
	protected String setTitle() {
		return "工作部成员";
	}

	@Override
	protected int setPageSize() {
		return C.PAGE_SIZE_DEFAULT;
	}

}
