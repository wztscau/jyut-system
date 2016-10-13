package com.jyut.server.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jyut.server.C.L;
import com.jyut.server.util.JsonDealer;
import com.jyut.server.util.TextUtil;

import jdk.nashorn.internal.scripts.JO;

/**
 * 
 * @date Sep 24, 2016
 * @author wztscau
 * @project 粤盟管理系统服务器
 *          <p>
 *          处理mysql数据库操作的助手
 *          </p>
 */
public class MysqlOpenHelper {

	// private Connection connection;
	private static MysqlOpenHelper helper;
	private static final String ID = "\"id\"";

	private MysqlOpenHelper(Connection connection) {
		// this.connection = connection;
	}

	/**
	 * 线程安全的单例模式
	 * 
	 * @param connection
	 * @return
	 */
	public static MysqlOpenHelper getInstance(Connection connection) {
		if (helper == null) {
			synchronized (MysqlOpenHelper.class) {
				if (helper == null) {
					helper = new MysqlOpenHelper(connection);
				}
			}
		}
		return helper;
	}

	public boolean insert() throws SQLException {

		return true;
	}

	public void delete() {

	}

	public static boolean updateByJsonObject(Connection conn, String tableName, JSONObject jsonObject, String keyName)
			throws SQLException {
		Statement statement = null;
		try {
			statement = conn.createStatement();
			Set<Entry<String, Object>> set = jsonObject.entrySet();
			boolean contain_Id = containsID(jsonObject.toJSONString());
			StringBuffer mark = new StringBuffer();
			for (int i = 0; i < (contain_Id ? set.size() - 2 : set.size() - 1); i++) {
				mark.append("?=?,");
			}
			mark.deleteCharAt(mark.length() - 1);
			String sql = "update " + tableName + " set " + mark + " where " + keyName + "=\'"
					+ jsonObject.getString(keyName) + "\'";
			System.out.println(sql);
			int i = 0;
			for (Entry<String, Object> entry : set) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if ("id".equals(key.toLowerCase())) {
					continue;
				}
				if (keyName.equals(key.toLowerCase())) {
					continue;
				}
				sql = TextUtil.replaceCharIndex(sql, '?', key, 1);
				if (value instanceof Number) {
					sql = TextUtil.replaceCharIndex(sql, '?', String.valueOf(value), 1);

				} else if (value instanceof String) {
					sql = TextUtil.replaceCharIndex(sql, '?', "\'" + value + "\'", 1);

				} else {
					throw new SQLException("The type in JsonObject is unknown type");
				}
				System.out.println("i==" + i + "  sql:" + sql);

				i++;
			}
			statement.execute(sql);
		} finally {
			statement.close();
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public static JSONArray queryObscureByJsonObject(Connection conn, String tableName, JSONObject jsonObject)
			throws SQLException {
		// String value = jsonObject.getString(L.DATA);
		if (jsonObject == null) {
			String sql = "select * from " + tableName;
			PreparedStatement pStatement = conn.prepareStatement(sql);
			JSONArray array = getArrayFromQuerytion(pStatement);
			return array;
		}

		List<String> columnsAlter = (List<String>) jsonObject.get(L.COLUMNS_ALTER);
		List<String> columnsLimited = (List<String>) jsonObject.get(L.COLUMNS_LIMITED);
		List<String> valuesLimited = (List<String>) jsonObject.get(L.VALUES_LIMITED);
		String value = jsonObject.getString(L.VALUES_ALTER);
		String orderBy = jsonObject.getString(L.ORDER_BY);
		boolean limit = jsonObject.getBooleanValue(L.LIMIT);
		int limitStart = jsonObject.getIntValue(L.LIMIT_START);
		int limitSize = jsonObject.getIntValue(L.LIMIT_SIZE);

		String where = value != null || valuesLimited != null ? " where " : "";
		String sql = "select * from " + tableName + where;
		StringBuffer mark1 = new StringBuffer();
		StringBuffer mark2 = new StringBuffer();
		for (int i = 0; columnsLimited != null && i < columnsLimited.size(); i++) {
			mark1.append(columnsLimited.get(i) + " = ? and ");
		}
		if (columnsAlter != null) {
			mark2.append("(");
		}
		for (int i = 0; columnsAlter != null && i < columnsAlter.size(); i++) {
			mark2.append(columnsAlter.get(i) + " like ? or ");
		}
		if (columnsLimited != null && columnsAlter == null) {
			mark1.delete(mark1.length() - 4, mark1.length());
		}
		if (columnsAlter != null) {
			mark2.delete(mark2.length() - 3, mark2.length());
			mark2.append(")");
		}

		sql += mark1.toString();
		sql += mark2.toString();
		sql += orderBy != null ? " order by " + orderBy : " order by id";
		sql += limit ? " limit " + limitStart + "," + (limitStart + limitSize) : "";
		System.out.println(sql);

		PreparedStatement pStatement = conn.prepareStatement(sql);
		for (int i = 0; columnsLimited != null && i < columnsLimited.size(); i++) {
			pStatement.setString(i + 1, valuesLimited.get(i));
		}
		for (int i = columnsLimited != null ? columnsLimited.size() + 1 : 1; columnsAlter != null
				&& i < columnsAlter.size() + (columnsLimited != null ? columnsLimited.size() + 1 : 1); i++) {
			System.out.println("i--->" + i);
			pStatement.setString(i, "%" + value + "%");
		}
		// for (int i = columnsLimited != null ? columnsLimited.size() +1 : 1;
		// columnsAlter != null
		// && i <= columnsAlter.size() + (columnsLimited != null ?
		// columnsAlter.size() : 0); i++) {
		// pStatement.setString(i, "%" + value + "%");
		// }
		JSONArray array = getArrayFromQuerytion(pStatement);
		// return JsonDealer.beanToJsonObject(list);
		// return JSONArray.array;
		return array;
	}

	private static JSONArray getArrayFromQuerytion(PreparedStatement pStatement) throws SQLException {
		ResultSet resultSet = pStatement.executeQuery();
		ResultSetMetaData metaData = resultSet.getMetaData();
		List<Map<String, String>> list = new ArrayList<>();
		JSONArray array = new JSONArray();
		while (resultSet.next()) {
			Map<String, String> map = new HashMap<>();
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				// 这里要统一大小写，因为从数据库中读取的列名和bean属性的名字虽然相同，但是大小写不相同
				// 但是如果统一大写的话，jsonObject会解析不成为一个合法的bean（虽然不为null，
				// 但是属性没有匹配上),因此实验结果为统一小写才行.
				String columnName = metaData.getColumnName(i).toLowerCase();
				String columnValue = resultSet.getString(i);
				map.put(columnName, columnValue);
			}
			list.add(map);
		}
		array.addAll(list);
		System.out.println("list---->" + list);
		pStatement.close();
		resultSet.close();
		return array;
	}

	// public static void queryPreciseByJsonObject(Connection conn, String
	// tableName, JSONObject jsonObject,
	// String... keywords) throws SQLException {
	// // String value = jsonObject.getString(L.DATA);
	// List<String> columns = (List<String>) jsonObject.get(L.COLUMNS_LIMITED);
	//
	// System.out.println("list : " + columns);
	// String sql = "select * from " + tableName + " where ";
	// StringBuffer mark = new StringBuffer();
	// for (int i = 0; i < keywords.length; i++) {
	// mark.append(keywords[i] + " like ? or ");
	// }
	// mark.delete(mark.length() - 3, mark.length());
	// sql += mark.toString();
	// System.out.println(sql);
	// PreparedStatement pStatement = conn.prepareStatement(sql);
	// for (int i = 0; i < keywords.length; i++) {
	// // pStatement.setString(i+1, value);
	// }
	// ResultSet resultSet = pStatement.executeQuery();
	// Map<String, String> map = new HashMap<>();
	// while (resultSet.next()) {
	// // map.put(key, value)
	// }
	// }

	/**
	 * 功能如其名
	 * 
	 * @param tableName
	 *            表名
	 * @param jsonObject
	 *            jsonobject
	 * @return 插入是否成功
	 * @throws SQLException
	 *             异常
	 */
	public static boolean insertByJsonObject(Connection conn, String tableName, JSONObject jsonObject)
			throws SQLException {
		Statement statement = null;
		try {
			statement = conn.createStatement();
			Set<Entry<String, Object>> set = jsonObject.entrySet();
			boolean contain_Id = containsID(jsonObject.toJSONString());

			StringBuffer mark = new StringBuffer();
			for (int i = 0; i < (contain_Id ? set.size() - 1 : set.size()); i++) {
				mark.append("?,");
			}
			System.out.println("mark::" + mark);
			mark.deleteCharAt(mark.length() - 1);
			String sql = "insert into " + tableName + "(" + mark + ")values(" + mark + ") ";
			System.out.println(sql);
			int i = 0;
			for (Entry<String, Object> entry : set) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (key.toLowerCase().equals("id")) {
					continue;
				}
				sql = TextUtil.replaceCharIndex(sql, '?', key, 1);
				if (value instanceof Number) {
					sql = TextUtil.replaceCharIndex(sql, '?', String.valueOf(value),
							contain_Id ? set.size() - i - 1 : set.size() - i);

				} else if (value instanceof String) {
					sql = TextUtil.replaceCharIndex(sql, '?', "\'" + value + "\'",
							contain_Id ? set.size() - i - 1 : set.size() - i);

				} else {
					throw new SQLException("The type in JsonObject is unknown type");
				}
				System.out.println("i==" + i + "  sql:" + sql);

				i++;
			}
			statement.execute(sql);
		} finally {
			if (statement != null) {
				statement.close();
			}
		}
		return true;
	}

	private static boolean containsID(String json) {
		if (json.toLowerCase().contains(ID)) {
			return true;
		}
		return false;
	}

	public static boolean insertByJsonObject(Connection conn, String tableName, String jsonString) throws SQLException {
		return insertByJsonObject(conn, tableName, JSON.parseObject(jsonString));
	}

}
