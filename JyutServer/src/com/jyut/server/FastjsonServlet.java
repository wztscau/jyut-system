/**
 * 
 */
package com.jyut.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jyut.server.C.L;
import com.jyut.server.util.Encryption;

/**
 * @date Sep 26, 2016
 * @author wztscau
 * @project 粤盟管理系统服务器
 */
public abstract class FastjsonServlet extends MysqlServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7428812674884732226L;
	protected String userName;
	protected String password;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jyut.server.MysqlServlet#doGet(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		super.doGet(req, resp);
		Connection connection = null;
		PrintWriter writer = null;
		try {
			connection = DriverManager.getConnection(url, "root", "root");
			writer = resp.getWriter();
			onHandle(req,resp,writer,connection);
			// 这里根据需求子类恰当地调用onSuccessResponse（）;
//			onSuccessResponse(req, resp, writer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				onFailResponse(req, resp, writer,e);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			if(writer!=null){
				writer.close();
			}
			if(connection!=null){
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

/**
	 * @param req
	 * @param resp
	 * @param writer
 * @param connection 
	 */
	protected abstract void onHandle(HttpServletRequest req, HttpServletResponse resp, PrintWriter writer, Connection connection) throws Exception;


	protected void onSuccessResponse(HttpServletRequest request, HttpServletResponse response, PrintWriter printWriter) throws Exception{
		Map<String, Object> map = new HashMap<>();
		map.put(L.DATA, setSuccessData(request, response));// 字符串、json、url
		map.put(L.ERROR, "0");// 0的时候代表服务端执行成功
		map.put(L.MESSAGE, setSuccessMessage());
		JSONObject jsonObject = (JSONObject) JSON.toJSON(map);
		String jsonString = jsonObject.toJSONString();
		if(C.ENCRYPTED){
			jsonString = Encryption.encryptAES(jsonString);
		}
		printWriter.write(jsonString);
		System.out.println("onSuccessResponse----->"+jsonString);
	}
	
	protected void onFailResponse(HttpServletRequest request, HttpServletResponse response, PrintWriter printWriter, Exception e) throws Exception{
		Map<String, Object> map = new HashMap<>();
		map.put(L.DATA, setFailData(request, response));
		map.put(L.ERROR, "-1");// error是-1的时候代表服务端有问题
		map.put(L.MESSAGE,setFailMessage());
		JSONObject jsonObject = (JSONObject) JSON.toJSON(map);
		String jsonString = jsonObject.toJSONString();
		if(C.ENCRYPTED){
			jsonString = Encryption.encryptAES(jsonString);
		}
		printWriter.write(jsonString);
		System.out.println("onFailResponse----->"+jsonString);
	}
	
	protected abstract String setSuccessData(HttpServletRequest request, HttpServletResponse response) throws Exception;
	protected abstract String setFailData(HttpServletRequest request, HttpServletResponse response) throws Exception;
	protected abstract String setFailMessage();
	protected abstract String setSuccessMessage();
	
	protected JSONObject getJsonObjectFromClient(HttpServletRequest req) {
		String data = req.getParameter(L.DATA);
		if(C.ENCRYPTED){
			data= Encryption.decryptAES(data);
		}
		System.out.println("getJsonObjectFromClient--->"+data);
		JSONObject jsonObject = JSONObject.parseObject(data);
		return jsonObject;
	}

	protected String getUserName(){
		return userName;
	}
	
	protected String getPassword(){
		return password;
	}
	
}
