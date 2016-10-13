/**
 * 
 */
package com.jyut.server.util;

/**
 * 处理文本或者字符串的工具
 * @date Sep 24, 2016
 * @author wztscau
 * @project 粤盟管理系统服务器
 * 
 */
public class TextUtil {

	/**
	 * 寻找字符串某个字符出现第x次的位置
	 * @param str 源字符串
	 * @param ch 要搜寻的某个字符
	 * @param count 出现的第X次
	 * @return
	 */
	public static int searchCharIndex(String str,char ch,int count) {
		int number = 0;
		char arr[] = str.toCharArray();
		for (int i = 0; i < arr.length; i++) {

			if (arr[i] == ch) {
				number++;
			}
			if (number == count) {
				return i; 
			}
		}
		return -1;
	}
	
	/**
	 * 将字符串中出现第X次的字符替换成另一个字符串
	 * @param str 源字符串
	 * @param oldCh 被替换的字符
	 * @param newStr 字符替换后的字符串
	 * @param count 被替换字符出现的第X次
	 * @return 运算后的字符串
	 */
	public static String replaceCharIndex(String str,char oldCh,String newStr,int count){
		int index = searchCharIndex(str, oldCh, count);
		if(index>-1){
			StringBuffer sb = new StringBuffer(str);
			StringBuffer replace = sb.replace(index, index+1, newStr);
			return replace.toString(); 
		}
		return str;
	}
}
