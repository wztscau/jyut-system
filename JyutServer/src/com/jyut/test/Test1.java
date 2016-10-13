/**
 * 
 */
package com.jyut.test;

import java.awt.print.Pageable;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Map.Entry;

import javax.print.DocFlavor.STRING;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jyut.server.C;
import com.jyut.server.C.L;
import com.jyut.server.bean.ClubMember;
import com.jyut.server.bean.DepartmentMember;
import com.jyut.server.mysql.MysqlOpenHelper;
import com.jyut.server.util.Encryption;
import com.jyut.server.util.JsonDealer;
import com.jyut.server.util.TextUtil;
import org.junit.Test;

/**
 * @date Sep 24, 2016
 * @author wztscau
 * @project 粤盟管理系统服务器
 */
public class Test1 {

	
	
	@Test
	public void testJsonObject(){
		String string = "{\"CLUB\":\"guang\",\"ID\":\"13\",\"LOCALE\":\"广东\",\"MODIFIER\":\"xxx\",\"NAME\":\"wuzitao\",\"QQ\":\"\",\"SCHOOL\":\"guang\",\"TEL\":\"13332808084\",\"WECHAT\":\"\"}";
		string = string.toLowerCase();
		JSONObject jsonObject = JSON.parseObject(string);
		ClubMember clubMember = JSON.toJavaObject(jsonObject, ClubMember.class);
		
		System.out.println(clubMember.getClub());
		JSONObject beanToJsonObject = JsonDealer.beanToJsonObject(clubMember);
		System.out.println(beanToJsonObject);
	}
	
	public static <T>List<?> abc(JSONArray jsonArray,Class<T> clz){
//		List<? extends parcel> memberList = new ArrayList<>();
//		Iterator<Object> iterator = jsonArray.iterator();
//		int i = 0;
//		while(iterator.hasNext()&&i++<10){
//			JSONObject jsonObject = (JSONObject) iterator.next();
////			Member member = (Member) JSON.toJavaObject(jsonObject, clz);
////			memberList.add(member);
//			 = JSON.toJavaObject(jsonObject,clz);
//			memberList.add(
//					);
//		}
		return null;
	}
	
	@Test
	public void testSQL2() {

		// String jsonString = "{\"key\":\"value\"}";
		// JSONObject jsonObject = JSONObject.parseObject(jsonString);
		// String s1 = jsonObject.getString("key");
		// String s2 = jsonObject.getString("value");
		// System.out.println(s2);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testSQL() throws ClassNotFoundException, SQLException {
		// String jsonString =
		// "{\"valuesLimited\":[\"全国\"],\"COLUMNS_LIMITED\":[\"locale\"],\"COLUMNS_ALTER\":[\"SCHOOL\",\"NAME\",\"CLUB\"],\"VALUES_ALTER\":\"s\",\"VALUES_LIMITED\":[\"全国\"]}";
		String jsonString = "{\"COLUMNS_ALTER\":[\"SCHOOL\",\"NAME\",\"CLUB\"],\"VALUES_ALTER\":\"s\",\"VALUES_LIMITED\":[\"全国\"]}";

		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		List<String> columnsAlter = (List<String>) jsonObject.get(L.COLUMNS_ALTER);
		List<String> columnsLimited = (List<String>) jsonObject.get(L.COLUMNS_LIMITED);
		List<String> valuesLimited = (List<String>) jsonObject.get(L.VALUES_LIMITED);
		String value = jsonObject.getString(L.VALUES_ALTER);

		String sql = "select * from " + "clubtable" + " where ";
		StringBuffer mark1 = new StringBuffer();
		StringBuffer mark2 = new StringBuffer();
		for (int i = 0; columnsLimited != null && i < columnsLimited.size(); i++) {
			mark1.append(columnsLimited.get(i) + " = ? and ");
		}
		mark2.append("(");
		for (int i = 0; columnsAlter != null && i < columnsAlter.size(); i++) {
			mark2.append(columnsAlter.get(i) + " like ? or ");
		}
		if (columnsLimited != null && columnsAlter == null) {
			mark1.delete(mark1.length() - 4, mark1.length());
		}
		mark2.delete(mark2.length() - 3, mark2.length());
		mark2.append(")");
		sql += mark1.toString();
		sql += mark2.toString();
		System.out.println(sql);
		System.out.println("widfafda");
	}

	@Test
	public void length() {
		String string = "JEQVRBIjoie1wiUEVSTUlTU0lPTlwiOlwiMVwifSIsIkVSUk9SIjoiMCIsIk1FU1NBR0UiOiJBY2Nlc3MgbXlzcWwgZGF0YWJhc2Ugc3VjY2VlZGVkIn0=";
	}

	@Test
	public void testUpdate() {
	}

	@org.junit.Test
	public void testMD5() throws Exception {
		String wzt = "wzt";
		String md5 = Encryption.encryptMD5(wzt);
		System.out.println(md5);
	}

	@org.junit.Test
	public void test() {
		// fail("Not yet implemented");
		// testJson();
		// testSearchCharIndex();
		// String str = "afsafsdfsvasdfd";
		// String string = TextUtil.replaceCharIndex(str, 'f', "??????", 3);

		// System.out.println(string);

		// int index = TextUtil.searchCharIndex("", '?', 1);
		// System.out.println(index);

	}

	public void testJsonToMysql() {
//		String text = "{\"city\":\"Beijing\",\"" + "street\":\" Chaoyang Road \\",\" + "\"abc\":\"def\\",\"
//				+ "\"rew\":\"342\\",\" + "\"abn\":\"ibm\\",\" + "\"city\":\"Beijing\\",\" + "\"postcode\":100025}";

		// JSONObject obj = JSONObject.parseObject(text);

		JSONObject obj = beanToJsonObject();
		Set<Entry<String, Object>> set = obj.entrySet();
		StringBuffer mark = new StringBuffer();
		for (int i = 0; i < set.size(); i++) {
			mark.append("?,");
		}
		mark.deleteCharAt(mark.length() - 1);
		String sql = "insert into tablename(" + mark + ")values(" + mark + ") ";
		System.out.println(sql);
		int i = 0;
		String[] ss = new String[set.size() * 2];
		for (Entry<String, Object> entry : set) {

			String key = entry.getKey();
			String value = String.valueOf(entry.getValue());
			System.out.println("key:" + key + "  value:" + value);
			// if(i>=ss.length/2)
			// continue;
			// ss[i] = key;
			// ss[i+set.size()/2] = value;
			// i++;
			System.out.println("i==" + i);
			System.out.println("i+set.size==" + (i + set.size()));
			ss[i] = key;
			ss[i + set.size()] = value;
			i++;
		}

		System.out.println();
		for (int j = 0; j < ss.length; j++) {
			System.out.print(j + "." + ss[j] + "  ");
		}
		System.out.println();
		System.out.println();
		System.out.println();
		for (int j = 0; j < ss.length / 2; j++) {
			System.out.print(ss[j] + "  ");
		}
		System.out.println();
		for (int j = ss.length / 2; j < ss.length; j++) {
			System.out.print(ss[j] + "  ");
		}
	}

	public JSONObject beanToJsonObject() {
		DepartmentMember member = new DepartmentMember();
		member.setName("wzt");
		member.setSex("nan");
		member.setNickName("komawang");
		// 先Bean转换成JsonString，再由JsonString转换成JsonObject。
		String jsonString = JSON.toJSONString(member);
		JSONObject jsonObject = JSONObject.parseObject(jsonString);
		return jsonObject;
	}

	public static JSONObject getJsonobject(String DorC) {
		switch (DorC.toLowerCase()) {
		case "d":
			DepartmentMember member = new DepartmentMember();
			member.setName("wzt");
			member.setNickName("wztscau");
			// member.setStatus("status");
			member.setSex("男");
			member.setSchool("scau");
			member.setTel("13332808084");
			member.setHead_path("http://www.baidu.com");
			member.setAccount("wzt");

			return JsonDealer.beanToJsonObject(member);

		case "c":
			ClubMember member1 = new ClubMember();
			return JsonDealer.beanToJsonObject(member1);
		}
		// JsonDealer<DepartmentMember> dealer = new
		// JsonDealer<DepartmentMember>(member);
		// JSONObject jsonObject = dealer.beanToJsonObject(member);
		// System.out.println(jsonObjectToMysql("department", jsonObject));
		return null;
		// System.out.println("the result string is : "+mysql);
		// System.out.println("jsonobject:");
		// System.out.println(jsonObject);
		//
		// DepartmentMember jsonBean = dealer.jsonObjectToBean(jsonObject);
		// System.out.println(jsonBean.getAccount());
		// System.out.println(jsonBean.getNickName());
		// System.out.println(jsonBean.getUserName());

	}

	public void testSearchCharIndex() {
		String str = "0000000??";
		int number = 0;
		char arr[] = str.toCharArray();
		for (int i = 0; i < arr.length; i++) {

			if (arr[i] == 's') {
				number++;
			}
			if (number == 1) {
				System.out.print("第五次出现s的位置是:" + i);
			}
		}

	}

	public static String jsonObjectToMysql(String tableName, JSONObject obj) {
		Set<Entry<String, Object>> set = obj.entrySet();
		StringBuffer mark = new StringBuffer();
		for (int i = 0; i < set.size(); i++) {
			mark.append("?,");
		}
		mark.deleteCharAt(mark.length() - 1);
		String sql = "insert into " + tableName + "(" + mark + ")values(" + mark + ") ";
		System.out.println(sql);
		int i = 0;
		for (Entry<String, Object> entry : set) {
			String key = entry.getKey();
			String value = String.valueOf(entry.getValue());
			sql = TextUtil.replaceCharIndex(sql, '?', key, 1);
			sql = TextUtil.replaceCharIndex(sql, '?', "\'" + value + "\'", set.size() - i);
			System.out.println("i==" + i + "sql:" + sql);

			i++;
		}

		return sql;
	}

	@org.junit.Test
	public void aes() throws UnsupportedEncodingException {
		String string = "我爱温艺慈";
		String encryptAES = Encryption.encryptAES(string);
		System.out.println(encryptAES);
		String aes = Encryption.decryptAES(encryptAES);
		System.out.println(aes);

	}

}
