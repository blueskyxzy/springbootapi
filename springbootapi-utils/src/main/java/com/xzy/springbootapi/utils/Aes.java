package com.xzy.springbootapi.utils;


import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.util.Random;
import java.util.UUID;

public class Aes {



    /**
     * 使用Apache的Hex类实现Hex(16进制字符串和)和字节数组的互转
     * <p>
     *
     * @Title : md5Hex
     */
    private static String md5Hex(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(str.getBytes());
            return new String(new Hex().encode(digest));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
            return "";
        }
    }
    public static String getMD5(String passWord,String salt){
        String password = md5Hex(passWord + salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return String.valueOf(cs);
    }
    public static String creatSalt(){
        // 生成一个16位的随机数
        Random random = new Random();
        StringBuilder sBuilder = new StringBuilder(16);
        sBuilder.append(random.nextInt(99999999)).append(random.nextInt(99999999));
        int len = sBuilder.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sBuilder.append("0");
            }
        }
        // 返回盐
        String Salt = sBuilder.toString();
        return Salt;
    }

    /**
     * 验证加盐后是否和原文一致
     */
    public static boolean getSaltverifyMD5(String password,String salt, String md5str) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5str.charAt(i);
            cs1[i / 3 * 2 + 1] = md5str.charAt(i + 2);
            cs2[i / 3] = md5str.charAt(i + 1);
        }
        String Salt = new String(cs2);
        return md5Hex(password + Salt).equals(String.valueOf(cs1));
    }

    //生成随机推广码
    public static String getPromoCode(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}

