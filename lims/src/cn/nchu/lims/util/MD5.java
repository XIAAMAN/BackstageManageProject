package cn.nchu.lims.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import sun.misc.BASE64Encoder;

public class MD5 {
    /**
     * ����MD5���м���
     * @param oldString : String Ҫ���ܵ��ַ�
     * @return newString : String ���ܺ���ַ�
     */
    public static String EncoderByMd5(String oldString) 
    		throws NoSuchAlgorithmException, UnsupportedEncodingException{
        MessageDigest md5 = MessageDigest.getInstance("MD5");  //ȷ�����㷽��
        BASE64Encoder base64en = new BASE64Encoder();
        String newString  //���ܺ���ַ���
        	= base64en.encode(md5.digest(oldString.getBytes("utf-8")));
        return newString;
    }
  
}