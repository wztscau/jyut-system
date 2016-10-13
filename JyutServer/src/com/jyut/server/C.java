/**
 * @date Sep 21, 2016
 * @author wztscau
 * @project 粤盟管理系统
 */
package com.jyut.server;

/**
 * 
 * @author wztscau
 * @date Sep 22, 2016
 * @project 粤盟管理系统
 */
public class C {

	/*              fixed value              */
	public static final String URL_SERVER_WAN = "http://110.209.141.88:9394/";
	public static final String URL_SERVER_LAN = "http://192.168.1.102:8080/";
	public static final String URL_SERVER_DOMAIN = "http://1581k8119c.51mypc.cn:9394/";
	public static final String URL_SERVER_LOCAL = "http://127.0.0.1/";
	public static final String URL_SERVER_LOCAL_EMULATOR = "http://10.0.0.2:8080/";
	public static final String PREFERENCE_DEFAULT = "DEFAULT";
	public static final String DEFAULT_DBNAME = "jyut";
	public static final String TABLE_USERS = "users";
	public static final String TABLE_DEPT = "department";
	public static final String TABLE_DEPT_KEY = "account";
	public static final String TABLE_CLUB = "clubtable";
	public static final String TABLE_SCHOOL = "school";
	public static final String TABLE_CLUB_KEY = "school";
	public static final int WHAT_SUCCESS = 2;
	/*              fixed value              */

	/*           alternative value           */
	public static final int WHAT = 1;
	public static final boolean ENCRYPTED = true;
	public static final String MEMBER = "MEMBER";
	public static final String DATA = "DATA";
	public static final String TYPE = "TYPE";
	public static final String TYPE_CLUB = "TYPE_CLUB";
	public static final String TYPE_DEPT = "TYPE_DEPT";
	public static final String TAG_CLUB = "CLUB";
	public static final String TAG_MSG = "MSG";
	public static final String TAG_ME = "ME";
	public static final String SP_LOGIN = "SP_LOGIN";
	/*           alternative value           */

	public static final class L {
		public static final String NAME = "NAME";
		public static final String SCHOOL = "SCHOOL";
		public static final String ID = "ID";
		public static final String CLUB = "CLUB";
		public static final String MODIFIER = "MODIFIER";
		public static final String HEAD_PATH = "HEAD_PATH";
		public static final String LOCALE = "LOCALE";
		public static final String TEL = "TEL";
		public static final String QQ = "QQ";
		public static final String WECHAT = "WECHAT";

		public static final String ACCOUNT = "ACCOUNT";
		public static final String PASSWORD = "PASSWORD";
		public static final String USERNAME = "USERNAME";
		public static final String PERMISSION = "PERMISSION";
		public static final String MESSAGE = "MESSAGE";
		public static final String ERROR = "ERROR";
		public static final String DATA = "DATA";
		public static final String QUERY = "QUERY";
		
		public static final String ORDER_BY = "ORDER_BY";
		public static final String LIMIT = "LIMIT";
		public static final String LIMIT_START = "LIMIT_START";
		public static final String LIMIT_SIZE = "LIMIT_SIZE";
		public static final String COLUMNS_LIMITED = "COLUMNS_LIMITED";
		public static final String COLUMNS_ALTER = "COLUMNS_ALTER";
		public static final String VALUES_ALTER = "VALUES_ALTER";
		public static final String VALUES_LIMITED = "VALUES_LIMITED";

	}

	public static final String LOG_ = "LOG";

	/*                 server                 */
	public static final String LOCALHOST = "127.0.0.1";
	public static final String URL_MYSQL = "jdbc:mysql://" + LOCALHOST + ":3306/";
	public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	public static final String ACCESS_MYSQL_FAILED = "Access mysql database failed";
	public static final String ACCESS_MYSQL_SUCCEED = "Access mysql database succeeded";
	/*                 server                 */
	
	public static final class S{
	/*           sentence in Chinese          */
	public static final String ERROR_DATABASE = "Database occurs error";
	public static final String USERNAME_OR_PWD_WRONG = "Username or Password not correct";
	public static final String INSERT_SUCCESS = "Insert data succeed";
	public static final String INSERT_FAIL = "Insert data failed";
	public static final String QUERY_FAIL = "Query data failed";
	public static final String QUERY_SUCCESS = "Query data succeed";
	public static final String UPDATE_SUCCESS = "Update data succeed";
	public static final String UPDATE_FAIL = "Update data failed";
	/*           sentence in Chinese          */
	}

}
