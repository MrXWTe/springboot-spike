package cn.mrxu.spike.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 利用MD5加密表单中密码，以及二次加密数据库中的密码
 */
public class MD5Util {
    private static String md5(String str){
        return DigestUtils.md5Hex(str);
    }

    private static final String salt = "1a2b3c4d";

    public static String inputPassToFormPass(String inputPass){
        String str = ""+salt.charAt(0)+salt.charAt(2) + inputPass +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String formPassToDbPass(String formPass, String salt){
        String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDbPass(String inputPass, String saltDB){
        String formPassword = inputPassToFormPass(inputPass);
        String dbPassword = formPassToDbPass(inputPass, saltDB);
        return md5(dbPassword);
    }

    public static void main(String[] args) {
//      System.out.println(inputPassToFormPass("123456"));
//		System.out.println(formPassToDbPass(inputPassToFormPass("123456"), "1a2b3c4d"));
//		System.out.println(inputPassToDbPass("123456", "1a2b3c4d"));
    }

}
