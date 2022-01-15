package com.springtest.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TreeSet;

public class ShardingUtils {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");

    public static TreeSet<String> getRange(String startTime, String endTime) {

        TreeSet<String> list = new TreeSet<>();
        if (startTime.equals(endTime)){
            list.add(startTime);
        }else {
            try {
                // 转化成日期类型
                Date startDate = sdf.parse(startTime);
                Date endDate = sdf.parse(endTime);

                //用Calendar 进行日期比较判断
                Calendar calendar = Calendar.getInstance();
                while (startDate.getTime()<=endDate.getTime()){
                    // 把日期添加到集合
                    list.add(sdf.format(startDate));
                    // 设置日期
                    calendar.setTime(startDate);
                    //把日期增加一个月
                    calendar.add(Calendar.MONTH, 1);
                    // 获取增加后的日期
                    startDate=calendar.getTime();
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}
