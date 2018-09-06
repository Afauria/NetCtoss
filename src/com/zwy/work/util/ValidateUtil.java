package com.zwy.work.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ValidateUtil {
    private ValidateUtil(){}
    public static Date getBirthDateFromIdCard(String idCard){
        String birthDate = idCard.substring(6, 14);// 截取年
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            return sdf.parse(birthDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
