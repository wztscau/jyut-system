/**
 * 
 */
package com.jyut.server.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;

/**
 * @date Sep 26, 2016
 * @author wztscau
 * @project 粤盟管理系统服务器
 */
public class Encryption {

	public static String encryptAES(String src){
		String des = null;
		try {
			des = new Base64().encodeToString(src.getBytes("utf-8"));
			return des;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return src;
	}
	
	public static String decryptAES(String src){
		byte[] bs = Base64.decodeBase64(src);
		try {
			return new String(bs,"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return src;
	}
	
	/**
     * Get the MD5 value of string.
     *
     * @param content the target string.
     * @return the MD5 value.
     */
    public static String encryptMD5(String src) {
        StringBuffer md5Buffer = new StringBuffer();
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] tempBytes = digest.digest(src.getBytes());
            int digital;
            for (int i = 0; i < tempBytes.length; i++) {
                digital = tempBytes[i];
                if (digital < 0) {
                    digital += 256;
                }
                if (digital < 16) {
                    md5Buffer.append("0");
                }
                md5Buffer.append(Integer.toHexString(digital));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5Buffer.toString();
    }
}
