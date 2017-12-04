package cn.nchu.lims.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class MD5 {
    /**
     * 利用MD5进行加密
     * @param oldString : String 要加密的字符
     * @return newString : String 加密后的字符
     */
    public static String EncoderByMd5(String oldString) 
    		throws NoSuchAlgorithmException, UnsupportedEncodingException{
        MessageDigest md5 = MessageDigest.getInstance("MD5");  //确定计算方法
        BASE64Encoder base64en = new BASE64Encoder();
        String newString  //加密后的字符串
        	= base64en.encode(md5.digest(oldString.getBytes("utf-8")));
        return newString;
    }
  
}