
package com.jyut.system;

/**
 * 
 * @author wztscau
 * @date Sep 22, 2016
 * @project 粤盟管理系统客户端
 */
public class C {

	/* fixed value */
	public static final String URL_SERVER_WAN = "http://10.91.63.94:9394/";
	public static final String URL_SERVER_LAN = "http://192.168.1.2:8080/";
	public static final String URL_SERVER_DOMAIN = "http://1581k8119c.51mypc.cn:9394/";
	public static final String URL_SERVER_LOCAL = "http://127.0.0.1/";
	public static final String URL_SERVER_LOCAL_EMULATOR = "http://10.0.0.2:8080/";
	public static final String OFFLINE = "OFFLINE";
	public static final String PATH_SERVER_DEFAULT = "Jyut/";
	public static final String PATH_SERVER_LOGIN = "login";
	public static final String PATH_SERVER_CLUB_QUERY = "cquery";
	public static final String PATH_SERVER_SCHOOL_QUERY = "squery";
	public static final String PATH_SERVER_DEPT_QUERY = "dquery";
	public static final String PATH_SERVER_DEPT_INSERT = "dinsert";
	public static final String PATH_SERVER_CLUB_INSERT = "cinsert";
	public static final String PATH_SERVER_SCHOOL_INSERT = "sinsert";
	public static final String PREFERENCE_DEFAULT = "DEFAULT";
	public static final String LOCAL_DATABASE_DEFAULT = "jyut";
	public static final String TABLE_MSG = "message";
	public static final int WHAT_SUCCESS = 1;
	public static final int WHAT_FAIL = 2;
	/* fixed value */

	/* alternative value */
	public static final int WHAT = 1;
	public static final int PAGE_SIZE_DEFAULT = 10;
	public static final int VIEWPAGE_LIMIT = 1;
	public static final boolean ENCRYTED = true;
	public static final String POSITION = "POSITION";
	public static final String MEMBER = "MEMBER";
	public static final String DATA = "DATA";
	public static final String TYPE = "TYPE";
	public static final String TYPE_CLUB = "TYPE_CLUB";
	public static final String TYPE_DEPT = "TYPE_DEPT";
	public static final String TYPE_WRITE = "TYPE_WRITE";
	public static final String TYPE_READ = "TYPE_READ";
	public static final String TYPE_W_OR_R = "TYPE_W_OR_R";

	public static final String TYPE_FUNC_ALLOC = "TYPE_FUNC_ALLOC";
	public static final String TYPE_FUNC_ANNO = "TYPE_FUNC_ANNO";
	public static final String TYPE_FUNC_SCHOOL = "TYPE_FUNC_SCHOOL";
	public static final String TYPE_FUNC_MSG = "TYPE_FUNC_MSG";
	public static final String TYPE_FUNC_PERM = "TYPE_FUNC_PERM";
	public static final String TYPE_FUNC_CHECK = "TYPE_FUNC_CHECK";
	public static final String TYPE_FUNC_ADD = "TYPE_FUNC_ADD";
	public static final String TYPE_FUNC_MEMBER = "TYPE_ALLO";

	public static final String TAG_CLUB = "CLUB";
	public static final String TAG_MSG = "MSG";
	public static final String TAG_ME = "ME";
	public static final String SP_LOGIN = "SP_LOGIN";
	/* alternative value */

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
		public static final String DATA = "DATA";
		public static final String ERROR = "ERROR";
		public static final String URL = "URL";
		public static final String METHOD = "METHOD";

		public static final String QUERY = "QUERY";
		public static final String ORDER_BY = "ORDER_BY";
		public static final String LIMIT = "LIMIT";
		public static final String LIMIT_START = "LIMIT_START";
		public static final String LIMIT_SIZE = "LIMIT_SIZE";
		public static final String COLUMNS_LIMITED = "COLUMNS_LIMITED";
		public static final String COLUMNS_ALTER = "COLUMNS_ALTER";
		public static final String VALUES_ALTER = "VALUES_ALTER";
		public static final String VALUES_LIMITED = "VALUES_LIMITED";

		public static final String TITLE = "TITLE";
		public static final String CONTENT = "CONTENT";
		public static final String DATE = "DATE";
		public static final String READED = "READED";
		public static final String _ID = "_ID";
	}

	/* server */
	public static final String LOCALHOST = "127.0.0.1";
	public static final String URL_MYSQL = "jdbc:mysql://" + LOCALHOST + ":3306/";
	public static final String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
	/* server */

	/* sentence in Chinese */
	public static final class S {
		public static final String ACCESS_MYSQL_SUCCEED = "Access mysql database succeeded";
		public static final String ACCESS_MYSQL_FAILED = "Access mysql database failed";
		public static final String USERNAME_OR_PWD_WRONG = "username or password incorrect";
		public static final String ARE_YOU_COMMIT = "你是否确认你输入的数据准确无误？";
		public static final String CANNOT_BE_NULL = " 不能为空";
		public static final String TEL_FORMAT_ERROR = "电话号码格式错误";
		public static final String INSERT_SUCCESS = "Insert data succeed";
		public static final String INSERT_FAIL = "Insert data failed";
		public static final String QUERY_FAIL = "Query data failed";
		public static final String QUERY_SUCCESS = "Query data succeed";
		public static final String UPDATE_SUCCESS = "Update data succeed";
		public static final String UPDATE_FAIL = "Update data failed";
		public static final String LIST_OF_COLLAGE_CLUB = "高校粤语社团列表";
	}
	/* sentence in Chinese */

	public static boolean offLine;
	public static final boolean offLine(String status) {
		boolean b = C.OFFLINE.toLowerCase().equals(status.toLowerCase());
		offLine = b;
		return b;
	}
	
	public static final class T{
		public static final String RESPONSE = "RESPONSE";
		public static final int TAB_CLUB = 0;
		public static final int TAB_MSG = 1;
		public static final int TAB_ME = 2;
	}
}
