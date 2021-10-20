package cn.dianyinhuoban.app2;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PWDTools {
    public static String md5Password(String password) {
        if (TextUtils.isEmpty(password)) {
            return "";
        }
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(password.getBytes());
            String result = "";
            for (byte b : bytes) {
                String temp = Integer.toHexString(b & 0xff);
                if (temp.length() == 1) {
                    temp = "0" + temp;
                }
                result += temp;
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String encryptPassword(String password) {
        if (TextUtils.isEmpty(password)) return "";
        String md5 = md5Password(password);
        if (!TextUtils.isEmpty(md5) && md5.length() > 2) {
            return md5.substring(0, md5.length() - 2);
        } else {
            return md5;
        }
    }
}
