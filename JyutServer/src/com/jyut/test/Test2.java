/**
 * 
 */
package com.jyut.test;

import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jyut.server.util.TextUtil;

/**
 * @date Oct 10, 2016
 * @author wztscau
 * @project 粤盟管理系统服务器
 */
public class Test2 {

	@Test
	public void testJsonArray(){
	}
	
	@Test
	public void testInsert(){
		
//		School school = new School("guangdong", "scau");
		JSONObject jsonobject2 = Test1.getJsonobject("D");
//		JSONObject jsonObject = JsonDealer.beanToJsonObject(school);
		abc(jsonobject2, "suibian");;
		
	}
	
	public void abc(JSONObject jsonObject,String tableName){
		Set<Entry<String, Object>> set = jsonObject.entrySet();
		boolean contain_Id = false;

		StringBuffer mark = new StringBuffer();
		for (int i = 0; i < (contain_Id ? set.size() - 1 : set.size()); i++) {
			mark.append("?,");
		}
		System.out.println("mark::"+mark);
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
						contain_Id ? set.size() - i - 1 : set.size()-i);

			} else if (value instanceof String) {
				sql = TextUtil.replaceCharIndex(sql, '?', "\'" + value + "\'",
						contain_Id ? set.size() - i - 1 : set.size()-i);

			} else {
			}
			System.out.println("i==" + i + "  sql:" + sql);

			i++;
		}
	}
	
	
	@Test
	public void testJsonTransient(){
		User user = new User();
		user.setPassword("password");
		user.setUserName("username");
		String jsonString = JSON.toJSONString(user);
		System.out.println(jsonString);
		
	}
	
	static class User{
		transient private String userName;
		 private String password;
		 transient private String uname;
		/**
		 * @return the userName
		 */
		public String getUserName() {
			return userName;
		}
		/**
		 * @param userName the userName to set
		 */
		public void setUserName(String userName) {
			this.userName = userName;
			uname = this.userName;
		}
		/**
		 * @return the password
		 */
		public String getPassword() {
			return password;
		}
		/**
		 * @param password the password to set
		 */
		public void setPassword(String password) {
			this.password = password;
		}
		/**
		 * @return the uname
		 */
		public String getUname() {
			return uname;
		}
		
	}
}
