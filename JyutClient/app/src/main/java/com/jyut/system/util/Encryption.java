/**
 *
 */
package com.jyut.system.util;

import com.yolanda.nohttp.Logger;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * 加密类，有AES的加密和解密方法和MD5的加密方法
 *
 * @author wztscau
 * @date Sep 26, 2016
 * @project 粤盟管理系统客户端
 */
public class Encryption {

    private static Cipher cipher;
    private static SecretKeySpec key;
    private static AlgorithmParameterSpec spec;
    public static final String SEED_16_CHARACTER = "U1MjU1M0FDOUZ.Qz";

    static {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(SEED_16_CHARACTER.getBytes("UTF-8"));
            byte[] keyBytes = new byte[32];
            System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);
            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
            key = new SecretKeySpec(keyBytes, "AES");
            spec = getIV();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static AlgorithmParameterSpec getIV() {
        byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,};
        IvParameterSpec ivParameterSpec;
        ivParameterSpec = new IvParameterSpec(iv);
        return ivParameterSpec;
    }

    /**
     * 太麻烦了
     *
     * @param src
     * @return
     * @throws Exception
     */
    @Deprecated
    public static String encryptAES1(String src) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] encrypted = cipher.doFinal(src.getBytes("UTF-8"));
        String encryptedText = new String(android.util.Base64.encode(encrypted, android.util.Base64.DEFAULT), "UTF-8");
        return encryptedText;
    }

    /**
     * 这个方法好恶心,因此deprecate,而且出来的String有空格或者断行符
     *
     * @param src
     * @return
     * @throws Exception
     */
    @Deprecated
    public static String decryptAES1(String src) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] bytes = android.util.Base64.decode(src, android.util.Base64.DEFAULT);
        byte[] decrypted = cipher.doFinal(bytes);
        String decryptedText = new String(decrypted, "UTF-8");
        return decryptedText;
    }

    /**
     * 得到MD5加密后的值
     *
     * @param content the target string.
     * @return the MD5 value.
     */
    public static String encryptMD5(String content) {
        StringBuffer md5Buffer = new StringBuffer();
        try {
            // 创建处理加密数据的工具
            MessageDigest digest = MessageDigest.getInstance("MD5");
            // 消息摘要转换成字节数组
            byte[] tempBytes = digest.digest(content.getBytes());
            int digital;
            // 进行加密
            for (int i = 0; i < tempBytes.length; i++) {
                digital = tempBytes[i];
                if (digital < 0) {
                    digital += 256;
                }
                if (digital < 16) {
                    md5Buffer.append("0");
                }
                // 最后转换成16进制
                md5Buffer.append(Integer.toHexString(digital));
            }
        } catch (NoSuchAlgorithmException e) {
            Logger.e(e);
        }
        return md5Buffer.toString();
    }

    /**
     * 对字符串进行AES加密
     *
     * @param src
     * @return
     */
    public static String encryptAES(String src) {
        byte[] bs = null;
        try {
            bs = src.getBytes("utf-8");
            String des = android.util.Base64.encodeToString(bs, android.util.Base64.DEFAULT);
            //这里要trim一下的原因是android.util.Base64编码之后的string有空格或者断行符，
            //而java8.util.Base64则没有这种情况
            return des.trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 不成功就返回原来的字符串
        return src;
    }

    public static String decryptAES(String src) {
        byte[] bs = android.util.Base64.decode(src, android.util.Base64.DEFAULT);
        String des = null;
        try {
            des = new String(bs, "utf-8");
            //这里要trim一下的原因是android.util.Base64编码之后的string有空格或者断行符，
            //而java8.util.Base64则没有这种情况
            return des.trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 不成功就返回原来的字符串
        return src;
    }
}
